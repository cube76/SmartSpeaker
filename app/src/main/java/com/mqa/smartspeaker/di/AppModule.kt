package com.mqa.smartspeaker.di

import com.mqa.smartspeaker.core.domain.usecase.SmartSpeakerInteractor
import com.mqa.smartspeaker.core.domain.usecase.SmartSpeakerUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideTourismUseCase(tourismInteractor: SmartSpeakerInteractor): SmartSpeakerUseCase

}
