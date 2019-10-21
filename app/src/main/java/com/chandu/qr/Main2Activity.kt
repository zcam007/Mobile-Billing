package com.chandu.qr


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main2.*

import retrofit2.Call
import retrofit2.Response
import com.fasterxml.jackson.module.kotlin.*
import com.google.firebase.database.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.net.URL


class Main2Activity : AppCompatActivity() {

    private lateinit var apiService: APIService
    private lateinit var productAdapter: ProductAdapter
    private val TAG = "Main2Activity"
    private var products = listOf<Product>()
//    val APIConfig = APIConfig.
private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Paper.init(this)
        setContentView(R.layout.activity_main2)
    //    setSupportActionBar(toolbar)
     //   apiService = APIConfig.getRetrofitClient(this).create(APIService::class.java)
//        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))
//        swipeRefreshLayout.isRefreshing = true

//        val layoutManager = StaggeredGridLayoutManager(this, Lin)

        products_recyclerview.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        cart_size.text = ShoppingCart.getShoppingCartSize().toString()


        val json = """{"description": "asdd", "id": 26,"name":"test","price":123}"""
//        val state = mapper.readValue<Product>("https://api.myjson.com/bins/15b6fq")
       // val json = URL("https://api.npoint.io/91b780cf9c91397d3ffd/").readText()
       // var product: Product = mapper.readValue(json)
       // println(product)
//        products+=product
//        productAdapter = ProductAdapter(this@Main2Activity, products)

//        products_recyclerview.adapter = productAdapter
        //Toast.makeText(this@Main2Activity, URL("https://api.npoint.io/91b780cf9c91397d3ffd/").readText(), Toast.LENGTH_SHORT).show()

        //getProducts()
        doAsync {
            var result = URL("https://api.npoint.io/91b780cf9c91397d3ffd/").readText()
            uiThread {
                toast(result)
                getProducts2(result)
            }
        }

        showCart.setOnClickListener {

            startActivity(Intent(this, ShoppingCartActivity::class.java))
        }

    }

    private fun getProducts2(result: String) {
        val mapper = jacksonObjectMapper()
//        var product: Product = mapper.readValue(result)
        val product: List<Product> = mapper.readValue(result)
        println(product)
        products+=product
        productAdapter = ProductAdapter(this@Main2Activity, products)
        products_recyclerview.adapter = productAdapter
        productAdapter.notifyDataSetChanged()
    }
    private fun getProducts() {

        apiService.getProducts().enqueue(object : retrofit2.Callback<List<Product>> {
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {

                print(t.message)
                Log.d("Data error", t.message)
                Toast.makeText(this@Main2Activity, t.message, Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {

//                swipeRefreshLayout.isRefreshing = false
             //   swipeRefreshLayout.is
//                swipeRefreshLayout.isEnabled = false
                Toast.makeText(this@Main2Activity, response.body().toString(), Toast.LENGTH_SHORT).show()
//                products = response.body()!!

//                productAdapter = ProductAdapter(this@Main2Activity, products)
//
//                products_recyclerview.adapter = productAdapter
//
//                productAdapter.notifyDataSetChanged()

            }

        })
    }

}
