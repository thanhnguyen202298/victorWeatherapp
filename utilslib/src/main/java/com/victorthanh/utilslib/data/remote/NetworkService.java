package com.victorthanh.utilslib.data.remote;

import android.util.Log;

import com.google.gson.Gson;
import com.victorthanh.utilslib.BuildConfig;
import com.victorthanh.utilslib.data.preference.PreferenceKey;
import com.victorthanh.utilslib.data.preference.PreferencesImp;
import com.victorthanh.utilslib.domain.model.opencage.CityInfo;
import com.victorthanh.utilslib.domain.model.openweather.Weather;
import com.victorthanh.utilslib.domain.model.openweather.WeatherInfo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class NetworkService {

    public static Gson gson;
    private static OpenCage openData;
    private static OpenWeather openWeather;
    public static PreferencesImp preferences;
    private static NetworkResponseTransformer<CityInfo> networkResponseTransformerCity;
    private static NetworkResponseTransformer<WeatherInfo> networkResponseTransformerWeather;

    public static String OPEN_CAGE_URL = "https://api.opencagedata.com/geocode/v1/";
    public static String OPEN_WEATHER_URL = "https://api.openweathermap.org/data/2.5/";
//    public static String BASE_URL = BuildConfig.END_POINT;

    public static void RefreshToken() throws IOException {
        //final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add("refreshToken", preferences.get(PreferenceKey.REFRESH_TOKEN, String.class));
        FormBody formBody = formBodyBuilder.build();
        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/json")
                .url(OPEN_CAGE_URL + "membership/api/v0/users/authentication")
                .put(formBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            try {
                String result = response.body().string();
                if (response.code() == 200) {
                    return;
                } else {
                    preferences.clear(PreferenceKey.REFRESH_TOKEN);
                    preferences.clear(PreferenceKey.ACCESS_TOKEN);
                }
                Log.d("TAG REFRESH TOKEN", result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void RefreshLogin() throws IOException {
        //final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
//
//        formBodyBuilder.add("username", DataHelper.Companion.getUserNameLogged());
//        formBodyBuilder.add("password", DataHelper.Companion.getPasswordLogged());
        formBodyBuilder.add("rememberMe", "true");
        FormBody formBody = formBodyBuilder.build();
        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/json")
                .url(OPEN_CAGE_URL + "membership/api/v0/users/authentication")
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            try {
                String result = response.body().string();
                if (response.code() == 200) {

                    return;
                } else {
                    preferences.clear(PreferenceKey.REFRESH_TOKEN);
                    preferences.clear(PreferenceKey.ACCESS_TOKEN);
                }
                Log.d("TAG LOGIN BACKGROUND", result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void init(PreferencesImp preferences) {
        gson = new Gson();
        NetworkService.preferences = preferences;
        networkResponseTransformerCity = new NetworkResponseTransformer();
        networkResponseTransformerWeather = new NetworkResponseTransformer();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(12, TimeUnit.SECONDS)
                .connectTimeout(12, TimeUnit.SECONDS)
                .addInterceptor(BuildConfig.DEBUG
                        ? new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                        : new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE));

//        httpClient.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request origin = chain.request();
//                Request request;
//                if (origin.url().toString().contains("api/v0/users/authentication"))
//                    request = origin.newBuilder()
//                            .addHeader("content-Type", "application/json")
//                            .method(origin.method(), origin.body()).build();
//                else
//                    request = origin.newBuilder()
//                            .addHeader("content-Type", "application/json")
//                            .header("Authorization", "Bearer " + preferences.get(PreferenceKey.ACCESS_TOKEN, String.class))
//                            .method(origin.method(), origin.body()).build();
//                try {
//                    Response response = chain.proceed(request);
//                    boolean unauthorized = response.code() == 401 || response.code() == 403;
//                    if (response.code() >= 500) {
//
//                    } else if (unauthorized) {
//                        RefreshToken();
//                        if (preferences.get(PreferenceKey.ACCESS_TOKEN, String.class).isEmpty())
//                            RefreshLogin();
//                        Request.Builder requestBuilder = origin.newBuilder()
//                                .header("Authorization", "bearer " + preferences.get(PreferenceKey.ACCESS_TOKEN, String.class));
//                        request = requestBuilder.build();
//                        Log.d("New Request:", requestBuilder.build().toString());
//                        response = chain.proceed(request);
//
//                    }
//                    return response;
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//                return null;
//            }
//        });

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        OkHttpClient client = httpClient.build();
        Retrofit retrofitData = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(OPEN_CAGE_URL)
                .client(client)
                .build();

        openData = retrofitData.create(OpenCage.class);

        Retrofit retrofitWeather = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(OPEN_WEATHER_URL)
                .client(client)
                .build();

        openWeather = retrofitWeather.create(OpenWeather.class);
    }
    //////////////////////////////////// INTERFACE CALL API FROM CLOUD /////////////////////////////

    private interface OpenCage {
        @GET("json?pretty=1&no_annotations=1&no_dedupe=1&no_record=1&litmit=1")
        Observable<CityInfo> loadCityInfo(@Query("key") String keyapi, @Query("q") String cityname);

    }

    private interface OpenWeather {
        @GET("onecall?units=metric")
        Observable<WeatherInfo> getWeatherBylatlon(@Query("lat") String lat, @Query("lon") String lon
                , @Query("exclude") String exclude, @Query("appid") String keyapi);

    }

    /////////////////////////////// METHOD RECEIVE OBSERVABLE RESPONSE /////////////////////////////

    public static Observable<CityInfo> getCityInfoByName(String cityName){
        return openData.loadCityInfo(BuildConfig.KEYAPI,cityName)
                .compose(networkResponseTransformerCity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<WeatherInfo> getWeatherBylatlon(String lat,String lon,String exclude){
        return openWeather.getWeatherBylatlon(lat,lon,exclude,BuildConfig.APPID)
                .compose(networkResponseTransformerWeather)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static class NetworkResponseTransformer<T> implements
            ObservableTransformer<T, T> {

        private Throwable validateNetworkResponse(T response) {
            if (response == null) {
                return new RuntimeException("NetworkResponse null");
            } else {
                return null;
            }
        }

        @Override
        public ObservableSource<T> apply(Observable<T> observable) {
            return observable
                    .flatMap(
                            baseResponse -> {
                                Throwable error = validateNetworkResponse(baseResponse);
                                if (error != null) {
                                    return Observable.error(error);
                                }
                                return Observable.just(baseResponse);
                            });
        }
    }
}
