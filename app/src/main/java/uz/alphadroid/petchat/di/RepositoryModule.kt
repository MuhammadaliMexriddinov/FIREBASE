package uz.alphadroid.petchat.di

import uz.alphadroid.petchat.repo.LoginRepository
import uz.alphadroid.petchat.repo.VerifyRepository
import uz.alphadroid.petchat.repo.imp.LoginRepositoryImp
import uz.alphadroid.petchat.repo.imp.VerifyRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun getLoginRepo(impl: LoginRepositoryImp): LoginRepository

    @Binds
    fun getVerifyRepo (impl:VerifyRepositoryImp):VerifyRepository

}