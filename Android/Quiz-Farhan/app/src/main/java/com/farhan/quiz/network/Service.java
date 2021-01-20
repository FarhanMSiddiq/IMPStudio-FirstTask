package com.farhan.quiz.network;

import com.farhan.quiz.QuestionsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by delaroy on 4/30/18.
 */

public interface Service {

    @GET("costume_api.php")
    Call<List<QuestionsModel>> getQuestions();

}
