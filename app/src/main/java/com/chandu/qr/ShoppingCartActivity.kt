package com.chandu.qr

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import kotlin.math.roundToInt

class ShoppingCartActivity : AppCompatActivity() {

    private lateinit var adapter: ShoppingCartAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)

        this.supportActionBar!!.hide()
//        setSupportActionBar(toolbar)
//
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//

        sharebutton.setOnClickListener {

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, total_price.text)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)


        }



        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)


        adapter = ShoppingCartAdapter(this, ShoppingCart.getCart())
        adapter.notifyDataSetChanged()
        shopping_cart_recyclerView.adapter = adapter

        shopping_cart_recyclerView.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        var totalPrice = ShoppingCart.getCart()
            .fold(0.toDouble()) { acc, cartItem -> acc + cartItem.quantity.times(cartItem.product.price!!.toDouble()) }
        val priceTwoDigits:Double = Math.round(totalPrice * 100.0) / 100.0

        total_price.text = "$${priceTwoDigits}"
    }





    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()

            }
        }

        return super.onOptionsItemSelected(item)
    }
}
