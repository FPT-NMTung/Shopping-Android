package fu.prm391.sampl.project.model.address.get_ward;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetWardResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Ward> list;

    public GetWardResponse() {
    }

    public GetWardResponse(String message, List<Ward> list) {
        this.message = message;
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Ward> getList() {
        return list;
    }

    public void setList(List<Ward> list) {
        this.list = list;
    }
}
