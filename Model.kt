package com.example.myassignment

import android.arch.lifecycle.ViewModel
import android.view.View

//simple class business logic(model in kotlin)

class Model : ViewModel(){

    var modelListener:ModelListener? = null

    fun getVideo(view : View){
        modelListener?.success();

    }



}