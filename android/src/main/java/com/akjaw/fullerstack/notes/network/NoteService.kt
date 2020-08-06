package com.akjaw.fullerstack.notes.network

import feature.AddNotePayload
import feature.UpdateNotePayload
import network.NoteSchema
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface NoteService {

    @GET("notes")
    suspend fun getNotes(): List<NoteSchema>

    @POST("notes")
    suspend fun addNote(@Body addNotePayload: AddNotePayload): Int

    @PATCH("notes")
    suspend fun updateNote(@Body updateNoteRequest: UpdateNotePayload)

    @DELETE("notes/{id}")
    suspend fun deleteNote(@Path("id") id: Int)

    @HTTP(method = "DELETE", path = "notes", hasBody = true)
    suspend fun deleteNotes(@Body noteIds: List<Int>)
}
