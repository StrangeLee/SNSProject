package com.strange.sns_project

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Read firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        // 회원가입 버튼
        btn_signUp.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        val email = et_signUp_email.text.toString()
        val pw = et_signUp_pw.text.toString()
        val pwCheck = et_signUp_pwCheck.text.toString()

        if (email == "" || pw == "" || pwCheck == "") { // null 확인
            Toast.makeText(applicationContext, "입력하지 않은 항목이 있습니다.", Toast.LENGTH_LONG).show()
        } else if (pw != pwCheck) { // 비밀 번호랑 비밀번호체크 란 비교
            Toast.makeText(applicationContext, "비밀번호가 일치 하지 않습니다.", Toast.LENGTH_LONG).show()
        } else { // 새로운 user 등록
            firebaseAuth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        val user = firebaseAuth?.currentUser
                        Toast.makeText(this, "회원가입이 성공했습니다.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                    } else {
                        Toast.makeText(this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        Log.d("SIGNUP", "Error about ${it.exception}")
                    }
                }
        }

    }
}
