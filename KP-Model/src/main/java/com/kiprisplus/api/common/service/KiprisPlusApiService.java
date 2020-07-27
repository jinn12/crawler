package com.kiprisplus.api.common.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KiprisPlusApiService {

	@GET("/kipo-api/kipi/trademarkInfoSearchService/getAdvancedSearch")
	Call<ResponseBody> getTrademarkAdvancedSearch(
			@Query("applicationDate") String applicationDate
			, @Query("trademark") boolean trademark
			, @Query("serviceMark") boolean serviceMark
			, @Query("trademarkServiceMark") boolean trademarkServiceMark
			, @Query("businessEmblem") boolean businessEmblem
			, @Query("collectiveMark") boolean collectiveMark
			, @Query("internationalMark") boolean internationalMark
			, @Query("character") boolean character
			, @Query("figure") boolean figure
			, @Query("compositionCharacter") boolean compositionCharacter
			, @Query("figureComposition") boolean figureComposition
			, @Query("numOfRows") int numOfRows
			, @Query("pageNo") int pageNo
			//, @Query(value = "ServiceKey", encoded = true) String ServiceKey
	);

	@GET("/kipo-api/kipi/trademarkInfoSearchService/getBibliographyDetailInfoSearch")
	Call<ResponseBody> getTrademarkBibliographyDetailInfoSearch(
			@Query("applicationNumber") String applicationNumber
	);

	@GET("/kipo-api/kipi/trademarkInfoSearchService/getChangeInfoSearch")
	Call<ResponseBody> getTrademarkChangeInfoSearch(
			@Query("date") String date
	);

	@GET("/kipo-api/kipi/patUtiModInfoSearchSevice/getAdvancedSearch")
	Call<ResponseBody> getPatentAdvancedSearch(
			@Query("applicationDate") String applicationDate
			, @Query("numOfRows") int numOfRows
			, @Query("pageNo") int pageNo
	);

	@GET("/kipo-api/kipi/patUtiModInfoSearchSevice/getBibliographyDetailInfoSearch")
	Call<ResponseBody> getPatentBibliographyDetailInfoSearch(
			@Query("applicationNumber") String applicationNumber
	);
	@GET("/kipo-api/kipi/patUtiModInfoSearchSevice/getChangeInfoSearch")
	Call<ResponseBody> getPatentChangeInfoSearch(
			@Query("date") String date
	);

	@GET("/openapi/rest/patUtiModInfoSearchSevice/patentCpcInfo")
	Call<ResponseBody> getPatentCpcInfo(
			@Query("applicationNumber") String applicationNumber
			, @Query("accessKey") String accessKey
	);

	@GET("/openapi/rest/KpaBibliographicService/summation")
	Call<ResponseBody> getKpaInfo(
			@Query("applicationNumber") String applicationNumber
			, @Query("accessKey") String accessKey
	);
}
