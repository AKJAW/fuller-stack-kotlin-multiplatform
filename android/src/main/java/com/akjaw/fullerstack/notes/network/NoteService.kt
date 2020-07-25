package com.akjaw.fullerstack.notes.network

import model.schema.NoteRequest
import model.schema.NoteSchema
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface NoteService {

    @GET("notes")
    suspend fun getNotes(): List<NoteSchema>

    @Headers("Content-Type: application/json")
    // TODO Is this needed
    @POST("notes")
    suspend fun addNote(@Body addNoteRequest: NoteRequest)

    @Headers("Content-Type: application/json")
    @PATCH("notes/{id}")
    suspend fun updateNote(
        @Path("id") id: Int,
        @Body updateNoteRequest: NoteRequest
    )

    @DELETE("notes/{id}")
    suspend fun deleteNote(
        @Path("id") id: Int
    )

    @HTTP(method = "DELETE", path = "notes", hasBody = true)
    suspend fun deleteNotes(
        @Body noteIds: List<Int>
    )
}
