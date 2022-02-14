package fu.prm391.sampl.project.model.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("isDefault")
    @Expose
    private int isDefault;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("provinceName")
    @Expose
    private String provinceName;
    @SerializedName("districtName")
    @Expose
    private String districtName;
    @SerializedName("districtPrefix")
    @Expose
    private String districtPrefix;
    @SerializedName("wardName")
    @Expose
    private String wardName;
    @SerializedName("wardPrefix")
    @Expose
    private String wardPrefix;

    public Address(int id, int userId, String fullName, String phone, String detail, int isDefault, String createdAt, String updatedAt, String provinceName, String districtName, String districtPrefix, String wardName, String wardPrefix) {
        this.id = id;
        this.userId = userId;
        this.fullName = fullName;
        this.phone = phone;
        this.detail = detail;
        this.isDefault = isDefault;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.provinceName = provinceName;
        this.districtName = districtName;
        this.districtPrefix = districtPrefix;
        this.wardName = wardName;
        this.wardPrefix = wardPrefix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictPrefix() {
        return districtPrefix;
    }

    public void setDistrictPrefix(String districtPrefix) {
        this.districtPrefix = districtPrefix;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getWardPrefix() {
        return wardPrefix;
    }

    public void setWardPrefix(String wardPrefix) {
        this.wardPrefix = wardPrefix;
    }
}
