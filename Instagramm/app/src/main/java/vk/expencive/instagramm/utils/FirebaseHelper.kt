package vk.expencive.instagramm.utils

import android.app.Activity
import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import vk.expencive.instagramm.activities.showToast

class FirebaseHelper(private val activity: Activity){

    val mAuth: FirebaseAuth =
        FirebaseAuth.getInstance()
    val  mDatabase: DatabaseReference = FirebaseDatabase.getInstance()
        .reference
    val mStorage: StorageReference = FirebaseStorage.getInstance()
        .reference

    fun updateUser(
        uid: String,
        updates: Map<String, Any?>,
        onSuccess: () -> Unit
    ) {
        mDatabase.child("users").child(uid).updateChildren(updates)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onSuccess()
                } else {
                    activity.showToast(it.exception!!.message!!)
                }
            }

    }


    fun updateEmail(email: String, onSuccess: () -> Unit) {
        mAuth.currentUser!!.updateEmail(email).addOnCompleteListener {

            if (it.isSuccessful) {
                onSuccess()
            } else {
                activity.showToast(it.exception!!.message!!)
            }

        }

    }


    fun reauthenticate(credential: AuthCredential, onSuccess: () -> Unit) {
        mAuth.currentUser!!.reauthenticate(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                onSuccess()
            } else {
                activity.showToast(it.exception!!.message!!)
            }
        }

    }

    fun uploadSharePhoto(localPhotoUrl: Uri, onSuccess: (UploadTask.TaskSnapshot) -> Unit) =
        mStorage.child("users/${currentUid()!!}").child("images")
            .child(localPhotoUrl.lastPathSegment!!)
            .putFile(localPhotoUrl)
            .addOnCompleteListener {
                if (it.isSuccessful)
                    onSuccess(it.result!!)
                else
                    activity.showToast(it.exception!!.message!!)
            }

    fun addSharePhoto(globalPhotoUrl: String, onSuccess: () -> Unit) =
        mDatabase.child("images").child(currentUid()!!)
            .push().setValue(globalPhotoUrl)
            .addOnComplete { onSuccess() }

    private fun Task<Void>.addOnComplete(onSuccess: () -> Unit) {
        addOnCompleteListener {
            if (it.isSuccessful)
                onSuccess()
            else
                activity.showToast(it.exception!!.message!!)
        }
    }

    fun currentUserReference(): DatabaseReference = mDatabase.child("users").child(currentUid()!!)


    fun currentUid(): String? = mAuth.currentUser?.uid



}