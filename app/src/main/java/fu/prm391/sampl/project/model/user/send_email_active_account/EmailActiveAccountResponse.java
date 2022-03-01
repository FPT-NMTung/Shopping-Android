package fu.prm391.sampl.project.model.user.send_email_active_account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmailActiveAccountResponse {

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
