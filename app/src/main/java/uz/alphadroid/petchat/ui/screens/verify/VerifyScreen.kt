package uz.alphadroid.petchat.ui.screens.verify

import android.annotation.SuppressLint
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
import uz.alphadroid.petchat.utils.showToast
import uz.alphadroid.petchat.utils.showToastSuccess
import uz.alphadroid.petchat.utils.state
import uz.alphadroid.petchat.viewmodels.imp.VerifyScreenViewModelImp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.verify_screen.*

@AndroidEntryPoint
class VerifyScreen : Fragment(R.layout.verify_screen) {
    private val viewModel by viewModels<VerifyScreenViewModelImp>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.successLiveData.observe(this, successObserver)
        viewModel.errorLiveData.observe(this, errorObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private val progressObserver = Observer<Boolean> { state(it) }
    private val successObserver = Observer<Unit> {
        findNavController().navigate(
            R.id.homeScreen,
            null,
            NavOptions.Builder().setPopUpTo(R.id.loginScreen, true).build()
        )
    }
    private val errorObserver = Observer<String> { showToast(it) }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        val data = arguments?.getSerializable("data") as UserData
        val uid = arguments?.getString("uid")
        description_verify.text = "${data.phone} raqamiga Kod Ketdi !"
        otp_text.addTextChangedListener {
            if (it.toString().length == 6) {
                viewModel.checkCode(it.toString(), uid.toString(), data)
                verify_button.isEnabled = true
            } else {
                verify_button.isEnabled = false
            }
        }
        verify_button.setOnClickListener {
            viewModel.checkCode(it.toString(), uid.toString(), data)
        }
    }
}