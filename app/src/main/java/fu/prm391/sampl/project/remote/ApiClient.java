package fu.prm391.sampl.project.remote;

import fu.prm391.sampl.project.remote.service.ProductService;
import fu.prm391.sampl.project.remote.service.UserService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "https://api.nmtung.xyz/";
//        public static final String BASE_URL = "https://shopping-project-git-api-order-fpt-nmtung.vercel.app/";

    private static Retrofit getRetrofit() {
    // logging
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
//                .client(okHttpClient)
                .build();

        return retrofit;
    }

    public static UserService getUserService() {
        UserService userService = getRetrofit().create(UserService.class);
        return userService;
    }

    public static ProductService getProductService() {
        ProductService productService = getRetrofit().create(ProductService.class);
        return productService;
    }
}
