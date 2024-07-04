package com.example.workoutwizard.helper.db

import com.example.workoutwizard.data.UserUiState
import com.google.firebase.firestore.FirebaseFirestore

val registerUser: (String, String, FirebaseFirestore, () -> Unit) -> Unit = {
    userId, email, db, onSuccess ->
        val user = UserUiState(
                email = email,
                createdInitialData = false,
        )
        db.collection("users")
                .document(userId)
                .set(user)
                .addOnSuccessListener {
                        onSuccess()
                }
}