package com.naver.helper;

import java.io.IOException;

import com.naver.api.common.service.NaverTranslationService;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NaverTranslationBuilder {
	private Retrofit retrofit;
    private NaverTranslationService naverTranslationService;
    public static NaverTranslationBuilder naverTranslationBuilder = null;

    public NaverTranslationBuilder(String clientId, String clientSecret) {


    	Interceptor requestInterceptor = new Interceptor() {

    		@Override public Response intercept(Interceptor.Chain chain) throws IOException {
    			Request original = chain.request();
    			Builder builder = original.newBuilder()
    					.header("X-NCP-APIGW-API-KEY-ID", clientId)
    					.header("X-NCP-APIGW-API-KEY", clientSecret)

    					.method(original.method(), original.body());

    			Request request = builder.build(); return chain.proceed(request);
    		}
    	};

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();

    	retrofit = new Retrofit.Builder()
                .baseUrl("https://naveropenapi.apigw.ntruss.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    	naverTranslationService = retrofit.create(NaverTranslationService.class);
    }

    public static NaverTranslationBuilder getInstance(String clientId, String clientSecret) {
    	if(null == naverTranslationBuilder) {
    		naverTranslationBuilder = new NaverTranslationBuilder(clientId, clientSecret);
    	}
    	return naverTranslationBuilder;

    }
    public NaverTranslationService getNaverTranslationService() {
    	return naverTranslationService;
    }
}
