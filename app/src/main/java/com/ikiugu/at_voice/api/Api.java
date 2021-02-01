package com.ikiugu.at_voice.api;

import com.ikiugu.at_voice.api.dto.CustomerDto;
import com.ikiugu.at_voice.api.model.Agent;
import com.ikiugu.at_voice.api.model.Customer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    /*Customer/Lead Apis*/

    @POST("/customer")
    Call<Boolean> createLead(@Body CustomerDto customerDto);

    @PATCH("/customer/{phoneNumber}")
    Call<Boolean> updateCustomer(@Path("phoneNumber") String phoneNumber);

    /* Agent apis*/

    @POST("/agent")
    Call<Boolean> createAgent(@Body Agent agent);

    @GET("/agent/leads/{phone}")
    Call<List<Customer>> getLeadsByPhoneNumber(@Path("phone") String phoneNumber);

}
