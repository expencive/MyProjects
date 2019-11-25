package vk.expencive.instagramm.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import kotlinx.android.synthetic.main.activity_edit_profile.*
import vk.expencive.instagramm.models.User
import vk.expencive.instagramm.views.PasswordDialog
import vk.expencive.instagramm.R
import vk.expencive.instagramm.utils.CameraHelper
import vk.expencive.instagramm.utils.FirebaseHelper


class EditProfileActivity : AppCompatActivity(), PasswordDialog.Listener {


    private lateinit var mFirebaseHelper: FirebaseHelper
    private val TAG = "EditProfileActivity"
    private lateinit var mUser: User
    private lateinit var mPendingUser: User
    private lateinit var cameraHelper: CameraHelper



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        cameraHelper = CameraHelper(this)
        mFirebaseHelper = FirebaseHelper(this)


        Log.d(TAG, "onCreate: ")
        close_image.setOnClickListener { finish() }
        save_image.setOnClickListener { updateProfile() }
        change_photo_text.setOnClickListener { cameraHelper.takeCameraPicture() }

        mFirebaseHelper.currentUserReference()
            .addListenerForSingleValueEvent(ValueEventListenerAdapter {
                mUser = it.asUser()!!
                name_input.setText(mUser.name)
                username_input.setText(mUser.username)
                website_input.setText(mUser.website)
                email_input.setText(mUser.email)
                bio_input.setText(mUser.bio)
                phone_input.setText(mUser.phone?.toString(), TextView.BufferType.EDITABLE)
                profile_image.loadUserPhoto(mUser.photo)

            })

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == cameraHelper.TAKE_REQUEST_CODE && resultCode == RESULT_OK) {
            val uid = mFirebaseHelper.currentUid()!!
            val ref = mFirebaseHelper.mStorage.child("users/$uid/photo")
            ref.putFile(cameraHelper.imageUri!!).addOnCompleteListener {
                if (it.isSuccessful) {
                    ref.downloadUrl.addOnCompleteListener {
                        if (it.isSuccessful){
                        val photoUrl = it.result.toString()
                            mUser = mUser.copy(photo = photoUrl)
                            profile_image.loadUserPhoto(mUser.photo)
                        mFirebaseHelper.mDatabase.child("users/$uid/photo").setValue(
                            photoUrl
                        ).addOnCompleteListener {
                            if (it.isSuccessful) {
                                Log.d(TAG, "-------$photoUrl")
                            } else {
                                showToast(it.exception!!.message!!)
                            }
                        }

                    }

                    }
                } else {
                    showToast(it.exception!!.message!!)
                }
            }



        }

    }



    private fun updateProfile() {
        //get user from inputs
        //validate

        mPendingUser = readInputs()
        val error = validate(mPendingUser)
        if (error == null) {
            //save user
            if (mPendingUser.email == mUser.email) {
                //update user
                updateUser(mPendingUser)

            } else {
                //show dialog
                PasswordDialog().show(supportFragmentManager, "password_dialog")
            }
        } else {
            showToast(error)
        }

    }

    private fun readInputs(): User {
        return User(
            name = name_input.text.toString(),
            username = username_input.text.toString(),
            email = email_input.text.toString(),
            website = website_input.text.toStringOrNull(),
            bio = bio_input.text.toStringOrNull(),
            phone = phone_input.text.toString().toLongOrNull()

        )
    }


    override fun onPasswordConfirm(password: String) {
        //re auntithicate
        if (password.isNotEmpty()) {
            val credential = EmailAuthProvider.getCredential(mUser.email, password)
            mFirebaseHelper.reauthenticate(credential) {
                mFirebaseHelper.updateEmail(mPendingUser.email) {
                    updateUser(mPendingUser)
                }
            }
        } else {
            showToast("You sould enter your password")
        }


    }


    private fun updateUser(user: User) {
        val updatesMap = mutableMapOf<String, Any?>()
        if (user.name != mUser.name) updatesMap["name"] = user.name
        if (user.username != mUser.username) updatesMap["username"] = user.username
        if (user.website != mUser.website) updatesMap["website"] = user.website
        if (user.bio != mUser.bio) updatesMap["bio"] = user.bio
        if (user.email != mUser.email) updatesMap["email"] = user.email
        if (user.phone != mUser.phone) updatesMap["phone"] = user.phone
        mFirebaseHelper.updateUser(mFirebaseHelper.currentUid()!!, updatesMap) {
            showToast("Profile saved")
            finish()
        }
    }

    private fun validate(user: User): String? =
        when {
            user.name.isEmpty() -> "Please enter name"
            user.username.isEmpty() -> "Please enter user name"
            user.email.isEmpty() -> "Please enter email"
            else -> null
        }

    
}

