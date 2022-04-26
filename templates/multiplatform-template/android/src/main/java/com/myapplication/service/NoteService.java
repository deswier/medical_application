package com.myapplication.service;

import com.myapplication.model.Note;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;
import java.util.UUID;

public interface NoteService {
    @GET("v1/note/{uuid}")
    Call<Note> getNote(@Path("uuid") UUID uuid);

    @GET("v1/note/{test}")
    Call<Note> getNote(@Path("test") String test);

    @GET("/v1/note/")
    Call<List<Note>> allNotes();

    @POST("v1/note/")
    Call<Void> createNote(@Body Note note);
}