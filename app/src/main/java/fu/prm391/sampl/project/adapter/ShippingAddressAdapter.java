package fu.prm391.sampl.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.model.address.Address;

public class ShippingAddressAdapter extends RecyclerView.Adapter<ShippingAddressViewHolder> {

    private ArrayList<Address> list;
    private LayoutInflater inflater;
    private Context context;

    public ShippingAddressAdapter(ArrayList<Address> list, Context context) {
        this.list = list;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ShippingAddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_address_psa, parent, false);
        ShippingAddressViewHolder viewHolder = new ShippingAddressViewHolder(view);
        return viewHolder;
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

    public ShippingAddressViewHolder(@NonNull View itemView) {
        super(itemView);

        txtName = itemView.findViewById(R.id.txtPsaItemName);
        txtAddress = itemView.findViewById(R.id.txtPsaItemAddress);
        txtPhone = itemView.findViewById(R.id.txtPsaItemPhone);
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
}