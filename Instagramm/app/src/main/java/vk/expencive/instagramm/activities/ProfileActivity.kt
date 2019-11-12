package vk.expencive.instagramm.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_profile.*
import vk.expencive.instagramm.R

class ProfileActivity : BaseActivity(4) {
    private val TAG = "ProfileActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupBottomNavigation()
        Log.d(TAG, "onCreate: ")

        edit_profile_btn.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
    }
}

