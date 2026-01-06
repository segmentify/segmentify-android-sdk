package com.segmentify.segmentifysdk

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val TAG = "DeepLinkManager"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermissions()
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent == null) {
            return
        }

        if (intent.hasExtra("deeplink")) {
            val deepLinkUrl = intent.getStringExtra("deeplink")
            processDeepLink(deepLinkUrl)
        } else if (intent.data != null) {
            val deepLinkUrl = intent.data.toString()
            processDeepLink(deepLinkUrl)
        } else {
            Log.w(TAG, "no deeplink.")
        }
    }

    private fun processDeepLink(url: String?) {
        if (url.isNullOrEmpty()) {
            return
        }

        try {
            val uri = Uri.parse(url)
            val host = uri.host // "product", "category" vb.

            if (host == "product") {
                val productId = uri.getQueryParameter("id")

                if (!productId.isNullOrEmpty()) {
                    val detailIntent = Intent(this, ProductDetailActivity::class.java)
                    detailIntent.putExtra("PRODUCT_ID", productId)
                    startActivity(detailIntent)
                } else {
                    Toast.makeText(this, "product not found.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.w(TAG, "Unknown Host: $host")
            }
        } catch (e: Exception) {
            Log.e(TAG, "DeepLink parse error: ${e.message}")
        }
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }
    }
}