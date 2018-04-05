package network;

import android.text.TextUtils;

import Define.DefineValue;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kim on 2017. 12. 8..
 */

public class NetworkManager {
    private static final NetworkManager networkManager = new NetworkManager();
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private NetworkManager() { }

    public static NetworkManager getInstance() {
        return networkManager;
    }

    public static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(DefineValue.END_POINT)
            .addConverterFactory(GsonConverterFactory.create());

    public static Retrofit retrofit = builder.build();

    public <T> T getRetrofit(Class<T> service) {
        return create(service, "packriding", "packriding");
    }

    public static <S> S create(
            Class<S> serviceClass, String username, String password) {
        if (!TextUtils.isEmpty(username)
                && !TextUtils.isEmpty(password)) {
            String authToken = Credentials.basic(username, password);
            return createService(serviceClass, authToken);
        }

        return createService(serviceClass, null);
    }

    public static <S> S createService(
            Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }
}