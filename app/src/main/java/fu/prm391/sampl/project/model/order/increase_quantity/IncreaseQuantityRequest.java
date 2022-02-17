package fu.prm391.sampl.project.model.order.increase_quantity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IncreaseQuantityRequest {
    @SerializedName("productId")
    @Expose
    private int productId;

    public IncreaseQuantityRequest(int productId) {
        this.productId = productId;
    }

    public IncreaseQuantityRequest() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
