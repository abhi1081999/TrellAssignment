package com.example.myassignment

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.databinding.DataBindingUtil
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import com.example.myassignment.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_video_playing.*


class MainActivity : AppCompatActivity() , ModelListener{



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val a : ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val viewmodel = ViewModelProviders.of(this).get(Model::class.java)

        a.viewmodel = viewmodel

        viewmodel.modelListener = this




    }

    override fun success() {
       var intent = Intent()
        intent.setType("video/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent,9)



        Toast.makeText(this, "HI" ,Toast.LENGTH_LONG).show();

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && data!= null){
            if(requestCode == 9){
                var uri: Uri = data.data
//                var myVideo:String = getPath(uri)

                if(uri!=null){

//                    p1.setVideoURI(uri)
//                    var mediaController:MediaController = MediaController(this)
//                    p1.setMediaController(mediaController)
//                    p1.start()

////
                    val i = Intent(this, VideoPlayingActivity::class.java)
                    val bn = Bundle()


                    bn.putString("send", uri.toString())
                    i.putExtras(bn)
                    startActivity(i)
                    Toast.makeText(this,"abc",Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this,"no",Toast.LENGTH_LONG).show()
                }




            }

        }

    }


}
