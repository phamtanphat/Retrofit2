package com.ptp.phamtanphat.retrofit2;

/**
 * Created by Dell on 2/24/2018.
 */

public class APIUtils {
    public static final String base_url = "";
    public static DataClient getDataClient(){
        return RetrofitClient.getClient(base_url).create(DataClient.class);
    }
}
