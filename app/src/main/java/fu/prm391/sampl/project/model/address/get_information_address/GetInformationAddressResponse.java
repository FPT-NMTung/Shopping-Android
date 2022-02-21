package fu.prm391.sampl.project.model.address.get_information_address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetInformationAddressResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private GetInformationAddress list;

    public GetInformationAddressResponse() {
    }

    public GetInformationAddressResponse(String message, GetInformationAddress list) {
        this.message = message;
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GetInformationAddress getList() {
        return list;
    }

    public void setList(GetInformationAddress list) {
        this.list = list;
    }
}
