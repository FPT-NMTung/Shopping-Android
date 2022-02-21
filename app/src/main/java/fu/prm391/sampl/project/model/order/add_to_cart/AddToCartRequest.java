package fu.prm391.sampl.project.model.order.add_to_cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddToCartRequest {

    @SerializedName("productId")
    @Expose
    private int productId;

    @SerializedName("quantity")
    @Expose
    private int quantity;

    public AddToCartRequest() {
    }

    public AddToCartRequest(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
