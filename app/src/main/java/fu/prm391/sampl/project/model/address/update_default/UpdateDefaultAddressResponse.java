package fu.prm391.sampl.project.model.address.update_default;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateDefaultAddressResponse {

    @SerializedName("message")
    @Expose
    private String message;

    public UpdateDefaultAddressResponse() {
    }

    public UpdateDefaultAddressResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
