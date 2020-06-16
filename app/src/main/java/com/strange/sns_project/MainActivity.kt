package com.strange.sns_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

// Todo : 이메일 로그인, facebook 로그인 구분하기 (User class 를 만들거나 prefared shared 를 쓰는 방법이 있다.
class MainActivity() : AppCompatActivity() {
    // 로그인 구분
    var loginCode : Int = 0

    // user 정보 가져오기
    val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var intent = Intent()
        if (intent.hasExtra("LOGIN")) {
            loginCode = intent.getIntExtra("LOGIN", 0)
            Log.i("INFO", "login code is $loginCode")
            println("login code is $loginCode")
        }
        when (loginCode) {
            1 -> { // 1일경우 google 로그인
                // 로그인한 user email 띄위기
                tv_main_user.text = user?.email.toString()
            }
            2 -> { // 2일 경우 facebook 로그인
                tv_main_user.text = "facebook"
            }
        }
    }
}
