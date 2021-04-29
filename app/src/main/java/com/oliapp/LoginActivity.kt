package com.oliapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.registerBTNLogin
import kotlinx.android.synthetic.main.activity_login.loginPW
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        registerBTNLogin.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
        loginBTNLogin.setOnClickListener {
            when {
                // Email is empty
                TextUtils.isEmpty(loginUserID.text.toString().trim(){ it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Enter Email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // Password is Empty
                TextUtils.isEmpty(loginPW.text.toString().trim(){ it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Enter Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // Both email and password are entered assign them to variables
                else -> {
                    val email : String = loginUserID.text.toString().trim() { it <=' '}
                    val password : String = loginPW.text.toString().trim() { it <=' '}

                    // Login user with email & password
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener{ task ->
                                // login is completed successfully
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Login Complete",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    // User logged in and sent to Main and stops all other
                                    // activities so can't use back button to return to login
                                    val intent =
                                        Intent(this@LoginActivity, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("userID", FirebaseAuth.getInstance().
                                        currentUser!!.uid)
                                    intent.putExtra("emailID", email)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    // login not completed
                                    Toast.makeText(
                                        this@LoginActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                        }
                }

            }
        }


    }
}
