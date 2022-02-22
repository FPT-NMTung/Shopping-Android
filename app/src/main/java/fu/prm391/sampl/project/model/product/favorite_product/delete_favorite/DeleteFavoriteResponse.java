package fu.prm391.sampl.project.model.product.favorite_product.delete_favorite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteFavoriteResponse {

    @SerializedName("message")
    @Expose
    private String message;

    public DeleteFavoriteResponse() {
    }

    public DeleteFavoriteResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
