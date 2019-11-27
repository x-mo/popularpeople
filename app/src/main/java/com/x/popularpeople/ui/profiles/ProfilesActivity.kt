package com.x.popularpeople.ui.profiles

import android.Manifest
import android.app.DownloadManager
import android.app.DownloadManager.Request.NETWORK_MOBILE
import android.app.DownloadManager.Request.NETWORK_WIFI
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.x.popularpeople.R
import com.x.popularpeople.api.ORIGINAL_POSTER_BASE_URL
import kotlinx.android.synthetic.main.activity_profiles.*

class ProfilesActivity : AppCompatActivity() {

    private val STORAGE_PERMISSION_CODE: Int = 1000

    var imagePath: String? = ORIGINAL_POSTER_BASE_URL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiles)

        imagePath += intent.getStringExtra("path")
        Glide.with(this).load(imagePath).into(profile_image)

        save_to_device.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    requestPermissions(
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        STORAGE_PERMISSION_CODE
                    )
                } else {
                    //download
                    downloadFile()
                }
            } else {
                //download
                downloadFile()
            }
        }

    }

    private fun downloadFile() {

        val request = DownloadManager.Request(Uri.parse(imagePath))
        request.setAllowedNetworkTypes(NETWORK_WIFI or NETWORK_MOBILE)
        request.setTitle("PopularPeople")
        request.setDescription("File is downloading...")

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            "${System.currentTimeMillis()}"
        )

        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    downloadFile()
                } else {
                    Toast.makeText(this, "Permission needed to save photo", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

}
