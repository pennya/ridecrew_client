package network;

import Define.DefineValue;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kim on 2017. 12. 8..
 */

public class NetworkManager {
    private static final NetworkManager networkManager = new NetworkManager();

    private NetworkManager() { }

    public static NetworkManager getIntance() {
        return networkManager;
    }

    public static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(DefineValue.END_POINT)
            .addConverterFactory(GsonConverterFactory.create());

    public static Retrofit retrofit = builder.build();

    public <T> T getRetrofit(Class<T> service) {
        return retrofit.create(service);
    }

}