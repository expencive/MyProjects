package vk.expencive.instagramm.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_profile_settings.*
import vk.expencive.instagramm.R
import vk.expencive.instagramm.utils.FirebaseHelper

class ProfileSettingsActivity: AppCompatActivity() {
    private lateinit var mFirebase: FirebaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)

        mFirebase = FirebaseHelper(this)

        sign_out_text.setOnClickListener { mFirebase.mAuth.signOut() }
        back_image.setOnClickListener { finish() }


    }

}
