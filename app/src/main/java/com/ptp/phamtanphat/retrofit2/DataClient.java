package com.ptp.phamtanphat.retrofit2;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Dell on 2/24/2018.
 */

public interface DataClient {

    @Multipart
    @POST("upload.php")
    Call<String> uploadphoto(
            //Thang nay chi la string don gian thi su dung class RequestBody
//            @Part("description") RequestBody description,
            //Thang nay` mo ta qua nhieu nen xai MultipartBody.Part
            @Part MultipartBody.Part photo
    );
}
