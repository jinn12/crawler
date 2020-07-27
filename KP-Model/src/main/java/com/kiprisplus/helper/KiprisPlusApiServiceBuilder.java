package com.kiprisplus.helper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.kiprisplus.api.common.service.KiprisPlusApiService;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class KiprisPlusApiServiceBuilder {
	private Retrofit retrofit;
    private KiprisPlusApiService kiprisPlusApiService;

    public KiprisPlusApiServiceBuilder(String serviceKey) {


		Interceptor requestInterceptor = new Interceptor() {

			@Override
			public Response intercept(Interceptor.Chain chain) throws IOException {
				Request originalRequest = chain.request();

				HttpUrl.Builder urlBuilder = originalRequest.url().newBuilder();
				urlBuilder.setQueryParameter("ServiceKey", serviceKey);

				Request request = originalRequest.newBuilder().url(urlBuilder.build().toString().replaceAll("%3D", "=")).build();
				return chain.proceed(request);
			}
		};

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(5,  TimeUnit.MINUTES)
                .readTimeout(5,  TimeUnit.MINUTES)
                .writeTimeout(5,  TimeUnit.MINUTES)
                .build();

    	retrofit = new Retrofit.Builder()
                .baseUrl("http://plus.kipris.or.kr")
                .client(client)
                .build();

    	kiprisPlusApiService = retrofit.create(KiprisPlusApiService.class);
    }

    public KiprisPlusApiService getKiprisPlusApiService() {
    	return kiprisPlusApiService;
    }
}