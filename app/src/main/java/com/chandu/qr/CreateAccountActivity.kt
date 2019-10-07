package com.chandu.qr

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import com.google.firebase.auth.*
import com.google.firebase.database.*
import android.widget.Toast




class CreateAccountActivity : AppCompatActivity() {

    private var etFirstName: EditText? = null
    private var etLastName: EditText? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnCreateAccount: Button? = null
   // private var mProgressBar: ProgressDialog? = null

    //Firebase References
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null



    //Global Variables

    private val TAG = "CreateAccountActivity"
    //global variables
    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //This is to make fullscreen - Remove time bar
        requestWindowFeature( Window.FEATURE_NO_TITLE )
        window.setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN )

        //This is to remove ap title bar
        this.supportActionBar!!.hide()
        setContentView(R.layout.activity_create_account)
        initialise()

    }
    private fun initialise() {
        etFirstName = findViewById<View>(R.id.et_first_name) as EditText
        etLastName = findViewById<View>(R.id.et_last_name) as EditText
        etEmail = findViewById<View>(R.id.et_email) as EditText
        etPassword = findViewById<View>(R.id.et_password) as EditText
        btnCreateAccount = findViewById<View>(R.id.btn_register) as Button
      //  mProgressBar = ProgressDialog(this)
        //mDatabase = FirebaseDatabase.getInstance()
        //mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        btnCreateAccount!!.setOnClickListener {
            createNewAccount()
        }
    }


    private fun createNewAccount() {
        firstName = etFirstName?.text.toString()
        lastName = etLastName?.text.toString()
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()
        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)
            && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
         //   mProgressBar!!.setMessage("Registering User...")
         //   mProgressBar!!.show()
         //   mAuth = FirebaseAuth.getInstance()
//            mAuth!!.createUserWithEmailAndPassword(email!!, password!!).addOnCompleteListener(this)
//            {
//                    task -> if (task.isSuccessful) {
//        // Sign in success, update UI with the signed-in user's information
//        Log.d(TAG, "createUserWithEmail:success")
//       // val user = mAuth.getCurrentUser()
//       // updateUI(user)
//    }
//            else {
//        // If sign in fails, display a message to the user.
//        Log.w(TAG, "createUserWithEmail:failure", task.exception)
//        Toast.makeText(this@CreateAccountActivity, "Authentication failed.",
//            Toast.LENGTH_SHORT).show()
//        //updateUI(null)
//    }
//
//}
            mAuth!!
                .createUserWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->
                //    mProgressBar!!.hide()
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val userId = mAuth!!.currentUser!!.uid
                        //Verify Email
                        verifyEmail()
                        //update user profile information
//                        val currentUserDb = mDatabaseReference!!.child(userId)
//                        currentUserDb.child("firstName").setValue(firstName)
//                        currentUserDb.child("lastName").setValue(lastName)
                        updateUserInfoAndUI()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this@CreateAccountActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }


        }

    }


    private fun updateUserInfoAndUI() {
        //start next activity
        val intent = Intent(this@CreateAccountActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser;
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@CreateAccountActivity,
                        "Verification email sent to " + mUser.email,
                        Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(this@CreateAccountActivity,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}
