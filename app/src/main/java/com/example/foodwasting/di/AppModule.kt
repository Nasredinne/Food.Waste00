package com.example.foodwasting.di

import com.example.foodwasting.model.BASE_URL
import com.example.foodwasting.repository.JsonHandler
import com.example.foodwasting.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {




    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit):JsonHandler =
        retrofit.create(JsonHandler::class.java)


    @Singleton
    @Provides
    fun provideRepository(api: JsonHandler): MainRepository = MainRepository(api)
}