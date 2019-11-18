package vk.expencive.instagramm.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_edit_profile.*
import vk.expencive.instagramm.models.User
import vk.expencive.instagramm.views.PasswordDialog
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import vk.expencive.instagramm.R


class EditProfileActivity : AppCompatActivity(), PasswordDialog.Listener {



    private lateinit var mImageUri: Uri
    private val TAKE_PICTURE_REQUEST_CODE = 1
    private val TAG = "EditProfileActivity"
    private lateinit var mUser: User
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference
    private lateinit var mStorage: StorageReference
    private lateinit var mPendingUser: User
    val simpleDateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)


        Log.d(TAG, "onCreate: ")
        close_image.setOnClickListener { finish() }
        save_image.setOnClickListener { updateProfile() }
        change_photo_text.setOnClickListener { takeCameraPicture() }
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference
        mStorage = FirebaseStorage.getInstance().reference
        mDatabase.child("users").child(mAuth.currentUser!!.uid)
            .addListenerForSingleValueEvent(ValueEventListenerAdapter {
                mUser = it.getValue(User::class.java)!!
                name_input.setText(mUser.name, TextView.BufferType.EDITABLE)
                username_input.setText(mUser.username, TextView.BufferType.EDITABLE)
                website_input.setText(mUser.website, TextView.BufferType.EDITABLE)
                email_input.setText(mUser.email, TextView.BufferType.EDITABLE)
                bio_input.setText(mUser.bio, TextView.BufferType.EDITABLE)
                phone_input.setText(mUser.phone?.toString(), TextView.BufferType.EDITABLE)
                profile_image.loadUserPhoto(mUser.photo)

            })

    }



    private fun takeCameraPicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            val imageFile = createImageFile()
            mImageUri = FileProvider.getUriForFile(this,
                "vk.expencive.instagramm.fileprovider",
                imageFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri)
            startActivityForResult(intent, TAKE_PICTURE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == TAKE_PICTURE_REQUEST_CODE && resultCode == RESULT_OK) {
            val uid = mAuth.currentUser!!.uid
            val ref = mStorage.child("users/$uid/photo")
            ref.putFile(mImageUri).addOnCompleteListener {
                if (it.isSuccessful) {
                    ref.downloadUrl.addOnCompleteListener {
                        if (it.isSuccessful){
                        val photoUrl = it.result.toString()
                            mUser = mUser.copy(photo = photoUrl)
                            profile_image.loadUserPhoto(mUser.photo)
                        mDatabase.child("users/$uid/photo").setValue(
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


    private fun createImageFile(): File {

        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "JPEG_${simpleDateFormat.format((Date()))}_",
            ".jpg",
            storageDir
        )
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
            mAuth.currentUser!!.reauthenticate(credential) {
                mAuth.currentUser!!.updateEmail(mPendingUser.email) {
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
        mDatabase.updateUser(mAuth.currentUser!!.uid, updatesMap) {
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

    private fun DatabaseReference.updateUser(
        uid: String,
        updates: Map<String, Any?>,
        onSuccess: () -> Unit
    ) {
        child("users").child(uid).updateChildren(updates)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onSuccess()
                } else {
                    showToast(it.exception!!.message!!)
                }
            }

    }


    private fun FirebaseUser.updateEmail(email: String, onSuccess: () -> Unit) {
        updateEmail(email).addOnCompleteListener {

            if (it.isSuccessful) {
                onSuccess()
            } else {
                showToast(it.exception!!.message!!)
            }

        }

    }


    private fun FirebaseUser.reauthenticate(credential: AuthCredential, onSuccess: () -> Unit) {
        reauthenticate(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                onSuccess()
            } else {
                showToast(it.exception!!.message!!)
            }
        }

    }


}

