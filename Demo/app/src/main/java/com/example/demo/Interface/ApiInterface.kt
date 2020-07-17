package com.example.demo.Interface

import android.content.Context
import com.example.demo.Model.ModelPage
import com.lifeprint.digitalframe.Login.Interface.ApiCallBack

interface ApiInterface {
   fun getMedia(callBack: ApiCallBack<ModelPage>, key : String, context: Context,page_no:Int)
}