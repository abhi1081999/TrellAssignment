package com.example.myassignment


import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.MediaController
import android.widget.Toast
import com.madhavanmalolan.ffmpegandroidlibrary.Controller
import kotlinx.android.synthetic.main.activity_video_playing.*

import java.io.File
import java.util.jar.Manifest
import android.R.attr.data



class VideoPlayingActivity : AppCompatActivity() {

    var filePath:String = ""

    var str:String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_playing)


        var b = getIntent().getExtras()
        str = b.getString("send")



        play.setVideoURI(Uri.parse(str))
        var mediaController:MediaController = MediaController(this)
        play.setMediaController(mediaController)
        play.start()


        compression.setOnClickListener {
            Toast.makeText(this,getPath(Uri.parse(str)),Toast.LENGTH_LONG).show()


            compress()

        }






    }
    private fun compress(){


        val complexCommand = arrayOf<String>("-y", "-i","", "-s", "160x120", "-r", "25", "-vcodec", "mpeg4", "-b:v", "150k", "-b:a", "48000", "-ac", "2", "-ar", "22050", filePath)

        Controller.getInstance().run(complexCommand)


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                var per = arrayOf<String>(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(per, 100)

            } else {
                startDownload()
            }
        }
        else{
            startDownload()
        }







    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 100){
            if(grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startDownload()
            }
        }





    }

    private fun startDownload(){
        var request = DownloadManager.Request(Uri.parse(str))

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE )
        request.setTitle("Downloading...!")
        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
            ""+System.currentTimeMillis())

        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)


    }

    private fun getPath(uri: Uri): String {


        val file = File(uri.getPath())//create path from uri
        val split = file.getPath().split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()//split the path.
        filePath = split[1]//assign it to a string(your choice).
        return filePath

//
//        var anyArr = arrayOf(MediaStore.Video.Media.DATA)
//
//        var crsr:Cursor = applicationContext.contentResolver.query(uri,anyArr,null,null,null)
//
//        if(crsr!= null){
//
//            var indx:Int = crsr.getColumnIndex(MediaStore.Video.Media.DATA)
//
//            crsr.moveToFirst();
//            return crsr.getString(indx)
//        }
//        else{
//            return ""
//        }



    }




//    companion object {
//        private val TAG: String? = VideoPlayingActivity::class.simpleName
//    }

}
