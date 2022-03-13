package fu.prm391.sampl.project.adapter.address;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.helper.PreferencesHelpers;
import fu.prm391.sampl.project.model.address.Address;
import fu.prm391.sampl.project.model.address.get_information_address.GetInformationAddressResponse;
import fu.prm391.sampl.project.model.address.update_default.UpdateDefaultAddressRequest;
import fu.prm391.sampl.project.model.address.update_default.UpdateDefaultAddressResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import fu.prm391.sampl.project.view.address.CreateNewAddress;
import fu.prm391.sampl.project.view.address.ProfileShippingAddress;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingAddressAdapter extends RecyclerView.Adapter<ShippingAddressViewHolder> {

    private List<Address> list;
    private LayoutInflater inflater;
    private Context context;
    private String token;

    private RadioButton lastRdb;
    private int positionCheck;

    public ShippingAddressAdapter(List<Address> list, Context context) {
        this.list = list;
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.lastRdb = null;
        this.positionCheck = 0;
        token = PreferencesHelpers.loadStringData(context, "token");
    }

    @NonNull
    @Override
    public ShippingAddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_address_psa, parent, false);
        return new ShippingAddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShippingAddressViewHolder holder, int position) {
        Address address = list.get(position);
        String detailAddress = address.getDetail()
                + ", " + address.getWardPrefix() + " " + address.getWardName()
                + ", " + address.getDistrictPrefix() + " " + address.getDistrictName()
                + ", " + address.getProvinceName();

        holder.getTxtName().setText(address.getFullName());
        holder.getTxtAddress().setText(detailAddress);
        holder.getTxtPhone().setText(address.getPhone());
        holder.getRdbPsaDefault().setChecked(address.getIsDefault() == 1);

        int temp = position;

        if (address.getIsDefault() == 1) {
            lastRdb = holder.getRdbPsaDefault();
            positionCheck = temp;
        }

        String token = PreferencesHelpers.loadStringData(context, "token");

        holder.getRdbPsaDefault().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (positionCheck != temp) {
                    lastRdb.setChecked(false);
                    holder.getRdbPsaDefault().setChecked(true);

                    Call<UpdateDefaultAddressResponse> call = ApiClient.getAddressService().updateDefaultAddress("Bearer " + token, new UpdateDefaultAddressRequest(address.getId()));
                    call.enqueue(new Callback<UpdateDefaultAddressResponse>() {
                        @Override
                        public void onResponse(Call<UpdateDefaultAddressResponse> call, Response<UpdateDefaultAddressResponse> response) {
                            Log.i("TAG", "onResponse: UpdateDefaultAddressResponse");
                        }

                        @Override
                        public void onFailure(Call<UpdateDefaultAddressResponse> call, Throwable t) {
                            Log.e("TAG", "onFailure: UpdateDefaultAddressResponse");
                        }
                    });

                    list.get(positionCheck).setIsDefault(0);

                    lastRdb = holder.getRdbPsaDefault();
                    positionCheck = temp;

                    list.get(positionCheck).setIsDefault(1);
                }
            }
        });
        holder.getBtnEdit().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.getBtnEdit().setEnabled(false);
                Call<GetInformationAddressResponse> call = ApiClient.getAddressService().getInformationAddress("Bearer " + token, address.getId());
                call.enqueue(new Callback<GetInformationAddressResponse>() {
                    @Override
                    public void onResponse(Call<GetInformationAddressResponse> call, Response<GetInformationAddressResponse> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(context, CreateNewAddress.class);

                            intent.putExtra("id", response.body().getList().getId());
                            intent.putExtra("fullName", response.body().getList().getFullName());
                            intent.putExtra("phone", response.body().getList().getPhone());
                            intent.putExtra("detail", response.body().getList().getDetail());

                            intent.putExtra("updateAddress", true);

                            context.startActivity(intent);
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<GetInformationAddressResponse> call, Throwable t) {
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class ShippingAddressViewHolder extends RecyclerView.ViewHolder {

    private TextView txtName;
    private TextView txtAddress;
    private TextView txtPhone;
    private RadioButton rdbPsaDefault;
    private Button btnEdit;

    public ShippingAddressViewHolder(@NonNull View itemView) {
        super(itemView);

        txtName = itemView.findViewById(R.id.txtPsaItemName);
        txtAddress = itemView.findViewById(R.id.txtPsaItemAddress);
        txtPhone = itemView.findViewById(R.id.txtPsaItemPhone);
        rdbPsaDefault = itemView.findViewById(R.id.rdbPsaDefault);
        btnEdit = itemView.findViewById(R.id.btnPsaEditAddress);
    }

    public TextView getTxtName() {
        return txtName;
    }

    public TextView getTxtAddress() {
        return txtAddress;
    }

    public TextView getTxtPhone() {
        return txtPhone;
    }

    public RadioButton getRdbPsaDefault() {
        return rdbPsaDefault;
    }

    public Button getBtnEdit() {
        return btnEdit;
    }
}