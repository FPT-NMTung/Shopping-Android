package fu.prm391.sampl.project.adapter.checkout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.model.address.Address;
import fu.prm391.sampl.project.view.checkout.CheckOutAddress;

public class CheckoutAddressAdapter extends RecyclerView.Adapter<CheckoutViewHolder> {

    private List<Address> list;
    private Context context;
    private LayoutInflater inflater;

    private int lastPositionCheck;
    private RadioButton lastRadioButton;

    public CheckoutAddressAdapter(List<Address> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.lastPositionCheck = 0;
        this.lastRadioButton = null;
    }

    @NonNull
    @Override
    public CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_address_cart, parent, false);
        return new CheckoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutViewHolder holder, int position) {
        Address address = list.get(position);
        String detailAddress = address.getDetail()
                + ", " + address.getWardPrefix() + " " + address.getWardName()
                + ", " + address.getDistrictPrefix() + " " + address.getDistrictName()
                + ", " + address.getProvinceName();

        holder.getTxtItemCheckoutAddressName().setText(address.getFullName());
        holder.getTxtItemCheckoutAddressDetail().setText(detailAddress);
        holder.getTxtItemCheckoutAddressPhone().setText(address.getPhone());
        holder.getRadioButtonCheckoutSelect().setChecked(address.getIsDefault() == 1);

        int tempPosition = position;
        if (address.getIsDefault() == 1) {
            lastPositionCheck = tempPosition;
            lastRadioButton = holder.getRadioButtonCheckoutSelect();
        }

        holder.getRadioButtonCheckoutSelect().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastPositionCheck != tempPosition) {
                    lastPositionCheck = tempPosition;
                    lastRadioButton.setChecked(false);
                    holder.getRadioButtonCheckoutSelect().setChecked(true);
                    lastRadioButton = holder.getRadioButtonCheckoutSelect();
                    ((CheckOutAddress) context).setSelectAddress(address);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class CheckoutViewHolder extends RecyclerView.ViewHolder {

    private TextView txtItemCheckoutAddressName;
    private TextView txtItemCheckoutAddressDetail;
    private TextView txtItemCheckoutAddressPhone;

    private RadioButton radioButtonCheckoutSelect;

    public CheckoutViewHolder(@NonNull View itemView) {
        super(itemView);

        this.txtItemCheckoutAddressName = itemView.findViewById(R.id.txtItemCheckoutAddressName);
        this.txtItemCheckoutAddressDetail = itemView.findViewById(R.id.txtItemCheckoutAddressDetail);
        this.txtItemCheckoutAddressPhone = itemView.findViewById(R.id.txtItemCheckoutAddressPhone);

        this.radioButtonCheckoutSelect = itemView.findViewById(R.id.radioButtonCheckoutSelect);
    }

    public TextView getTxtItemCheckoutAddressName() {
        return txtItemCheckoutAddressName;
    }

    public TextView getTxtItemCheckoutAddressDetail() {
        return txtItemCheckoutAddressDetail;
    }

    public TextView getTxtItemCheckoutAddressPhone() {
        return txtItemCheckoutAddressPhone;
    }

    public RadioButton getRadioButtonCheckoutSelect() {
        return radioButtonCheckoutSelect;
    }
}
