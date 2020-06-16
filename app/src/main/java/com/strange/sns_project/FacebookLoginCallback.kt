package com.strange.sns_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.facebook.AccessToken
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginResult

// 로그인 요청후 결과를 받을 Callback 클래스
class FacebookLoginCallback : FacebookCallback<LoginResult> {
    override fun onSuccess(result: LoginResult?) {
        Log.e("Callback::", "Success")
        requestMe(result?.accessToken)
    }

    override fun onCancel() {
        Log.e("Callback::", "Cancel")
    }

    override fun onError(error: FacebookException?) {
        Log.e("Callback::", "Error")
    }

    // 사용자 정보 요청
    private fun requestMe(accessToken: AccessToken?) {
        var graphRequest : GraphRequest = GraphRequest.newMeRequest(
            accessToken, GraphRequest.GraphJSONObjectCallback { `object`, response ->
                Log.e("Result", `object`.toString())
            }
        )

        var parameters = Bundle()
        parameters.putString("fields", "id, name, email, gender, birthday")
        graphRequest.parameters = parameters
        graphRequest.executeAsync()
    }
}