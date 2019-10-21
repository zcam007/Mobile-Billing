package com.chandu.qr
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

import com.google.zxing.integration.android.IntentIntegrator
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.showCart
import kotlinx.android.synthetic.main.activity_main2.*


import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private var txtName: TextView? = null

    private var txtSiteName: TextView? = null

    private var btnScan: Button? = null

    private var qrScanIntegrator: IntentIntegrator? = null

    private var products = listOf<Product>()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Paper.init(this)
        txtName = findViewById(R.id.name)
        txtSiteName = findViewById(R.id.site_name)

        btnScan = findViewById(R.id.btnScan)
        btnScan!!.setOnClickListener { performAction() }

        qrScanIntegrator = IntentIntegrator(this)
        qrScanIntegrator?.setOrientationLocked(false)

        val button = findViewById<Button>(R.id.showQRScanner)
        button?.setOnClickListener {
            // Add code to show QR Scanner Code in Fragment.
        }
     //   DrawerBuilder().withActivity(this).build()


        showCart.setOnClickListener {

            startActivity(Intent(this, ShoppingCartActivity::class.java))
        }
        productsPage.setOnClickListener{
            startActivity(Intent(this, ShoppingList::class.java))
        }
    }

    private fun performAction() {
        qrScanIntegrator?.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if (result.contents == null) {
                Toast.makeText(this, getString(R.string.result_not_found), Toast.LENGTH_LONG).show()
            } else {
                // If QRCode contains data.
                try {
                    // Converting the data to json format
                    val obj = JSONObject(result.contents)

                    // Show values in UI.
                    txtName?.text = obj.getString("name")
                    txtSiteName?.text = obj.getString("site_name")

                } catch (e: JSONException) {
                    e.printStackTrace()

                    // Data not in the expected format. So, whole object as toast message.
                    Toast.makeText(this, result.contents, Toast.LENGTH_LONG).show()
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}