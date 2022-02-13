package fu.prm391.sampl.project.model.address.delete_address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteAddressResponse {
    @SerializedName("message")
    @Expose
    private String message;

    public DeleteAddressResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
