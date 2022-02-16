package fu.prm391.sampl.project.model.address.update_address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateAddressResponse {
    @SerializedName("message")
    @Expose
    private String message;

    public UpdateAddressResponse() {
    }

    public UpdateAddressResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
