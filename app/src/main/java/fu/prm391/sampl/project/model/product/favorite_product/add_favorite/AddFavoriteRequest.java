package fu.prm391.sampl.project.model.product.favorite_product.add_favorite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddFavoriteRequest {

    @SerializedName("productId")
    @Expose
    private int productId;

    public AddFavoriteRequest() {
    }

    public AddFavoriteRequest(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
