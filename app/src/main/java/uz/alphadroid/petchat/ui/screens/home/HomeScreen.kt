package uz.alphadroid.petchat.ui.screens.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import uz.alphadroid.petchat.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.home_screen.*

class HomeScreen : Fragment(R.layout.home_screen) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSignOut()
    }

    private fun initSignOut() {
        signoutBtn.setOnClickListener {
            val auth = FirebaseAuth.getInstance()
            auth.signOut()
            findNavController().navigate(
                R.id.loginScreen,
                null,
                NavOptions.Builder().setPopUpTo(R.id.homeScreen, true).build()
            )
        }
    }
}