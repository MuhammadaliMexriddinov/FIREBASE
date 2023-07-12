package uz.alphadroid.petchat.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import uz.alphadroid.petchat.R
import uz.alphadroid.petchat.app.App.Companion.activity
import uz.alphadroid.petchat.utils.LocalData.googleSignInClient
import uz.alphadroid.petchat.viewmodels.imp.MainViewModelImp
import com.facebook.FacebookSdk
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var host: NavHostFragment
    private lateinit var graph: NavGraph
    private val viewModel by viewModels<MainViewModelImp>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init Facebook and Google
        FacebookSdk.sdkInitialize(this);
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        activity=this
        host = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        graph = host.navController.navInflater.inflate(R.navigation.nav_graph)
        viewModel.homeScreenLiveData.observe(this, openHomeScreenObserver)
        viewModel.loginScreenLiveData.observe(this, getStartedScreenObserver)

    }

    private val openHomeScreenObserver= Observer<Unit>{
        graph.setStartDestination(R.id.homeScreen)
        host.navController.graph = graph

    }
    private val getStartedScreenObserver=Observer<Unit>{
        graph.setStartDestination(R.id.loginScreen)
        host.navController.graph = graph
    }

}