package com.app.composeapptemplate.di

import android.content.Context
import com.app.composeapptemplate.data.PreferencesHelperImpl
import com.app.composeapptemplate.data.ResourceProvider
import com.app.composeapptemplate.utils.Constant.PREF_FILE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun okHttp(): OkHttpClient {
        val tlsSpecs = listOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT)
        return OkHttpClient.Builder()
            .connectionSpecs(tlsSpecs)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideResourceProvider(@ApplicationContext context: Context) : ResourceProvider {
        return ResourceProvider(context)
    }

    @Provides
    @Singleton
    fun providePreferencesHelper(@ApplicationContext context: Context): PreferencesHelperImpl {
        return PreferencesHelperImpl(context, PREF_FILE_NAME)
    }

}