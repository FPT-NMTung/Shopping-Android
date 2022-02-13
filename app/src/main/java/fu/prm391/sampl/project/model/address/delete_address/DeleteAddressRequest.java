package fu.prm391.sampl.project.model.address.delete_address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteAddressRequest {
    @SerializedName("id")
    @Expose
    private int id;

    public DeleteAddressRequest(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
