package fu.prm391.sampl.project.model.address.create_new_address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateNewAddressResponse {
    @SerializedName("message")
    @Expose
    private String message;

    public CreateNewAddressResponse() {
    }

    public CreateNewAddressResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
