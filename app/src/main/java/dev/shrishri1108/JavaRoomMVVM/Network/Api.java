package dev.shrishri1108.JavaRoomMVVM.Network;

import dev.shrishri1108.JavaRoomMVVM.Modal.api_response.ApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    @GET("users?page=2")
    Call<ApiResponse> getAllActors();
}
