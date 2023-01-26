package com.example.chattingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {

    private lateinit var mAuth:FirebaseAuth
    private lateinit var username:EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var btnsignup: Button
    private lateinit var mDbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide()

        mAuth=FirebaseAuth.getInstance();
        username=findViewById(R.id.username)
        email=findViewById(R.id.email)
        password=findViewById(R.id.password)
        btnsignup=findViewById(R.id.btnsignup)

        btnsignup.setOnClickListener{
            val username=username.text.toString()
            val email=email.text.toString()
            val password=password.text.toString()

            signup(username,email,password)
        }
    }

    private fun signup(username:String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(username,email,mAuth.currentUser?.uid!!)
                    val intent=Intent(this@SignupActivity,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignupActivity,"Some error occurred",Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun addUserToDatabase(username: String, email: String, uid: String?) {
        mDbRef=FirebaseDatabase.getInstance().getReference()
        if (uid != null) {
            mDbRef.child("user").child(uid).setValue(User(username,email,uid))
        }

    }
}