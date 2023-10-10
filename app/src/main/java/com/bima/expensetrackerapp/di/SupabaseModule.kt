package com.bima.expensetrackerapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.createSupabaseClient
import javax.inject.Singleton
import com.bima.expensetrackerapp.BuildConfig
import com.bima.expensetrackerapp.ExpenseTrackerApp
import com.bima.expensetrackerapp.data.repositoryImpl.AuthenticationRepositoryImpl
import com.bima.expensetrackerapp.domain.repository.AuthenticationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.jan.supabase.gotrue.FlowType
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage


@InstallIn(SingletonComponent::class)
@Module
object SupabaseModule {
    @OptIn(SupabaseExperimental::class)
    @Provides
    @Singleton
    fun provideSupabaseClient() : SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.API_KEY
        ) {
            install(Postgrest)
            install(GoTrue) {
                flowType = FlowType.PKCE
                scheme = "app"
                host = "supabase.com"
            }
            install(Storage)
        }
    }

    @Provides
    @Singleton
    fun provideSupabaseDatabase(client: SupabaseClient): Postgrest {
        return client.postgrest
    }

    @Provides
    @Singleton
    fun provideSupabaseGoTrue(client: SupabaseClient): GoTrue {
        return client.gotrue
    }


    @Provides
    @Singleton
    fun provideSupabaseStorage(client: SupabaseClient): Storage {
        return client.storage
    }


    @Provides
    @Singleton
    fun provideAuthRepository(goTrue: GoTrue): AuthenticationRepository {
        return AuthenticationRepositoryImpl(goTrue)
    }

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): ExpenseTrackerApp{
        return app as ExpenseTrackerApp
    }
}