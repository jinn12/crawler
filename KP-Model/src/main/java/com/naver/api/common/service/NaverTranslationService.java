package com.naver.api.common.service;





import com.naver.model.vo.NaverTranslationResultVO;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NaverTranslationService {

	@FormUrlEncoded
	@POST("/nmt/v1/translation")
	Call<NaverTranslationResultVO> getTranslation(@Field("source") String source, @Field("target") String target, @Field("text") String text);

	/*
	 * @POST("/nmt/v1/translation") Call<ResponseBody> getTranslation(@Body
	 * JSONObject jsonObj);
	 */

	/*
	 * @POST("/nmt/v1/translation") Call<ResponseBody>
	 * getTranslation(@Header("Content-Type") String contentType , @Body String
	 * body);
	 */

}
