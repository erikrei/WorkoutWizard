package com.example.workoutwizard.helper.db

import android.util.Log
import com.example.workoutwizard.data.UserUiState
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

val loginUser: (String, FirebaseFirestore, (UserUiState?) -> Unit) -> Unit = {
    userId, db, onSuccess ->
        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener {
                document ->
                    val user = document.toObject(UserUiState::class.java)
                    onSuccess(user)
            }
}