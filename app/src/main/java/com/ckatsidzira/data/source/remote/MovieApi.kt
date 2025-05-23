package com.ckatsidzira.data.source.remote

import com.ckatsidzira.data.source.remote.model.ApiResponseDto
import com.ckatsidzira.data.source.remote.model.MovieDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("trending/movie/{timeWindow}")
    suspend fun getTrendingMovies(
        @Path("timeWindow") timeWindow: String,
    ): ApiResponseDto<MovieDto>

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
    ): MovieDto
}