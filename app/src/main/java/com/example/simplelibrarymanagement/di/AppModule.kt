package com.example.simplelibrarymanagement.di

import com.example.simplelibrarymanagement.data.remote.ApiService
import com.example.simplelibrarymanagement.data.remote.AuthInterceptor
import com.example.simplelibrarymanagement.data.remote.AuthTokenManager
import com.example.simplelibrarymanagement.data.repository.AuthRepositoryImpl
import com.example.simplelibrarymanagement.data.repository.BookRepositoryImpl
import com.example.simplelibrarymanagement.data.repository.CategoryRepositoryImpl
import com.example.simplelibrarymanagement.data.repository.UserRepositoryImpl
import com.example.simplelibrarymanagement.domain.repository.AuthRepository
import com.example.simplelibrarymanagement.domain.repository.BookRepository
import com.example.simplelibrarymanagement.domain.repository.CategoryRepository
import com.example.simplelibrarymanagement.domain.repository.UserRepository
import com.example.simplelibrarymanagement.data.repository.BorrowRepositoryImpl
import com.example.simplelibrarymanagement.domain.repository.BorrowRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "http://10.0.2.2:5000/"

    @Provides
    @Singleton
    fun provideAuthTokenManager(): AuthTokenManager = AuthTokenManager()

    @Provides
    @Singleton
    fun provideApiService(tokenManager: AuthTokenManager): ApiService {
        val authInterceptor = AuthInterceptor(tokenManager)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(apiService: ApiService): AuthRepository {
        return AuthRepositoryImpl(apiService)
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

    @Provides
    @Singleton
    fun provideUserRepository(apiService: ApiService): UserRepository {
        return UserRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideBorrowRepository(apiService: ApiService): BorrowRepository {
        return BorrowRepositoryImpl(apiService)
    }
}