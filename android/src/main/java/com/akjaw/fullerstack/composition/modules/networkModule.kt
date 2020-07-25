package com.akjaw.fullerstack.composition.modules

import com.akjaw.fullerstack.notes.network.NoteService
import com.akjaw.fullerstack.notes.network.RetrofitNoteApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import network.NoteApi
import okhttp3.MediaType.Companion.toMediaType
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import retrofit2.Retrofit

val networkModule = DI.Module("networkModule") {
    bind<NoteService>() with singleton {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl("https://fuller-stack-ktor.herokuapp.com")
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
            .create(NoteService::class.java)
    }
    bind<NoteApi>() with singleton { RetrofitNoteApi(instance(), instance()) }
}
