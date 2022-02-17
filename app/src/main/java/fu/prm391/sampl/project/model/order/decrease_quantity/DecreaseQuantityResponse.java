package fu.prm391.sampl.project.model.order.decrease_quantity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DecreaseQuantityResponse {
    @SerializedName("message")
    @Expose
    private String message;

    public DecreaseQuantityResponse() {
    }

    public DecreaseQuantityResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
