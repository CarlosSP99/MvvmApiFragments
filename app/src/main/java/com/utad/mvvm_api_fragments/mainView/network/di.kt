package com.utad.mvvm_api_fragments.mainView.network

import android.content.Context
import androidx.room.Room
import com.utad.mvvm_api_fragments.mainView.room.AppDatabase
import com.utad.mvvm_api_fragments.mainView.room.SingleMovieDao
import com.utad.mvvm_api_fragments.mainView.room.UserDao
import com.utad.mvvm_api_fragments.mainView.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object di {

    const val MOVIE_DATABASE_NAME = "movie_database"

    // informar la respuesta HTTP
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(
            HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            },
        )
    }.build()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)

            .addConverterFactory(GsonConverterFactory.create())
            .client(client)

            .build()

    }

    @Singleton
    @Provides
    fun provideQuoteApiClient(retrofit: Retrofit): ApiMovieClient {
        return retrofit.create(ApiMovieClient::class.java)
    }


    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context // Proporciona el Context de la aplicación
    ): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext, // Usa el contexto de la aplicación
            AppDatabase::class.java,
            MOVIE_DATABASE_NAME // Nombre de la base de datos
          // para no haber conflictos cuando modifico el esquema mi bbdd
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideSingleMovieDao(appDatabase: AppDatabase): SingleMovieDao {
        return appDatabase.movieDao()
    }



}