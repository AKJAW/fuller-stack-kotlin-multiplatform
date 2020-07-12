package com.akjaw.fullerstack.network

import model.schema.NoteSchema
import retrofit2.http.GET

interface NoteService {

    @GET("notes")
    suspend fun getNotes(): List<NoteSchema>
}
