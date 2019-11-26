package vk.expencive.instagramm.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_feed.view.*
import vk.expencive.instagramm.R
import vk.expencive.instagramm.models.FeedPost
import vk.expencive.instagramm.utils.FirebaseHelper

class HomeActivity : BaseActivity(0), FeedAdapter.Listener {


    private lateinit var mAdapter: FeedAdapter
    private val TAG = "HomeActivity"
    private lateinit var mFirebase: FirebaseHelper
    private var mLikesListeners: Map<String, ValueEventListener> = emptyMap()
    private lateinit var mBaseViewModel: BaseViewModel

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
        mBaseViewModel = ViewModelProviders.of(this)[BaseViewModel::class.java]




    }

    override fun toggleLike(postId: String) {
        val reference = mFirebase.mDatabase.child("likes").child(postId).child(mFirebase.currentUid()!!)
        reference.addListenerForSingleValueEvent(ValueEventListenerAdapter{
            reference.setValueTrueOrRemove(!it.exists())
            })

    }

    override fun loadLikes(postId: String, position: Int) {
        fun createListeners() =
        mFirebase.mDatabase.child("likes")
            .child(postId).addValueEventListener(ValueEventListenerAdapter{
                val userLikes = it.children.map { it.key }.toSet()
                val postLikes = FeedPostLikes(userLikes.size,
                    userLikes.contains(mFirebase.currentUid()))
                mAdapter.updatePostLikes(position, postLikes)
            })
        if (mLikesListeners[postId] == null){
            mLikesListeners += (postId to createListeners()) }


    }

    override fun onDestroy() {
        super.onDestroy()
        mLikesListeners.values.forEach {mFirebase.mDatabase.removeEventListener(it)}
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
                    val posts = it.children.map { it.asFeedPost()!! }
                        .sortedByDescending { it.timestampDate() }
                    Log.d(TAG, "feed posts: ${posts.joinToString("\n", "\n")}")
                    mAdapter = FeedAdapter(this, posts)
                    feed_recycler.adapter = mAdapter
                    feed_recycler.layoutManager = LinearLayoutManager(this)
                })
        }
    }
}

data class FeedPostLikes(val likesCount: Int, val likes: Boolean){

}

class FeedAdapter(private val listener: Listener, private val posts: List<FeedPost>): RecyclerView.Adapter<FeedAdapter.ViewHolder>(){

    private var postLikes: Map<Int, FeedPostLikes> = emptyMap()
    private val defaultPostLikes = FeedPostLikes(0, false)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view =  LayoutInflater.from(parent.context)
           .inflate(R.layout.item_feed, parent, false)
        return ViewHolder(view)
    }

    interface Listener{
        fun toggleLike(postId: String)
        fun loadLikes(id: String, position: Int)
    }

    override fun getItemCount(): Int = posts.size

    fun updatePostLikes( position: Int, likes: FeedPostLikes) {
        postLikes += (position to likes)
        notifyItemChanged(position)

    }




    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feedPost = posts[position]


        val likes = postLikes[position]?: defaultPostLikes

        with(holder.view){
            user_photo_image.loadUserPhoto(feedPost.photo)
            username_text.text = feedPost.username
            post_image.loadImage(feedPost.image)
            if (likes.likesCount == 0){
                likes_text.visibility = View.GONE
            }else{
                likes_text.visibility = View.VISIBLE
                likes_text.text = "${likes.likesCount} likes"
            }

            caption_text.setCaptionText(feedPost.username, feedPost.caption)
            like_image.setOnClickListener { listener.toggleLike(feedPost.id) }
            like_image.setImageResource(if (likes.likes)
                R.drawable.ic_likes else R.drawable.ic_likes_boarder)

            listener.loadLikes(feedPost.id, position)


        }

    }

    private fun TextView.setCaptionText(username: String, caption:String){
        //Spannable : username(bold, clicable) caption
        val usernameSpannable = SpannableString(username)
        usernameSpannable.setSpan(StyleSpan(Typeface.BOLD), 0, usernameSpannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        usernameSpannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                widget.context.showToast("username is clicked")
            }

            override fun updateDrawState(ds: TextPaint) {}
        }, 0, usernameSpannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        text = SpannableStringBuilder().append(usernameSpannable)
            .append(" ").append(caption)
        movementMethod = LinkMovementMethod.getInstance()

    }




    class ViewHolder(val view: View): RecyclerView.ViewHolder(view){}
}
