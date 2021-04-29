package com.oliapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.loginPW
import kotlinx.android.synthetic.main.activity_register.registerBTNLogin

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerBTNLogin.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
        //When Register button is clicked
        buttonRegister.setOnClickListener {
            when {
                // Email is empty
                TextUtils.isEmpty(registerUserID.text.toString().trim(){ it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Enter Email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // Password is Empty
                TextUtils.isEmpty(loginPW.text.toString().trim(){ it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Enter Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // Both email and password are entered assign them to val variables
                else -> {
                    val email : String = registerUserID.text.toString().trim() { it <=' '}
                    val password : String = loginPW.text.toString().trim() { it <=' '}

                    // Register user with email & password
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->
                                // Registration is completed successfully
                                if (task.isSuccessful) {
                                    val newUser: FirebaseUser = task.result!!.user!!
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "Registration Complete",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    // User logged in and sent to Main and stops all other
                                    // activities so can't use back button to return to register
                                    val intent = Intent(this@RegisterActivity,
                                            MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("userID", newUser.uid)
                                    intent.putExtra("emailID", email)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    // Registration not completed
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }
                        )
                }

            }
        }


        }
    }
