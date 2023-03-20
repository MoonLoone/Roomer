package com.example.roomer.di

import com.example.roomer.data.remote.RoomerApi
import com.example.roomer.data.repository.RoomerRepository
import com.example.roomer.data.repository.RoomerRepositoryInterface
import com.example.roomer.utils.Consts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomerApi(): RoomerApi {
        return Retrofit.Builder()
            .baseUrl(Consts.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RoomerApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRoomerRepository(roomerApi: RoomerApi): RoomerRepositoryInterface {
        return RoomerRepository(roomerApi)
    }
}
