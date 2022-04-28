package com.myapplication.service;

import com.myapplication.model.Note;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.UUID;

public interface NoteService {
    @GET("v1/note/{uuid}")
    Call<Note> getNote(@Path("uuid") UUID uuid);

    @GET("v1/note/")
    Call<List<Note>> getNote(@Query("test") String test);

    @GET("/v1/note/")
    Call<List<Note>> allNotes();

    @POST("v1/note/")
    Call<Void> createNote(@Body Note note);

    @DELETE("v1/note/{uuid}")
    Call<Void> deleteNote(@Body UUID uuid);
}