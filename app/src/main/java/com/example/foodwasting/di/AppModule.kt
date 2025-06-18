package com.example.foodwasting.di

import android.content.Context
import com.example.foodwasting.classification.TFLiteModel
import com.example.foodwasting.repository.AuthInterceptor
import com.example.foodwasting.repository.JsonHandler
import com.example.foodwasting.repository.MainRepository // Keep this import
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }

    // Teach Hilt how to build an OkHttpClient with our interceptor
    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(json: Json): Retrofit = Retrofit.Builder()
        // The Gemini API endpoint
        .baseUrl("https://generativelanguage.googleapis.com/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): JsonHandler =
        retrofit.create(JsonHandler::class.java)
    @Provides
    @Singleton
    fun provideTFLiteModel(@ApplicationContext context: Context): TFLiteModel {
        return TFLiteModel(context)
    }


}