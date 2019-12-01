package com.chandu.qr
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.integration.android.IntentIntegrator
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.showCart


import org.json.JSONException
import org.json.JSONObject
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import kotlinx.android.synthetic.main.activity_main.cart_size
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import android.graphics.drawable.Drawable
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import android.net.Uri
import android.widget.ImageView
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {

    private var txtName: TextView? = null

    private var txtSiteName: TextView? = null

    private var btnScan: Button? = null

    private var qrScanIntegrator: IntentIntegrator? = null

    private var products = listOf<Product>()
    private lateinit var productAdapter: ProductAdapter
    private var welcomeName: TextView?= null
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Paper.init(this)
    //    txtName = findViewById(R.id.name)
   //     txtSiteName = findViewById(R.id.site_name)

        this.supportActionBar!!.hide()
        welcomeName=findViewById(R.id.welcomeTag)
        btnScan = findViewById(R.id.btnScan)
        btnScan!!.setOnClickListener { performAction() }

        qrScanIntegrator = IntentIntegrator(this)
        qrScanIntegrator?.setOrientationLocked(false)

        var instance= FirebaseAuth.getInstance()

        if(instance.currentUser!!.displayName==null){
            welcomeName!!.text="Welcome, "+instance.currentUser!!.email

        }else
        welcomeName!!.text="Welcome, "+instance.currentUser!!.displayName
       // SupportActionBar.setDisplayHomeAsUpEnabled(false);
        //result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
//println("Photoooo  "+instance.currentUser!!.photoUrl!!)
        val headerResult = AccountHeaderBuilder()
            .withActivity(this)
            .addProfiles(
                ProfileDrawerItem().withName(welcomeName!!.text).withEmail(instance.currentUser!!.email).withIcon(instance.currentUser!!.photoUrl!!)
            )

            .build()
        //Resources().getDrawable(R.drawable.profile)
       // DrawerImageLoader.init()

        var result= DrawerBuilder().withActivity(this)
            .withTranslucentStatusBar(false)
            .withActionBarDrawerToggle(true)
            .withAccountHeader(headerResult)

            .addDrawerItems(
                 PrimaryDrawerItem().withName("Shopping List"),
                 DividerDrawerItem(),
                 SecondaryDrawerItem().withName("Cart")
            )
                    .build()


        val button = findViewById<Button>(R.id.showQRScanner)
        button?.setOnClickListener {
            // Add code to show QR Scanner Code in Fragment.
        }
     //   DrawerBuilder().withActivity(this).build()

        cart_size.text = ShoppingCart.getShoppingCartSize().toString()
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
        database = FirebaseDatabase.getInstance().reference
        var mMainMenuRef = database.child("/")
        var instance= FirebaseAuth.getInstance()
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if (result.contents == null) {



                Toast.makeText(this, getString(R.string.result_not_found), Toast.LENGTH_LONG).show()
                val key = database.child("/").push().key
                println("KEYYYY"+key)
            //    var newProduct=Product(23,"test","2.33","asdd","https://image.shutterstock.com/image-photo/isolated-apples-whole-red-apple-260nw-575378506.jpg")
//                database.child("/").child("3").setValue(newProduct)
              //  database.child("/").push().setValue(newProduct)

            } else {
                // If QRCode contains data.
                try {
                    // Converting the data to json format
                    val obj = JSONObject(result.contents)
                    var count=0
                    var idFromQR=obj.getInt("id")
                    var nameFromQR=obj.getString("name")
                    var descFromQR=obj.getString("description")
                    var imageFromQR=obj.getString("imageURL")
                    var priceFromQR=obj.getString("price")

                    // println("idFromQR "+idFromQR)

                    database.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            print("Error fetching data from firebase")
                        }
                        override fun onDataChange(p0: DataSnapshot) {
                            var children=p0!!.children
                            children.forEach { println("hello"+it.value.toString())
                                count++
                            }
                            var newProduct=Product(idFromQR,nameFromQR,priceFromQR,descFromQR,imageFromQR)
                            database.child("/").child(count.toString()).setValue(newProduct)
                        }
                    })

                    Toast.makeText(this, "Added Successfully", Toast.LENGTH_LONG).show()
                    // Show values in UI.
                 //   txtName?.text = obj.getString("name")
                   // txtSiteName?.text = obj.getString("site_name")

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