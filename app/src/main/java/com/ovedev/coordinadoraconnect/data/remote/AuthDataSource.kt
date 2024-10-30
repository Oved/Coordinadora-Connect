package com.ovedev.coordinadoraconnect.data.remote

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.ovedev.coordinadoraconnect.data.remote.response.LoginResponse
import com.ovedev.coordinadoraconnect.data.remote.response.PdfResponse
import com.ovedev.coordinadoraconnect.presentation.model.Position
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

    fun getPdfImage(onResult: (PdfResponse) -> Unit) {

        val request = JsonObjectRequest(
            Request.Method.POST, Constant.URL_GET_IMAGE_PDF, JSONObject(),
            { response ->
                val base64Response = if (response.getBoolean("isError")) {
                    PdfResponse(isError = true)
                } else {
                    PdfResponse(
                        isError = false,
                        base64Data = response.getString("base64Data"),
                        positions = extractPositionsFromJson(response)
                    )
                }
                onResult(base64Response)
            },
            { onResult(PdfResponse(isError = true)) }
        )

        requestQueue.add(request)
    }

    private fun extractPositionsFromJson(response: JSONObject): List<Position> {
        val positions = mutableListOf<Position>()
        val positionsJsonArray = response.getJSONArray("posiciones")

        for (i in 0 until positionsJsonArray.length()) {
            val positionJson = positionsJsonArray.getJSONObject(i)

            val latitude = positionJson.getDouble("latitud")
            val longitude = positionJson.getDouble("longitud")

            positions.add(Position(latitude, longitude))
        }
        return positions
    }

}