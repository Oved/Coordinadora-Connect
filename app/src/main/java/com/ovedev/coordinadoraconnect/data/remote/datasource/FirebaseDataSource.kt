package com.ovedev.coordinadoraconnect.data.remote.datasource

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.ovedev.coordinadoraconnect.data.remote.response.UserResponse
import javax.inject.Inject

class FirebaseDataSource @Inject constructor(
    private val firebaseFireStore: FirebaseFirestore
) {

    fun saveUser(userId: String, userName: String, validationPeriod: Int, onResultSuccess: (Boolean) -> Unit) {
        val userData = hashMapOf(
            "usuario" to userName,
            "fecha_registro" to Timestamp.now(),
            "periodo_validacion" to validationPeriod
        )

        firebaseFireStore.collection("usuario_login")
            .document(userId)
            .set(userData)
            .addOnSuccessListener {
                println("Usuario y periodo de validación guardados correctamente")
                onResultSuccess(true)
            }
            .addOnFailureListener { e ->
                println("Error al guardar los datos: ${e.message}")
                onResultSuccess(false)
            }
    }

    fun getUser(userId: String, onResult: (UserResponse?) -> Unit) {
        firebaseFireStore.collection("usuario_login")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val usuarioNombre = document.getString("usuario") ?: "Usuario desconocido"
                    val periodoValidacion = document.getLong("periodo_validacion")?.toInt() ?: 0
                    val fechaRegistro = document.getTimestamp("fecha_registro")
                    val userResponse = UserResponse(
                        userId = userId,
                        userName = usuarioNombre,
                        validationPeriod = periodoValidacion,
                        registerDate = fechaRegistro ?: Timestamp.now()
                    )
                    onResult(userResponse)
                } else {
                    onResult(UserResponse(userId = null, "", 0, Timestamp.now()))
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                onResult(null)
            }
    }

    fun updateValidationPeriod(userId: String, newValidationPeriod: Int, onResultSuccess: (Boolean) -> Unit) {
        firebaseFireStore.collection("usuario_login")
            .document(userId)
            .update("periodo_validacion", newValidationPeriod)
            .addOnSuccessListener {
                onResultSuccess(true)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                onResultSuccess(false)
            }
    }

    fun deleteUser(userId: String, onResultSuccess: (Boolean) -> Unit) {
        firebaseFireStore.collection("usuario_login")
            .document(userId)
            .delete()
            .addOnSuccessListener {
                onResultSuccess(true)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                onResultSuccess(false)
            }
    }

}