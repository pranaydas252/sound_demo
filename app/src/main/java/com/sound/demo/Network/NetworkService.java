package com.sound.demo.Network;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface NetworkService {


    @POST("like_audio.php")
    Call<String> likeAudio(@Header("Username") String name, @Header("Email") String email, @Header("Audiobookid") String audioBook, @Header("Likestatus") String status);

    @POST("add_comment.php")
    Call<String> addComment(@Header("Username") String name, @Header("Email") String email, @Header("Audiobookid") String audioBook, @Header("Commentstatus") String status);

    @POST("add_rating.php")
    Call<String> addRating(@Header("Username") String name, @Header("Email") String email, @Header("Audiobookid") String audioBook, @Header("Ratingstatus") String status);

    @POST("add_user.php")
    Call<String> addUser(@Header("Username") String name, @Header("Email") String email);

    @POST("notification.php")
    Call<String> sendCloudMessagingToken(@Header("Username") String name, @Header("Email") String email, @Header("Fms") String key);

}