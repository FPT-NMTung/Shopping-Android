package fu.prm391.sampl.project.model.product.favorite_product.delete_favorite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteFavoriteRequest {

    @SerializedName("productId")
    @Expose
    private int productId;

    public DeleteFavoriteRequest() {
    }

    public DeleteFavoriteRequest(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
