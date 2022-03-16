package fu.prm391.sampl.project.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import fu.prm391.sampl.project.model.address.Address;
import fu.prm391.sampl.project.model.product.Product;

public class Order {

    @SerializedName("product")
    @Expose
    private Product product;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public Order() {
    }

    public Order(Product product, Address address, int status, int quantity, String createdAt, String updatedAt) {
        this.product = product;
        this.address = address;
        this.status = status;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
}
