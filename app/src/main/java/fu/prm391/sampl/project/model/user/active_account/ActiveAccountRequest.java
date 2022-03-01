package fu.prm391.sampl.project.model.user.active_account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActiveAccountRequest {

    @SerializedName("code")
    @Expose
    private String code;

    public ActiveAccountRequest() {
    }

    public ActiveAccountRequest(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
