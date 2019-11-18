package vk.expencive.instagramm.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_share.*
import org.jetbrains.anko.startActivity
import vk.expencive.instagramm.R
import vk.expencive.instagramm.utils.CameraHelper
import vk.expencive.instagramm.utils.FirebaseHelper

class ShareActivity : BaseActivity(2) {
    private val TAG = "ShareActivity"
    private lateinit var mCamera: CameraHelper
    private lateinit var mFirebase: FirebaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        Log.d(TAG, "onCreate: ")
        mCamera = CameraHelper(this)
        mCamera.takeCameraPicture()
        mFirebase = FirebaseHelper(this)
        back_image.setOnClickListener { finish() }

        share_text.setOnClickListener { share() }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode==mCamera.TAKE_REQUEST_CODE){
            if (resultCode == RESULT_OK) {
                Glide.with(this).load(mCamera.imageUri).centerCrop().into(post_image)
            }else{
                finish()
            }
        }
    }


    private fun share() {
        val imageUri = mCamera.imageUri
        if (imageUri != null) {
            mFirebase.uploadSharePhoto(imageUri) {
                it.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                    mFirebase.addSharePhoto(it.toString()) {
                        startActivity(Intent(this, ProfileActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }

//    private fun share() {
////        val imageUri = mCamera.imageUri
////        if (imageUri !=null){
////            //upload picture to user folder <-storage
////            val uid = mFirebase.mAuth.currentUser!!.uid
////            mFirebase.mStorage.child("users").child(uid)
////                .child("images").child(imageUri.lastPathSegment!!).putFile(imageUri)
////                .addOnCompleteListener {
////                    if (it.isSuccessful){
////                        mFirebase.mDatabase.child("images").child(uid).push()
////                            .setValue(imageUri.toString())
////                            .addOnCompleteListener {
////                                if (it.isSuccessful){
////                                    startActivity<ProfileActivity>()
////                                    finish()
////
////                                }else{showToast(it.exception!!.message!!)}
////                            }
////                    }else{showToast(it.exception!!.message!!)}
////                }
////            //add image to user images <- db
////        }
//    }
}

