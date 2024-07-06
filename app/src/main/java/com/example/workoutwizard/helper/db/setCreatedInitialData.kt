package com.example.workoutwizard.helper.db

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

val setCreatedInitialData: (String, FirebaseFirestore) -> Unit = {
    userId, db ->
        db.collection("users")
            .document(userId)
            .update("createdInitialData", true)
            .addOnSuccessListener {
                Log.i("SetCreatedInitialData", "Erfolgreich auf true gesetzt von $userId")
            }
}