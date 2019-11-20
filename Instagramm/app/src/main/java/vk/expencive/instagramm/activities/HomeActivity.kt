package vk.expencive.instagramm.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_feed.view.*
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
        mFirebase.mAuth.addAuthStateListener {
            if (mFirebase.mAuth.currentUser==null){
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }


    }

    override fun onStart() {
        super.onStart()
        val currentUser = mFirebase.mAuth.currentUser
        if (currentUser == null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }else {
            mFirebase.mDatabase.child("feed-posts").child(currentUser.uid)
                .addValueEventListener(ValueEventListenerAdapter {
                    val posts = it.children.map { it.getValue(FeedPost::class.java)!! }
                    Log.d(TAG, "feed posts: ${posts.joinToString("\n", "\n")}")
                    feed_recycler.adapter = FeedAdapter(posts)
                    feed_recycler.layoutManager = LinearLayoutManager(this)
                })
        }
    }
}

class FeedAdapter(private val posts: List<FeedPost>): RecyclerView.Adapter<FeedAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view =  LayoutInflater.from(parent.context)
           .inflate(R.layout.item_feed, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = posts.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.post_image.loadImage(posts[position].image)
    }

    private fun ImageView.loadImage(image: String){
        Glide.with(this).load(image).centerCrop().into(this)
    }


    class ViewHolder(val view: View): RecyclerView.ViewHolder(view){}
}
