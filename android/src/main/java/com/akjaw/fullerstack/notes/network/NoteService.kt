package com.akjaw.fullerstack.notes.network

import feature.AddNotePayload
import feature.DeleteNotePayload
import feature.UpdateNotePayload
import network.NoteSchema
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PATCH
import retrofit2.http.POST

interface NoteService {

    @GET("notes")
    suspend fun getNotes(): List<NoteSchema>

    @POST("notes")
    suspend fun addNote(@Body addNotePayload: AddNotePayload): Int

    @PATCH("notes")
    suspend fun updateNote(@Body updateNoteRequest: UpdateNotePayload)

    @HTTP(method = "DELETE", path = "notes", hasBody = true)
    suspend fun deleteNotes(@Body deleteNotePayloads: List<DeleteNotePayload>)
}
