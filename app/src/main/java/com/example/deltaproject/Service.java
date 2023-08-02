package com.example.deltaproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Service {
    @GET("/users/me/")
    Call<ArrayList<String>> getUsers();
    @POST("/token/")
    @FormUrlEncoded
    Call<Data> loginaccess(
            @Field("username") String username,
            @Field("hashpass") String password
    );
    @POST("users/me/days")
    @FormUrlEncoded
    Call<ArrayList<HashMap<String,String>>> getdays(
            @Field("username") String username,
            @Field("day") String day
    );
    @POST("users/me/items")
    @FormUrlEncoded
    Call<ArrayList<HashMap<String,String>>> getitems(
            @Field("username") String username,
            @Field("day") String day,
            @Field("task") String task
    );
    @POST("users/me/schedule")
    @FormUrlEncoded
    Call<ArrayList<HashMap<String,String>>> getschedule(
            @Field("username") String username,
            @Field("day") String day,
            @Field("task") String task
    );
    @POST("users/me/notes")
    @FormUrlEncoded
    Call<ArrayList<HashMap<String,String>>> getnotes(
            @Field("username") String username,
            @Field("day") String day,
            @Field("task") String task
    );
    @POST("users/me/addnotes")
    @FormUrlEncoded
    Call<Void> addnotes(
            @Field("username") String username,
            @Field("day") String day,
            @Field("task") String task,
            @Field("note") String note
            );
    @POST("users/me/sidenote")
    @FormUrlEncoded
    Call<Void> addsidenotes(
            @Field("username") String username,
            @Field("day") String day,
            @Field("task") String task,
            @Field("sidenote") String sidenote
    );
    @POST("users/me/select")
    @FormUrlEncoded
    Call<Void> selects(
            @Field("username") String username,
            @Field("day") String day,
            @Field("task") String task,
            @Field("selects") String selects,
            @Field("item") String item
    );
    @POST("users/me/done")
    @FormUrlEncoded
    Call<Void> donezo(
            @Field("username") String username,
            @Field("day") String day,
            @Field("task") String task,
            @Field("done") String done,
            @Field("slot") String slot
    );
    @POST("/users/me/sptasks")
    @FormUrlEncoded
    Call<Void> addsptasks(
            @Field("username") String username,
            @Field("task") String task,
            @Field("slot") String slot
    );
    @POST("/users/me/getsptasks")
    @FormUrlEncoded
    Call<ArrayList<HashMap<String,String>>> getsptasks(
            @Field("username") String username
    );
    @POST("/users/me/spitems")
    @FormUrlEncoded
    Call<Void> addspitems(
            @Field("username") String username,
            @Field("task") String task,
            @Field("item") String item
    );
    @POST("/users/me/getspitems")
    @FormUrlEncoded
    Call<ArrayList<HashMap<String,String>>> getspitems(
            @Field("username") String username,
            @Field("task") String task
    );
    @POST("/users/me/spnotes")
    @FormUrlEncoded
    Call<Void> addspnotes(
            @Field("username") String username,
            @Field("task") String task,
            @Field("note") String note
    );
    @POST("/users/me/getspnotes")
    @FormUrlEncoded
    Call<ArrayList<HashMap<String,String>>> getspnotes(
            @Field("username") String username,
            @Field("task") String task
    );
    @POST("/users/me/spitemsselect")
    @FormUrlEncoded
    Call<Void> changeselect(
            @Field("username") String username,
            @Field("task") String task,
            @Field("item") String item,
            @Field("select") String select
    );
    @POST("/users/me/clearsptasks")
    @FormUrlEncoded
    Call<Void> clear(
            @Field("username") String username,
            @Field("task") String task
    );
    @POST("/users/me/addmylist")
    @FormUrlEncoded
    Call<Void> addlist(
            @Field("username") String username,
            @Field("listname") String listname
    );
    @POST("/users/me/createmylist")
    @FormUrlEncoded
    Call<Void> createlist(
            @Field("listname") String listname,
            @Field("columnname") String columnname,
            @Field("columntype") String columntype
    );
    @POST("/users/me/domylist")
    @FormUrlEncoded
    Call<Void> getmylist(
            @Field("username") String username,
            @Field("listname") String listname,
            @Field("newlist")List<Object> newlist
    );
    @POST("/users/me/mylistcolumns")
    @FormUrlEncoded
    Call<ArrayList<HashMap<String,String>>> mylistcolumn(
            @Field("listname") String listname
    );
    @POST("/users/me/namemylist")
    @FormUrlEncoded
    Call<ArrayList<HashMap<String,Object>>> namemy(
            @Field("username") String username
    );
    @POST("/users/me/allmylist")
    @FormUrlEncoded
    Call<ArrayList<HashMap<String,String>>> names(
            @Field("username") String username
    );

}
