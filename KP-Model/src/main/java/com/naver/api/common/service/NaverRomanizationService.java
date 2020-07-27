package com.naver.api.common.service;

import com.naver.model.vo.NaverRomanizationResultVO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NaverRomanizationService {
	@GET("/krdict/v1/romanization")
	Call<NaverRomanizationResultVO> getRomanization(@Query("query") String query);

	@GET("/krdict/v1/romanization")
	Call<ResponseBody> getRomanization2(@Query("query") String query);

}
