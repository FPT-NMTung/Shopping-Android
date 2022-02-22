package fu.prm391.sampl.project.model.product.favorite_product.add_favorite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddFavoriteResponse {

    @SerializedName("message")
    @Expose
    private String message;

    public AddFavoriteResponse() {
    }

    public AddFavoriteResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
