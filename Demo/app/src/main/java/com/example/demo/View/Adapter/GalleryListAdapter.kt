package com.example.demo.View.Adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.demo.Model.Data
import com.example.demo.R
import com.example.demo.View.Activity.ImageDetail

class GalleryListAdapter(private val context: Context, var mItemList: ArrayList<Data>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_gallery_item, parent, false)
            ItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.progress_dialog_layout, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (viewHolder is ItemViewHolder) {
            populateItemRows(viewHolder, position)
        } else if (viewHolder is LoadingViewHolder) {
            showLoadingView(viewHolder, position)
        }
    }

    override fun getItemCount(): Int {
        return if (mItemList == null) 0 else mItemList!!.size
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    override fun getItemViewType(position: Int): Int {
        return if (mItemList.get(position).link == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    private inner class ItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var ivItem: ImageView

        init {
            ivItem = itemView.findViewById(R.id.imageViewPhoto)
        }
    }

    private inner class LoadingViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var progressBar: ProgressBar

        init {
            progressBar = itemView.findViewById(R.id.progressBar)
        }
    }

    private fun showLoadingView(
        viewHolder: LoadingViewHolder,
        position: Int
    ) { //ProgressBar would be displayed
    }

    private fun populateItemRows(viewHolder: ItemViewHolder, position: Int) {


        Glide.with(context)
            .load(mItemList.get(position).images?.get(0)?.link)
            .thumbnail(0.2f)
            .apply(RequestOptions().placeholder(R.drawable.placeholder_for_missing_posters).fitCenter())
            .into(viewHolder.ivItem)

        viewHolder.ivItem.setOnClickListener {
            val intent = Intent(context, ImageDetail::class.java)
            intent.putExtra("url",mItemList.get(position).images?.get(0)?.link)
            intent.putExtra("id",mItemList.get(position).id)
            context.startActivity(intent)
        }
    }

    fun notifyData(mediaList: java.util.ArrayList<Data>) {
        mItemList = mediaList
        notifyDataSetChanged()
    }

    fun addNullData() {
        mItemList.add(Data())
        notifyItemInserted(mItemList.size - 1)
    }

    fun removeNullData() {
        val data: Data? = mItemList.find { it.link == null }
        mItemList.remove(data)
        notifyDataSetChanged()
    }

}