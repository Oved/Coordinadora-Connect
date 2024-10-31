package com.ovedev.coordinadoraconnect.data.remote.datasource

import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.ovedev.coordinadoraconnect.data.remote.response.LoginResponse
import com.ovedev.coordinadoraconnect.data.remote.response.PdfResponse
import com.ovedev.coordinadoraconnect.presentation.model.Position
import com.ovedev.coordinadoraconnect.utils.Constant
import org.json.JSONArray
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
                val loginResponse = if (response.optBoolean("isError", false)) {
                    LoginResponse(
                        isError = true,
                        message = response.optString("message", "Error desconocido")
                    )
                } else {
                    LoginResponse(
                        isError = false,
                        dataUserName = response.optString("data", ""),
                        validationPeriod = response.optInt("perido_validacion", 2)
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
        request.retryPolicy = getDefaultRetryPolicy()
        requestQueue.add(request)
    }

    fun getPdfImage(onResult: (PdfResponse) -> Unit) {

        val request = JsonObjectRequest(
            Request.Method.GET, Constant.URL_GET_IMAGE_PDF, JSONObject(),
            { response ->
                val base64Response = if (response.optBoolean("isError", false)) {
                    PdfResponse(isError = true)
                } else {
                    PdfResponse(
                        isError = false,
                        base64Data = response.optString("base64"),
                        positions = extractPositionsFromJson(response)
                    )
                }
                onResult(base64Response)
            }, { error ->
                error.printStackTrace()
                onResult(PdfResponse(isError = true))
            }
        )
        request.retryPolicy = getDefaultRetryPolicy()
        requestQueue.add(request)
    }

    private fun extractPositionsFromJson(response: JSONObject): List<Position> {
        val positions = mutableListOf<Position>()
        val positionsJsonArray = response.optJSONArray("posiciones") ?: JSONArray()

        for (i in 0 until positionsJsonArray.length()) {
            val positionJson = positionsJsonArray.getJSONObject(i)

            val latitude = positionJson.optDouble("latitud", 0.0)
            val longitude = positionJson.optDouble("longitud", 0.0)

            positions.add(Position(latitude, longitude))
        }
        return positions
    }

    private fun getDefaultRetryPolicy(): DefaultRetryPolicy {
        return DefaultRetryPolicy(
            20000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
    }

}