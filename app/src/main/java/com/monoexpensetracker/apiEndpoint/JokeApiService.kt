package com.monoexpensetracker.apiEndpoint

import com.monoexpensetracker.dataclass.JokeResponse
import retrofit2.Call
import retrofit2.http.GET

interface JokeApiService {
    @GET("/random_joke")
    fun getRandomJoke(): Call<JokeResponse>
}