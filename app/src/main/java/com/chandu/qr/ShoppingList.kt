package com.chandu.qr


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.paperdb.Paper
import com.fasterxml.jackson.module.kotlin.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_shopping_list.*
import com.google.firebase.database.DatabaseReference
import com.google.gson.Gson




class ShoppingList : AppCompatActivity() {

    private lateinit var apiService: APIService
    private lateinit var productAdapter: ProductAdapter
    private val TAG = "ShoppingList"
    private var products = listOf<Product>()
    //    val APIConfig = APIConfig.
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Paper.init(this)
        setContentView(R.layout.activity_shopping_list)
        this.supportActionBar!!.hide()
        //    setSupportActionBar(toolbar)
        //   apiService = APIConfig.getRetrofitClient(this).create(APIService::class.java)
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))
        swipeRefreshLayout.isRefreshing = true

//        val layoutManager = StaggeredGridLayoutManager(this, Lin)

        products_recyclerview.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        cart_size.text = ShoppingCart.getShoppingCartSize().toString()

        database = FirebaseDatabase.getInstance().reference
        var mMainMenuRef = database.child("/")
        var instance= FirebaseAuth.getInstance()
        mMainMenuRef.addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                print("Error fetching data from firebase")
                Toast.makeText(this@ShoppingList, "An error has occurred!", Toast.LENGTH_SHORT).show()
            }
            override fun onDataChange(p0: DataSnapshot) {
                var children=p0!!.children
                //children.forEach { println("crazy!"+it.value.toString())
               // }
                println("Children-"+p0)
                val obj = p0.getValue(Any::class.java)
                val json = Gson().toJson(obj)
                println("JSON"+json)
                getProducts(json.toString())

            }
        })
//        doAsync {
//            var result = URL("https://api.npoint.io/91b780cf9c91397d3ffd/").readText()
//         //   var result = URL(" https://shoppingqr-51242.firebaseio.com/").readText()
//
//
//            //dataSnapShot.getValue()
//            uiThread {
//               // toast(result)
////                getProducts2(result)
//            }
//        }
        showCart.setOnClickListener {
            startActivity(Intent(this, ShoppingCartActivity::class.java))
        }
        qrScanButton.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    private fun getProducts(result: String) {
        swipeRefreshLayout.isRefreshing = false
//      swipeRefreshLayout.is
        swipeRefreshLayout.isEnabled = false
        val mapper = jacksonObjectMapper()
        val product: List<Product> = mapper.readValue(result)
        products+=product
        productAdapter = ProductAdapter(this@ShoppingList, products)
        products_recyclerview.adapter = productAdapter
        productAdapter.notifyDataSetChanged()
    }
//    private fun getProducts() {
//
//        apiService.getProducts().enqueue(object : retrofit2.Callback<List<Product>> {
//            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
//
//                print(t.message)
//                Log.d("Data error", t.message)
//                Toast.makeText(this@ShoppingList, t.message, Toast.LENGTH_SHORT).show()
//
//            }
//
//            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
//
////                swipeRefreshLayout.isRefreshing = false
//                //   swipeRefreshLayout.is
////                swipeRefreshLayout.isEnabled = false
//                Toast.makeText(this@ShoppingList, response.body().toString(), Toast.LENGTH_SHORT).show()
////                products = response.body()!!
//
////                productAdapter = ProductAdapter(this@Main2Activity, products)
////
////                products_recyclerview.adapter = productAdapter
////
////                productAdapter.notifyDataSetChanged()
//
//            }
//
//        })
//    }

}
