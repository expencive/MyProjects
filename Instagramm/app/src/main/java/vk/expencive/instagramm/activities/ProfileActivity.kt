package vk.expencive.instagramm.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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

        settings_image.setOnClickListener {
            val intent = Intent(this, ProfileSettingsActivity::class.java)
            startActivity(intent)
        }

        add_friends_image.setOnClickListener {
            val intent = Intent(this, AddFriendsActivity::class.java)
            startActivity(intent)
        }

        mFirebaseHelper = FirebaseHelper(this)
        mFirebaseHelper.currentUserReference().addValueEventListener(ValueEventListenerAdapter{

            mUser = it.asUser()!!
            profile_image.loadUserPhoto(mUser.photo)
            username_text.text = mUser.username

        })

        images_recycler.layoutManager = GridLayoutManager(this, 3)
        mFirebaseHelper.mDatabase.child("images").child(mFirebaseHelper.currentUid()!!)
            .addValueEventListener(ValueEventListenerAdapter{
                val images = it.children.map { it.getValue(String::class.java)!! }
                images_recycler.adapter = ImagesAdapter(images)
            })
    }
}

class ImagesAdapter(private val images: List<String>):
    RecyclerView.Adapter<ImagesAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         val image = LayoutInflater.from(parent.context)
             .inflate(R.layout.item_image, parent, false) as ImageView

        return ViewHolder(image)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.loadImage(images[position])

    }


    override fun getItemCount(): Int = images.size;


    class ViewHolder(val image: ImageView): RecyclerView.ViewHolder(image)
}

class SquareImageView(context: Context, attrs: AttributeSet): ImageView (context, attrs){
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

}

