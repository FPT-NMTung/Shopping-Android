package fu.prm391.sampl.project.model.address.update_default;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateDefaultAddressRequest {

    @SerializedName("id")
    @Expose
    private int id;

    public UpdateDefaultAddressRequest(int id) {
        this.id = id;
    }

    public UpdateDefaultAddressRequest() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
