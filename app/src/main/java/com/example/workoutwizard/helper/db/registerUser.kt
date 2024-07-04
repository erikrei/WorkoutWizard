package com.example.workoutwizard.helper.db

import com.google.firebase.firestore.FirebaseFirestore

val registerUser: (String, String, FirebaseFirestore, () -> Unit) -> Unit = {
    userId, email, db, onSucess ->
        val user = hashMapOf(
                "_id" to userId,
                "email" to email
        )
        db.collection("users")
                .add(user)
                .addOnSuccessListener {
                        onSucess()
                }
}