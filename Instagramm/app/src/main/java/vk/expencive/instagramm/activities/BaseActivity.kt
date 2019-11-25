package vk.expencive.instagramm.activities

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.bottom_navigation_view.*
import vk.expencive.instagramm.R

abstract class BaseActivity(val navNumber: Int): AppCompatActivity() {
    private val TAG = "BaseActivity"

    fun setupBottomNavigation(){
        bottom_navigation_view.setTextVisibility(false)
        bottom_navigation_view.setIconSize(29f, 29f)
        bottom_navigation_view.enableItemShiftingMode(false)
        bottom_navigation_view.enableShiftingMode(false)
        bottom_navigation_view.enableAnimation(false)
        for (i in 0..bottom_navigation_view.menu.size()){
            bottom_navigation_view.setIconTintList(i, null)
        }
        bottom_navigation_view.setOnNavigationItemSelectedListener {
            val nextActivity =
                when (it.itemId){
                    R.id.nav_item_home -> HomeActivity::class.java
                    R.id.nav_item_search -> SearchActivity::class.java
                    R.id.nav_item_share -> ShareActivity::class.java
                    R.id.nav_item_likes -> LikesActivity::class.java
                    R.id.nav_item_profile -> ProfileActivity::class.java
                    else -> { Log.d(TAG, "unknoun nav_item $it")
                        null}
                }
            if (nextActivity!=null){
                val intent = Intent(this, nextActivity)
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                overridePendingTransition(0,0)
                true
            }else{false }


        }

    }

    override fun onResume() {
        super.onResume()
        if (bottom_navigation_view!=null){
            bottom_navigation_view.menu.getItem(navNumber).isChecked=true
        }

    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0,0)
    }


}