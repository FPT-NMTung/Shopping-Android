package fu.prm391.sampl.project.model.order.add_to_cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddToCartResponse {

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
