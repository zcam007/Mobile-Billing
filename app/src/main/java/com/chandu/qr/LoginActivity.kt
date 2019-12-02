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
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.find


class LoginActivity : AppCompatActivity() {
    private var btnSignin: Button? = null
    private var mAuth: FirebaseAuth? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnSignup: Button? = null
    private var resetbtn: Button?= null
    //Google Login Request Code
    private val RC_SIGN_IN = 7
    //Google Sign In Client
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    //Firebase Auth
    private var email: String? = null
    private var password: String? = null
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //This is to make fullscreen - Remove time bar
//        requestWindowFeature( Window.FEATURE_NO_TITLE )
//        window.setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN )

        //This is to remove ap title bar
        this.supportActionBar!!.hide()
        setContentView(R.layout.activity_login)


        val signInButton = findViewById<View>(R.id.sign_in_button)
        //signInButton.setSize(SignInButton.SIZE_STANDARD)
        // signInButton.set
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        mAuth = FirebaseAuth.getInstance()
        etEmail = findViewById<View>(R.id.login_email) as EditText
        etPassword = findViewById<View>(R.id.login_password) as EditText
        btnSignin = findViewById<View>(R.id.login_btn) as Button
        btnSignup = findViewById<View>(R.id.signup_btn) as Button
        resetbtn= findViewById<View>(R.id.resetbtn) as Button

        signInButton.setOnClickListener {
            signIn()
        }
        if (mAuth != null) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            //startActivity(intent)
            //finish()
        }
        btnSignin!!.setOnClickListener {
            if(etEmail?.text.toString()!=""&&etPassword?.text.toString()!="") {
                firebaseSignin()
            }

        }
        btnSignup!!.setOnClickListener {
            signupPage()
        }
        resetbtn!!.setOnClickListener{
            resetpage()
        }


    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
    }


    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("Login", "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Login", "signInWithCredential:Google success")
                    val user = mAuth!!.currentUser

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                    // updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Login", "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Auth Failed", Toast.LENGTH_LONG).show()
                    //  updateUI(null)
                }

                // ...
            }
    }


    private fun firebaseSignin() {

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
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d(TAG, "signInWithEmail:failure")
                    Toast.makeText(this@LoginActivity, "Authentication failed.", Toast.LENGTH_SHORT)
                        .show()
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

    private fun signupPage() {
        val intent = Intent(this@LoginActivity, CreateAccountActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
    private fun resetpage() {
        val intent=Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}
