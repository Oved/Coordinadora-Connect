package com.ovedev.coordinadoraconnect.data.remote

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.ovedev.coordinadoraconnect.data.remote.response.LoginResponse
import com.ovedev.coordinadoraconnect.utils.Constant
import org.json.JSONObject
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val requestQueue: RequestQueue
) {

    fun login(
        username: String,
        password: String,
        onResult: (LoginResponse) -> Unit
    ) {
        val params = JSONObject().apply {
            put("usuario", username)
            put("password", password)
        }

        val request = JsonObjectRequest(
            Request.Method.POST, Constant.URL_VALIDATE_USER, params,
            { response ->
                val loginResponse = if (response.getBoolean("isError")) {
                    LoginResponse(
                        isError = true,
                        message = response.getString("data")
                    )
                } else {
                    LoginResponse(
                        isError = false,
                        message = response.getString("data"),
                        validationPeriod = response.getInt("perido_validacion")
                    )
                }
                onResult(loginResponse)
            },
            { error ->
                val loginResponse = LoginResponse(
                    isError = true,
                    message = error.message ?: "Error desconocido"
                )
                onResult(loginResponse)
            }
        )
        requestQueue.add(request)
    }
}