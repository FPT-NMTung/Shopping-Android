package fu.prm391.sampl.project.model.address.create_new_address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateNewAddressRequest {
    @SerializedName("fullName")
    @Expose
    private String fullName;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("provinceId")
    @Expose
    private int provinceId;

    @SerializedName("districtId")
    @Expose
    private int districtId;

    @SerializedName("wardId")
    @Expose
    private int wardId;

    @SerializedName("detail")
    @Expose
    private String detail;

    public CreateNewAddressRequest(String fullName, String phone, int provinceId, int districtId, int wardId, String detail) {
        this.fullName = fullName;
        this.phone = phone;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.wardId = wardId;
        this.detail = detail;
    }

    public CreateNewAddressRequest() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getWardId() {
        return wardId;
    }

    public void setWardId(int wardId) {
        this.wardId = wardId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
