package com.strange.sns_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity() {

    // firebase Auth
    lateinit var firebaseAuth : FirebaseAuth

    // facebook login
    lateinit var mLoginCallback : FacebookLoginCallback
    lateinit var mCallbackManager: CallbackManager

    private final val LOGIN_EXTRA = "LOGIN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
        setContentView(R.layout.activity_login)

        // facebook login callback 관리
        mCallbackManager = CallbackManager.Factory.create()
        mLoginCallback = FacebookLoginCallback()

        btn_facebook_login.setOnClickListener {
            val loginManager = LoginManager.getInstance()
            loginManager.logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
            loginManager.registerCallback(mCallbackManager, mLoginCallback)
        }

        // Read firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        // email Login btn
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
                    Log.d("LOGIN", "Login 성공")
                    var intent = Intent(this, MainActivity::class.java)
                    intent.putExtra(LOGIN_EXTRA, 1)
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "로그인 실패", Toast.LENGTH_LONG).show()
                    Log.d("LOGIN", "Login 실패")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra(LOGIN_EXTRA, 2)
        startActivity(intent)
    }
}

