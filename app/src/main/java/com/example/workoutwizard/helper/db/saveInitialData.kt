package com.example.workoutwizard.helper.db

import android.util.Log
import com.example.workoutwizard.data.InitialUserDataUiState
import com.google.firebase.firestore.FirebaseFirestore

val saveInitialData: (String, InitialUserDataUiState, FirebaseFirestore, () -> Unit) -> Unit = {
    userId, initData, db, onSuccess ->
        db.collection("initialData")
            .document(userId)
            .set(initData)
            .addOnSuccessListener {
                Log.i("SaveInitialData", "Erfolgreich in der Datenbank gespeichert")
                onSuccess()
            }
}