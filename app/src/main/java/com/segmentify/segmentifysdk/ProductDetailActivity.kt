package com.segmentify.segmentifysdk

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvDesc: TextView
    private lateinit var btnBuy: Button
    private lateinit var imgProduct: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        initViews()
        val productId = intent.getStringExtra("PRODUCT_ID")

        if (productId != null) {
            loadProductData(productId)
        } else {
            Toast.makeText(this, "product not found!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun initViews() {
        tvTitle = findViewById(R.id.tvProductTitle)
        tvPrice = findViewById(R.id.tvProductPrice)
        tvDesc = findViewById(R.id.tvProductDesc)
        btnBuy = findViewById(R.id.btnBuy)
        imgProduct = findViewById(R.id.imgProduct)

        btnBuy.setOnClickListener {
            Toast.makeText(this, "Added to basket!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadProductData(id: String) {

        val productTitle = "Product #$id"
        val productPrice = "XX.XX Eur"
        val productDesc = "You need to add details here!"

        tvTitle.text = productTitle
        tvPrice.text = productPrice
        tvDesc.text = productDesc

    }
}