package com.example.workoutwizard.helper.db

import android.util.Log
import com.example.workoutwizard.data.Workout
import com.google.firebase.firestore.FirebaseFirestore

val setWorkouts: (String, List<Workout>, FirebaseFirestore, () -> Unit) -> Unit = {
    userId, workouts, db, onSuccess ->
        db.collection("users")
            .document(userId)
            .update("workouts", workouts)
            .addOnSuccessListener {
                Log.i("SetWorkouts", "Workouts wurden erfolgreich aktualisiert")
                onSuccess()
            }
}