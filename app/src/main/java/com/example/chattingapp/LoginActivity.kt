package com.example.chattingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var btnlogin:Button
    private lateinit var btnsignup:Button

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        mAuth=FirebaseAuth.getInstance();

        email=findViewById(R.id.email)
        password=findViewById(R.id.password)
        btnlogin=findViewById(R.id.btnlogin)
        btnsignup=findViewById(R.id.btnsignup)

        btnsignup.setOnClickListener{
            val intent= Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }

        btnlogin.setOnClickListener{
            val email=email.text.toString()
            val password=password.text.toString()

            login(email,password)
        }
    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent=Intent(this@LoginActivity,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }
                else {
                    Toast.makeText(this@LoginActivity,"User does not exit",Toast.LENGTH_SHORT).show()
                }
            }
    }
}