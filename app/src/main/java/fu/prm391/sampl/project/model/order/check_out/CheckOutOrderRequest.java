package fu.prm391.sampl.project.model.order.check_out;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckOutOrderRequest {
    @SerializedName("addressId")
    @Expose
    private int addressId;

    public CheckOutOrderRequest(int addressId) {
        this.addressId = addressId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
}
