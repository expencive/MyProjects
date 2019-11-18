package vk.expencive.instagramm.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_profile.*
import vk.expencive.instagramm.R
import vk.expencive.instagramm.models.User
import vk.expencive.instagramm.utils.FirebaseHelper

class ProfileActivity : BaseActivity(4) {
    private lateinit var mUser: User
    private val TAG = "ProfileActivity"
    private lateinit var mFirebaseHelper: FirebaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupBottomNavigation()
        Log.d(TAG, "onCreate: ")

        edit_profile_btn.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        mFirebaseHelper = FirebaseHelper(this)
        mFirebaseHelper.currentUserReference().addValueEventListener(ValueEventListenerAdapter{

            mUser = it.getValue(User::class.java)!!
            profile_image.loadUserPhoto(mUser.photo)
            username_text.text = mUser.username

        })
    }
}

