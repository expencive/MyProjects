package vk.expencive.instagramm.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import vk.expencive.instagramm.R

class LoginActivity : AppCompatActivity(), KeyboardVisibilityEventListener,
    View.OnClickListener {


    private val TAG = "LoginActivity"
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.d(TAG, "onCreate: ")
        KeyboardVisibilityEvent.setEventListener(this, this)
        coordinateBtnandImputs(login_btn, email_input, password_input)
        login_btn.setOnClickListener(this)
        create_account_text.setOnClickListener(this)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.login_btn -> {
                val email = email_input.text.toString()
                val password = password_input.text.toString()
                if (validate(email, password)) {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            startActivity<HomeActivity>()
                            finish()
                        }
                    }
                } else {
                    showToast("Please enter email and password")
                }


            }
            R.id.create_account_text ->{
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }

    override fun onVisibilityChanged(isKeyboardOpen: Boolean) {
        if (isKeyboardOpen) {
            create_account_text.visibility = View.GONE
        } else {
            create_account_text.visibility = View.VISIBLE
        }
    }

//    override fun afterTextChanged(p0: Editable?) {
//        login_btn.isEnabled = validate(email_input.text.toString(), password_input.text.toString())
//    }

    private fun validate(email: String, password: String) = email.isNotEmpty()
            && password.isNotEmpty()

}
