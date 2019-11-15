package net.mizucoffee.canislupus.repository

import com.google.firebase.firestore.FirebaseFirestore
import net.mizucoffee.canislupus.model.User

class FirebaseRepository {

    fun addUser() {
        val db = FirebaseFirestore.getInstance()
        val user = User("1hui6fswo3", "mizucoffee")
        db.collection("user")
            .document()
            .set(user)
    }
}