package com.chandu.qr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import com.google.firebase.auth.*
import android.widget.Toast
import android.util.Log



class LoginActivity : AppCompatActivity() {
    private var btnSignin: Button? = null
    private var mAuth: FirebaseAuth? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnSignup: Button?=null


    private var email: String? = null
    private var password: String? = null
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //This is to make fullscreen - Remove time bar
        requestWindowFeature( Window.FEATURE_NO_TITLE )
        window.setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN )

        //This is to remove ap title bar
        this.supportActionBar!!.hide()
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        etEmail = findViewById<View>(R.id.login_email) as EditText
        etPassword = findViewById<View>(R.id.login_password) as EditText
        btnSignin = findViewById<View>(R.id.login_btn) as Button
        btnSignup = findViewById<View>(R.id.signup_btn) as Button

        btnSignin!!.setOnClickListener {
            firebaseSignin()
        }
        btnSignup!!.setOnClickListener {
            signupPage()
        }
    }


    private fun firebaseSignin(){

        email = etEmail?.text.toString()
        password = etPassword?.text.toString()
        mAuth!!.signInWithEmailAndPassword(email!!, password!!)
               .addOnCompleteListener(this) { task ->
    if (task.isSuccessful) {
        // Sign in success, update UI with the signed-in user's information
     //   Log.d(FragmentActivity.TAG, "signInWithEmail:success")
        Log.d(TAG, "signInWithEmail:success")

       // val user = mAuth.getCurrentUser()

      //  updateUI(user)
      //  mAuth.currentUser
        signInSuccess()
    }
    else {
        // If sign in fails, display a message to the user.
        Log.d(TAG, "signInWithEmail:failure")
        Toast.makeText(this@LoginActivity, "Authentication failed.", Toast.LENGTH_SHORT).show()
        // updateUI(null)
    }
        }
    }


    private fun signInSuccess() {
        //start next activity
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun signupPage()
    {
        val intent = Intent(this@LoginActivity, CreateAccountActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
