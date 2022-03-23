package fu.prm391.sampl.project.remote;

import fu.prm391.sampl.project.remote.service.AddressService;
import fu.prm391.sampl.project.remote.service.CategoryService;
import fu.prm391.sampl.project.remote.service.OrderService;
import fu.prm391.sampl.project.remote.service.ProductService;
import fu.prm391.sampl.project.remote.service.UserService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "https://api.nmtung.dev/";

    private static Retrofit getRetrofit() {
        // logging
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build();

        return retrofit;
    }

    public static UserService getUserService() {
        return getRetrofit().create(UserService.class);
    }

    public static ProductService getProductService() {
        return getRetrofit().create(ProductService.class);
    }

    public static CategoryService getCategoryService() {
        return getRetrofit().create(CategoryService.class);
    }

    public static AddressService getAddressService() {
        return getRetrofit().create(AddressService.class);
    }

    public static OrderService getOrderService() {
        return getRetrofit().create(OrderService.class);
    }
}
