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
        //apiService = APIConfig.getRetrofitClient(this).create(APIService::class.java)
        val mapper = jacksonObjectMapper()


//        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))

//        swipeRefreshLayout.isRefreshing = true


//        val layoutManager = StaggeredGridLayoutManager(this, Lin)

        products_recyclerview.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        cart_size.text = ShoppingCart.getShoppingCartSize().toString()


// ...
//        database = FirebaseDatabase.getInstance().getReference("/products")
        //mMessageReference = FirebaseDatabase.getInstance().getReference("products")
//        val messageListener = object : ValueEventListener {
//
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    val product = dataSnapshot.getValue(Product::class.java)
//                    Toast.makeText(this@Main2Activity, product.toString(), Toast.LENGTH_SHORT).show()
//                    // ...
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Failed to read value
//            }
//        }
//        mMessageReference!!.addValueEventListener(messageListener)


        val json = """{"description": "asdd", "id": 26,"name":"test","price":123}"""
//        val state = mapper.readValue<Product>("https://api.myjson.com/bins/15b6fq")
        var product: Product = mapper.readValue(json)
        println(product)
        products+=product
        productAdapter = ProductAdapter(this@Main2Activity, products)

        products_recyclerview.adapter = productAdapter
      //  getProducts()


        showCart.setOnClickListener {

            startActivity(Intent(this, ShoppingCartActivity::class.java))
        }

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

                products = response.body()!!

                productAdapter = ProductAdapter(this@Main2Activity, products)

                products_recyclerview.adapter = productAdapter

                productAdapter.notifyDataSetChanged()

            }

        })
    }

}
