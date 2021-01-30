package com.akjaw.fullerstack.composition.modules

import com.akjaw.fullerstack.notes.network.AuthenticationInterceptor
import com.akjaw.fullerstack.notes.network.NoteService
import com.akjaw.fullerstack.notes.network.RetrofitNoteApi
import com.akjaw.fullerstack.notes.socket.SessionCookieJar
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import network.ApiUrl
import network.NoteApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import retrofit2.Retrofit

val networkModule = DI.Module("networkModule") {
    bind() from singleton { AuthenticationInterceptor(instance()) }
    bind() from singleton { SessionCookieJar() }
    bind() from singleton {
        OkHttpClient.Builder()
            .addInterceptor(instance<AuthenticationInterceptor>())
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            .cookieJar(instance<SessionCookieJar>())
            .build()
    }
    bind<NoteService>() with singleton {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl(ApiUrl.BASE_URL)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .client(instance<OkHttpClient>())
            .build()
            .create(NoteService::class.java)
    }
    bind<NoteApi>() with singleton { RetrofitNoteApi(instance()) }
}
