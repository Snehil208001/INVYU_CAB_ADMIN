package com.tride.admin.core.di


import com.google.firebase.auth.FirebaseAuth
import com.tride.admin.data.remote.AdminApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // 1. Provide Firebase Auth
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    // 2. Provide Retrofit Instance
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://ovlo8ek40d.execute-api.us-east-1.amazonaws.com/riding_app/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 3. Provide AdminApiService (This fixes your error)
    @Provides
    @Singleton
    fun provideAdminApiService(retrofit: Retrofit): AdminApiService {
        return retrofit.create(AdminApiService::class.java)
    }
}