package com.example.workoutwizard.helper.db

import com.google.firebase.firestore.FirebaseFirestore

val registerUser: (String, String, FirebaseFirestore, () -> Unit) -> Unit = {
    userId, email, db, onSuccess ->
        val user = hashMapOf(
                "email" to email,
                "createdInitialData" to false
        )
        db.collection("users")
                .document(userId)
                .set(user)
                .addOnSuccessListener {
                        onSuccess()
                }
}