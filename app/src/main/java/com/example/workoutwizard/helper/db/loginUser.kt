package com.example.workoutwizard.helper.db

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

val loginUser: (String, FirebaseFirestore, (DocumentSnapshot) -> Unit) -> Unit = {
    userId, db, onSuccess ->
        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener {
                document -> onSuccess(document)
            }
}