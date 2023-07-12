package uz.alphadroid.petchat.di

import uz.alphadroid.petchat.usecases.LoginUseCase
import uz.alphadroid.petchat.usecases.VerifyUseCase
import uz.alphadroid.petchat.usecases.imp.LoginUseCaseImp
import uz.alphadroid.petchat.usecases.imp.VerifyUseCaseImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {
    @Binds
    fun bindLoginScreenUseCase(impl: LoginUseCaseImp):LoginUseCase

    @Binds
    fun bindVerifyScreenUseCase(impl:VerifyUseCaseImp):VerifyUseCase
}