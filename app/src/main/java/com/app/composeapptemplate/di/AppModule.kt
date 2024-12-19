package com.app.composeapptemplate.di

import android.content.Context
import com.app.composeapptemplate.data.PreferencesHelperImpl
import com.app.composeapptemplate.utils.ResourceProvider
import com.app.composeapptemplate.network.apiclient.KtorHttpClient
import com.app.composeapptemplate.utils.Constant.PREF_FILE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

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

    @Provides
    fun getHttpClient(client: KtorHttpClient): HttpClient = client.getHttpClient()


}