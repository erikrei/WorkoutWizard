package com.example.workoutwizard.helper.db

import com.example.workoutwizard.data.UserUiState
import com.example.workoutwizard.data.Workout
import com.example.workoutwizard.data.WorkoutData
import com.example.workoutwizard.helper.getMillisecondsBeginningDay
import com.example.workoutwizard.helper.getMillisecondsOfLocalDate
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.util.UUID

val registerUser: (String, String, FirebaseFirestore, () -> Unit) -> Unit = {
    userId, email, db, onSuccess ->
        val user = UserUiState(
                email = email,
                createdInitialData = false,
                workouts = listOf(
                        Workout(
                                data = WorkoutData.SITUP,
                                completed = true,
                                createdAt = getMillisecondsBeginningDay(),
                                workoutID = UUID.randomUUID().toString()
                        ),
                        Workout(
                                data = WorkoutData.YOGA,
                                completed = false,
                                createdAt = getMillisecondsBeginningDay(),
                                workoutID = UUID.randomUUID().toString()
                        ),
                        Workout(
                                data = WorkoutData.BICEPS_LONG_DUMBELL,
                                completed = false,
                                createdAt = getMillisecondsOfLocalDate(
                                        LocalDate.of(2024, 7, 6)
                                ),
                                workoutID = UUID.randomUUID().toString()
                        )
                )
        )
        db.collection("users")
                .document(userId)
                .set(user)
                .addOnSuccessListener {
                        onSuccess()
                }
}