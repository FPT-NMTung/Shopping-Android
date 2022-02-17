package fu.prm391.sampl.project.model.order.delete_order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteOrderRequest {
    @SerializedName("productId")
    @Expose
    private int productId;

    public DeleteOrderRequest() {
    }

    public DeleteOrderRequest(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
