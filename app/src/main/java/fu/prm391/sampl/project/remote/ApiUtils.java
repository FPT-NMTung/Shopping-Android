package fu.prm391.sampl.project.remote;

public class ApiUtils {

    public static final String BASE_URL = "https://shopping-project-alpha.vercel.app/api/";

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}