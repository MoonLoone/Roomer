package com.example.roomer.di

import android.app.Application
import com.example.roomer.data.local.RoomerStoreInterface
import com.example.roomer.data.remote.RoomerApi
import com.example.roomer.data.repository.auth_repository.AuthRepository
import com.example.roomer.data.repository.auth_repository.AuthRepositoryInterface
import com.example.roomer.data.repository.roomer_repository.RoomerRepository
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface
import com.example.roomer.management.PermissionManager
import com.example.roomer.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC
        return logger
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(logger: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }

    @Singleton
    @Provides
    fun provideRoomerApi(okHttpClient: OkHttpClient): RoomerApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RoomerApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(roomerApi: RoomerApi): AuthRepositoryInterface {
        return AuthRepository(roomerApi)
    }

    @Singleton
    @Provides
    fun provideRoomerRepository(
        roomerApi: RoomerApi,
        roomerStore: RoomerStoreInterface,
    ): RoomerRepositoryInterface {
        return RoomerRepository(roomerApi, roomerStore)
    }

    @Singleton
    @Provides
    fun providePermissionManager(
        application: Application,
    ): PermissionManager {
        return PermissionManager(application)
    }
}
