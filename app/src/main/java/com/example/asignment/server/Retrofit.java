package com.example.asignment.server;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    //Khởi tạo ddooois tượng PolyRetrofit
    public static Sevices sevices;

    public static Sevices getRetrofit() {
        if (sevices == null) {
            retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl("http://asian.dotplays.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            sevices = retrofit.create(Sevices.class);
        }
        return sevices;
    }
}
