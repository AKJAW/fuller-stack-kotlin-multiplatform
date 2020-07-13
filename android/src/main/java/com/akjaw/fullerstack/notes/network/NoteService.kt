package com.akjaw.fullerstack.notes.network

import model.schema.NoteSchema
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface NoteService {

    @GET("notes")
    suspend fun getNotes(): List<NoteSchema>

    @Headers("Content-Type: application/json")
    @POST("add-note")
    suspend fun addNote(@Body addNoteRequest: AddNoteRequest)

    @Headers("Content-Type: application/json")
    @POST("update-note")
    suspend fun updateNote(@Body updateNoteRequest: UpdateNoteRequest)

}
