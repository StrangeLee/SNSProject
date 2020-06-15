package com.strange.sns_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Read firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Login btn
        btn_login.setOnClickListener {
            login()
        }

        // SignUp btn
        tv_login_signup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    // login function
    private fun login() {
        val email : String = et_login_email.text.toString()
        val pw : String = et_login_password.text.toString()

        if (email == "" || pw == "") { // null 체크
            Toast.makeText(applicationContext, "이메일이 또는 패스워드를 입력하지 않으셨습니다.", Toast.LENGTH_LONG).show()
        } else { // null 이 아닐 시 이메일, 비밀번호 확인
            firebaseAuth.signInWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "로그인 성공", Toast.LENGTH_LONG).show()
                    // Todo : intent 추가
                    Log.d("LOGIN", "Login 성공")
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(applicationContext, "로그인 실패", Toast.LENGTH_LONG).show()
                    Log.d("LOGIN", "Login 실패")
                }
            }
        }
    }
}

