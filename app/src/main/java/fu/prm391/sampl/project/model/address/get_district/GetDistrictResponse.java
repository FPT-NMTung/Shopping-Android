package fu.prm391.sampl.project.model.address.get_district;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetDistrictResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<District> list;

    public GetDistrictResponse() {
    }

    public GetDistrictResponse(String message, List<District> list) {
        this.message = message;
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<District> getList() {
        return list;
    }

    public void setList(List<District> list) {
        this.list = list;
    }
}

