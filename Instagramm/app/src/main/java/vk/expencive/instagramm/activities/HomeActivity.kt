package vk.expencive.instagramm.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import vk.expencive.instagramm.R
import vk.expencive.instagramm.utils.FirebaseHelper

class HomeActivity : BaseActivity(0) {
    private val TAG = "HomeActivity"
    private lateinit var mFirebase: FirebaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation()
        Log.d(TAG, "onCreate: ")

        mFirebase = FirebaseHelper(this)
        sign_out_text.setOnClickListener { mFirebase.mAuth.signOut() }
        mFirebase.mAuth.addAuthStateListener {
            if (mFirebase.mAuth.currentUser==null){
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        mFirebase.mDatabase.child("feed-posts").child(mFirebase.mAuth.currentUser!!.uid)
            .addValueEventListener(ValueEventListenerAdapter{
                val posts = it.children.map { it.getValue(FeedPost::class.java)!! }
                Log.d(TAG, "feed posts: ${posts.joinToString("\n", "\n")}")
            })

    }

    override fun onStart() {
        super.onStart()
        if (mFirebase.mAuth.currentUser==null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
