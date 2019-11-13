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
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import com.mikepenz.materialdrawer.AccountHeader
//import android.R
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import kotlinx.android.synthetic.main.activity_main.cart_size
import kotlinx.android.synthetic.main.activity_shopping_list.*


class MainActivity : AppCompatActivity() {

    private var txtName: TextView? = null

    private var txtSiteName: TextView? = null

    private var btnScan: Button? = null

    private var qrScanIntegrator: IntentIntegrator? = null

    private var products = listOf<Product>()
    private lateinit var productAdapter: ProductAdapter
    private var welcomeName: TextView?= null

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


        val headerResult = AccountHeaderBuilder()
            .withActivity(this)
            .addProfiles(
                ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com")
            )

            .build()

        var result= DrawerBuilder().withActivity(this)
            .withTranslucentStatusBar(false)
            .withActionBarDrawerToggle(true)
            .withAccountHeader(headerResult)

            .addDrawerItems(
                 PrimaryDrawerItem().withName("Hello"),
                 DividerDrawerItem(),
                 SecondaryDrawerItem().withName("Menu item2")
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