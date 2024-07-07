package com.example.workoutwizard.helper.db

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

val saveCalories: (String, Int, FirebaseFirestore, () -> Unit) -> Unit = {
    userId, calories, db, onSuccess ->
        db.collection("users")
            .document(userId)
            .update("calories", calories)
            .addOnSuccessListener {
                Log.i("SaveCalories", "Erfolgreich Kalorien gespeichert $calories")
                onSuccess()
            }
}