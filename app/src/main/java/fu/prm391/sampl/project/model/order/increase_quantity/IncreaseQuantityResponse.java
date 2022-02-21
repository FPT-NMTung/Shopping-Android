package fu.prm391.sampl.project.model.order.increase_quantity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IncreaseQuantityResponse {
    @SerializedName("message")
    @Expose
    private String message;

    public IncreaseQuantityResponse() {
    }

    public IncreaseQuantityResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
