package fu.prm391.sampl.project.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.model.address.Address;
import fu.prm391.sampl.project.model.address.update_default.UpdateDefaultAddressRequest;
import fu.prm391.sampl.project.model.address.update_default.UpdateDefaultAddressResponse;
import fu.prm391.sampl.project.remote.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingAddressAdapter extends RecyclerView.Adapter<ShippingAddressViewHolder> {

    private List<Address> list;
    private LayoutInflater inflater;
    private Context context;

    private RadioButton lastRdb;
    private int positionCheck;

    public ShippingAddressAdapter(List<Address> list, Context context) {
        this.list = list;
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.lastRdb = null;
        this.positionCheck = 0;
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

        holder.getRdbPsaDefault().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (positionCheck != temp) {
                    lastRdb.setChecked(false);
                    holder.getRdbPsaDefault().setChecked(true);

                    Call<UpdateDefaultAddressResponse> call = ApiClient.getAddressService().updateDefaultAddress("Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NywiZW1haWwiOiJubXR1bmdvZmZpY2lhbEBnbWFpbC5jb20iLCJpYXQiOjE2NDQyMzM1OTl9.X7sI6-AIyKQHNj6-vlBHuuplFmTEkLnL5zkZfn5Dnzs", new UpdateDefaultAddressRequest(address.getId()));
                    call.enqueue(new Callback<UpdateDefaultAddressResponse>() {
                        @Override
                        public void onResponse(Call<UpdateDefaultAddressResponse> call, Response<UpdateDefaultAddressResponse> response) {

                        }

                        @Override
                        public void onFailure(Call<UpdateDefaultAddressResponse> call, Throwable t) {

                        }
                    });

                    list.get(positionCheck).setIsDefault(0);

                    lastRdb = holder.getRdbPsaDefault();
                    positionCheck = temp;

                    list.get(positionCheck).setIsDefault(1);
                }
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

    public ShippingAddressViewHolder(@NonNull View itemView) {
        super(itemView);

        txtName = itemView.findViewById(R.id.txtPsaItemName);
        txtAddress = itemView.findViewById(R.id.txtPsaItemAddress);
        txtPhone = itemView.findViewById(R.id.txtPsaItemPhone);
        rdbPsaDefault = itemView.findViewById(R.id.rdbPsaDefault);
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
}