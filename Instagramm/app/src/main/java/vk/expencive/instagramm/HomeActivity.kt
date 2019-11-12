package vk.expencive.instagramm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : BaseActivity(0) {
    private val TAG = "HomeActivity"
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation()
        Log.d(TAG, "onCreate: ")

        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut()
//        mAuth.signInWithEmailAndPassword("expencive@bk.ru", "123456")
//            .addOnCompleteListener {
//                if (it.isSuccessful){
//                    Log.d(TAG, "signIn sucsess")
//                }else{
//                    Log.d(TAG, "signIn failure", it.exception)
//                }
//            }
    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser==null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
