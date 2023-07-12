package uz.alphadroid.petchat.ui.screens.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import uz.alphadroid.petchat.R
import uz.alphadroid.petchat.data.model.UserData
import uz.alphadroid.petchat.utils.LocalData.REQUEST_CODE
import uz.alphadroid.petchat.utils.checkPhone
import uz.alphadroid.petchat.utils.LocalData.googleSignInClient
import uz.alphadroid.petchat.utils.showToast
import uz.alphadroid.petchat.utils.state
import uz.alphadroid.petchat.viewmodels.imp.LoginScreenViewModelImp
import com.facebook.*
import com.facebook.CallbackManager.Factory.create
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_screen.*
import java.util.*

@AndroidEntryPoint
class LoginScreen : Fragment(R.layout.login_screen) {

    private val viewModel by viewModels<LoginScreenViewModelImp>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(requireActivity());
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.successLiveData.observe(this, successObserver)
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.accountLiveData.observe(this) {
            findNavController().navigate(
                R.id.homeScreen,
                null,
                NavOptions.Builder().setPopUpTo(R.id.loginScreen, true).build()
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLoginWithPhone()
        initLoginWithGoogle()
        initLoginWithFacebook()
        initLoginWithTwitter()
    }


    private fun initLoginWithPhone() {
        phoneEditTxt.addTextChangedListener {
            val phoneTxt = StringBuilder()
            it.toString().split(" ").onEach {
                phoneTxt.append(it)
            }
            stateWithButton(phoneTxt.toString().checkPhone() && nameTxt.text.isNotEmpty())
        }
        nameTxt.addTextChangedListener {
            if (it.toString().trim().isEmpty()) stateWithButton(false)
            else stateWithButton(
                "+998${phoneEditTxt.unMaskedText}".checkPhone() && this.toString().trim()
                    .isNotEmpty()
            )
        }


        login_btn.setOnClickListener {
            viewModel.loginWithPhoneName(
                "+998" + phoneEditTxt.unMaskedText.toString(),
                nameTxt.text.toString()
            )
        }
    }

    private fun initLoginWithGoogle() {
        google_btn.setOnClickListener {
            googleSignInClient!!.signInIntent.also {
                startActivityForResult(it, REQUEST_CODE)
            }
        }

    }

    private fun initLoginWithFacebook() {
        facebook_btn.setOnClickListener {
            var loginManager = LoginManager.getInstance()
            var callbackManager = create()

            loginManager
                .registerCallback(
                    callbackManager,
                    object : FacebookCallback<LoginResult> {
                        override fun onSuccess(result: LoginResult) {
                            viewModel.loginFaceBook(result.accessToken)
                        }

                        override fun onCancel() {

                        }

                        override fun onError(error: FacebookException) {
                            showToast(error.message.toString())
                        }
                    })
            loginManager.logInWithReadPermissions(
                requireActivity(),
                Arrays.asList(
                    "email",
                    "public_profile",
                    "user_birthday"
                )
            );
        }
    }

    private fun initLoginWithTwitter() {
        twitter_btn.setOnClickListener {
            viewModel.loginTwitter()
        }
    }

    private fun stateWithButton(boolean: Boolean) {
        login_btn.isEnabled = boolean
    }

    private val successObserver = Observer<String> {
        val data =
            UserData(phone = "+998" + phoneEditTxt.unMaskedText, name = nameTxt.text.toString())
        val bundle = Bundle()
        bundle.putSerializable("data", data)
        bundle.putString("uid", it)
        findNavController().navigate(R.id.verifyScreen, bundle)
    }

    private val errorObserver = Observer<String> {
        showToast(it)
    }

    private val progressObserver = Observer<Boolean> {
        state(it)
    }

    private fun googleAuthWithFirebase(authGoogleSignInAccount: GoogleSignInAccount) {
        viewModel.googleSign(authGoogleSignInAccount)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
            account.apply {
                googleAuthWithFirebase(this)
            }
        }
    }

}