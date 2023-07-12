package uz.alphadroid.petchat.di

import android.content.Context
import uz.alphadroid.petchat.app.App
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @[Provides Singleton]
    fun getFirebaseAuth() = FirebaseAuth.getInstance()

    @[Provides Singleton]
    fun getFirebaseDatabase() = FirebaseDatabase.getInstance()


    @[Provides Singleton]
    fun googleSignClient(@ApplicationContext context:Context ): GoogleSignInClient =
        GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN)

}