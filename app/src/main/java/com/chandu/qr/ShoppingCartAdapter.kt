package com.chandu.qr

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_list_item.view.*

class ShoppingCartAdapter(var context: Context, var cartItems: List<CartItem>) :
    RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {

        val layout = LayoutInflater.from(context).inflate(R.layout.cart_list_item, parent, false)

        return ViewHolder(layout)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        //if(cartItems[position].quantity!=0)
        viewHolder.bindItem(cartItems[position])
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(cartItem: CartItem) {
            var urlFromDB=cartItem.product.imageURL
            if(cartItem.product.imageURL=="" || cartItem.product.imageURL==null){
                urlFromDB="https://img.webmd.com/dtmcms/live/webmd/consumer_assets/site_images/articles/health_tools/12_powerhouse_vegetables_slideshow/intro_cream_of_crop.jpg"
            }
            //Picasso.get().load(urlFromDB).fit().into(itemView.product_image)

            Picasso.get().load(urlFromDB).fit().into(itemView.product_image)


                itemView.product_name.text = cartItem.product.name

                itemView.product_price.text = "$${cartItem.product.price}"

                itemView.product_quantity.text = cartItem.quantity.toString()

        }


    }

}