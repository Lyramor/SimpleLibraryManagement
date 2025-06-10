package com.example.simplelibrarymanagement.di

import com.example.simplelibrarymanagement.data.remote.ApiService
import com.example.simplelibrarymanagement.data.repository.BookRepositoryImpl
import com.example.simplelibrarymanagement.data.repository.CategoryRepositoryImpl
import com.example.simplelibrarymanagement.data.repository.UserRepositoryImpl
import com.example.simplelibrarymanagement.domain.repository.BookRepository
import com.example.simplelibrarymanagement.domain.repository.CategoryRepository
import com.example.simplelibrarymanagement.domain.repository.UserRepository
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

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://ganti.dengan.url.api.anda/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideBookRepository(apiService: ApiService): BookRepository {
        return BookRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(apiService: ApiService): CategoryRepository {
        return CategoryRepositoryImpl(apiService)
    }

    // BARU: Menyediakan UserRepository
    @Provides
    @Singleton
    fun provideUserRepository(apiService: ApiService): UserRepository {
        return UserRepositoryImpl(apiService)
    }
}
