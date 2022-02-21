package fu.prm391.sampl.project.model.order.delete_order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteOrderResponse {
    @SerializedName("message")
    @Expose
    private String message;

    public DeleteOrderResponse() {
    }

    public DeleteOrderResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
