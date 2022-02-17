package fu.prm391.sampl.project.model.order.decrease_quantity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DecreaseQuantityRequest {
    @SerializedName("productId")
    @Expose
    private int productId;

    public DecreaseQuantityRequest(int productId) {
        this.productId = productId;
    }

    public DecreaseQuantityRequest() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
