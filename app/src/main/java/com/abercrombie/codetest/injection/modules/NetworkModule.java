package com.abercrombie.codetest.injection.modules;

import com.abercrombie.codetest.utils.OkHttpBuilder;
import com.abercrombie.codetest.services.DataService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

  @Provides
  @Singleton Gson provideGson() {
    GsonBuilder builder = new GsonBuilder();
    return builder.create();
  }

  @Provides
  @Singleton Retrofit provideRetrofit(Gson gson) {
    return new Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl("https://www.abercrombie.com/")
        .client(OkHttpBuilder.createClient())
        .build();
  }

  @Provides
  @Singleton DataService provideDataService(Retrofit retrofit) {
    return retrofit.create(DataService.class);
  }
}
