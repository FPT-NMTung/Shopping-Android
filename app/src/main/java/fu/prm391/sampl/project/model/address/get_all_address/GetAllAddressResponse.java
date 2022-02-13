package fu.prm391.sampl.project.model.address.get_all_address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import fu.prm391.sampl.project.model.address.Address;

public class GetAllAddressResponse {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<Address> listAddress;

    public GetAllAddressResponse() {
    }

    public GetAllAddressResponse(String message, List<Address> listAddress) {
        this.message = message;
        this.listAddress = listAddress;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Address> getListAddress() {
        return listAddress;
    }

    public void setListAddress(List<Address> listAddress) {
        this.listAddress = listAddress;
    }
}
