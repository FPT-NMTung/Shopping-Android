package fu.prm391.sampl.project.adapter.address;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.model.address.get_district.District;
import fu.prm391.sampl.project.model.address.get_province.Province;

public class DistrictAdapter extends ArrayAdapter<District> {

    private LayoutInflater inflater;

    public DistrictAdapter(@NonNull Context context, int resource, @NonNull List<District> objects) {
        super(context, resource, objects);
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_spinner_new_address, parent, false);
        District district = getItem(position);

        TextView textView = view.findViewById(R.id.txt_spinner_new_address);
        textView.setText(district.getPrefix() + " " + district.getName());

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_spinner_new_address, parent, false);
        }
        District district = getItem(position);

        TextView textView = convertView.findViewById(R.id.txt_spinner_new_address);
        textView.setText(district.getPrefix() + " " + district.getName());

        return convertView;
    }
}
