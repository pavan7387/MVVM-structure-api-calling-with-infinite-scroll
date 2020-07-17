package com.example.demo

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.Interface.MediaInjection
import com.example.demo.Model.Data
import com.example.demo.Util.AppUtil
import com.example.demo.View.Adapter.GalleryListAdapter
import com.example.demo.ViewModel.MediaViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private  var searchKey: String=""
    private var spancount: Int = 3
    private var mList: ArrayList<Data> = ArrayList()
    private var page_no: Int = 1
    private var PAGE_SIZE_data: Int = 0
    private var isApiCalled: Boolean = false
    private lateinit var wifiReceiver: BroadcastReceiver
    private lateinit var mGalleryListAdapter: GalleryListAdapter
    private lateinit var photoGridManager: GridLayoutManager
    private lateinit var mMediaViewModel: MediaViewModel
    private  var mDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerBroadcast()
        setUpViewModel()
        setPhotoAdapter()
        listLazyLoading()
        setClickEvent()
    }

    private fun setClickEvent() {
        iv_search.setOnClickListener(this)
    }

    private fun listLazyLoading() {
        rv_image.addOnScrollListener(@RequiresApi(Build.VERSION_CODES.M)
        object : RecyclerView.OnScrollListener(),
            View.OnScrollChangeListener {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = photoGridManager.childCount
                val firstVisibleItemPosition = photoGridManager.findFirstVisibleItemPosition()
                val totalItemCount = photoGridManager.itemCount

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= PAGE_SIZE_data && !isApiCalled
                ) {
                    isApiCalled = true
                    Log.d("nullllllll","add")
                    mGalleryListAdapter.addNullData()
                    Handler().postDelayed({
                        Thread(Runnable {
                            mMediaViewModel.getMedia(this@MainActivity, editTextSearch.text.toString().trim(), page_no)
                        }).start()
                    }, 2000)
                }
            }

            override fun onScrollChange(
                v: View?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {

            }
        })
    }

    private fun registerBroadcast() {
        wifiReceiver = WifiReceiver()
        try {
            registerReceiver(wifiReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        }catch (e: Exception){}
    }

    inner class WifiReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val conMan: ConnectivityManager? =context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo: NetworkInfo? = conMan?.getActiveNetworkInfo()
            if (netInfo != null){
                if (mMediaViewModel.getMediaList().size >0){
                    rv_image.visibility = View.VISIBLE
                    tv_no_data.visibility = View.GONE

                }else{
                    tv_no_data.text = getString(R.string.no_data)
                  //  getMediaList()
                }

            }else{
                tv_no_data.visibility = View.VISIBLE
                tv_no_data.text = getString(R.string.no_internet_nplease_connect_to_internet)
                rv_image.visibility = View.GONE
            }
        }
    }

    private fun setUpViewModel() {
        mMediaViewModel = ViewModelProvider(this,MediaInjection.provideViewModelFactory()).get(MediaViewModel::class.java)
        setObserver()
    }

    private fun setObserver() {
        mMediaViewModel.getImages().observe(this, Observer { it ->
            rv_image.visibility = View.VISIBLE
            tv_no_data.visibility = View.GONE
            if (it.data?.size != 0) {
                if (page_no != 1) {
                    mGalleryListAdapter.removeNullData()
                }
                PAGE_SIZE_data += it.data?.size!!
                page_no++
                mGalleryListAdapter.notifyData(mMediaViewModel.getMediaList())
                mDialog?.dismiss()
                addToDb(it?.data as ArrayList<Data>)
                isApiCalled = false
            }
        })
        mMediaViewModel.getImageFaild().observe(this,Observer{
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            mDialog?.dismiss()
            isApiCalled = false
        })
    }

    private fun addToDb(arrayList: ArrayList<Data>) {
        Thread(Runnable {
            Thread.sleep(5000)
            mMediaViewModel.addDataToDb(arrayList)
        }).start()
    }

    /**
     * get media list
     */
    private fun getMediaList() {
        if (mMediaViewModel.getMediaList().size == 0) {
            isApiCalled = true
            mDialog = AppUtil.showProgressDialog(this)
            mMediaViewModel.getMedia(this,editTextSearch.text.toString().trim(),page_no)
        }
    }

    /**
     * set adpter to show list
     */
    private fun setPhotoAdapter() {
        photoGridManager = GridLayoutManager(this, spancount)
        rv_image.layoutManager = photoGridManager
        mList = mMediaViewModel.getMediaList()
        mGalleryListAdapter = GalleryListAdapter(this,mList)
        rv_image.adapter = mGalleryListAdapter
        rv_image.itemAnimator = null
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.iv_search -> handleSearchClick()
        }
    }


    /**
     * this method handle search click
     */
    private fun handleSearchClick() {
        if (!editTextSearch.text.toString().trim().equals("")) {
            if (searchKey.equals(editTextSearch.text.toString().trim())) {
            } else {
                mMediaViewModel.clearList()
                PAGE_SIZE_data = 0
                page_no =1
            }
            searchKey = editTextSearch.text.toString().trim()
            getMediaList()
        }else{
            Toast.makeText(this, "Please enter search key.", Toast.LENGTH_SHORT).show()
        }
    }
}
