package com.mqa.smartspeaker.core.di

import com.mqa.smartspeaker.core.data.SmartSpeakerRepository
import com.mqa.smartspeaker.core.domain.repository.ISmartSpeakerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(tourismRepository: SmartSpeakerRepository): ISmartSpeakerRepository

}