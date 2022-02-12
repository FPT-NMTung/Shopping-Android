package fu.prm391.sampl.project.view.address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.adapter.DistrictAdapter;
import fu.prm391.sampl.project.adapter.ProvinceAdapter;
import fu.prm391.sampl.project.adapter.WardAdapter;
import fu.prm391.sampl.project.model.address.get_district.District;
import fu.prm391.sampl.project.model.address.get_district.GetDistrictResponse;
import fu.prm391.sampl.project.model.address.get_province.GetProvinceResponse;
import fu.prm391.sampl.project.model.address.get_province.Province;
import fu.prm391.sampl.project.model.address.get_ward.GetWardResponse;
import fu.prm391.sampl.project.model.address.get_ward.Ward;
import fu.prm391.sampl.project.remote.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNewAddress extends AppCompatActivity {

    private Button btnCnaBack;
    private Button btnCnaSubmit;

    private Spinner spinnerCnaProvince;
    private Spinner spinnerCnaDistrict;
    private Spinner spinnerCnaWard;

    private List<Province> provinceList = new ArrayList<>();
    private List<District> districtList = new ArrayList<>();
    private List<Ward> wardList = new ArrayList<>();

    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextDetail;

    private int provinceId;
    private int districtId;
    private int wardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_address);

        btnCnaBack = findViewById(R.id.btnCnaBack);
        btnCnaSubmit = findViewById(R.id.btnCnaSubmit);

        spinnerCnaProvince = findViewById(R.id.spinnerCnaProvince);
        spinnerCnaDistrict = findViewById(R.id.spinnerCnaDistrict);
        spinnerCnaWard = findViewById(R.id.spinnerCnaWard);

        editTextName = findViewById(R.id.editText_cna_name);
        editTextPhone = findViewById(R.id.editText_cna_phone);
        editTextDetail = findViewById(R.id.editText_cna_detail);

        provinceId = 0;
        districtId = 0;
        wardId = 0;

        setDefaultProvince();
        setDefaultDistrict();
        setDefaultWard();

        loadSpinnerProvince();

        setEventChangeProvince();
        setEventChangeDistrict();
        setEventChangeWard();

        setEventBtnBack();
        setEventBtnSubmit();
    }

    private void setEventBtnBack() {
        btnCnaBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setEventBtnSubmit() {
        btnCnaSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                String detail = editTextDetail.getText().toString().trim();

                if (name.equals("") || name.length() < 2 || name.length() > 100) {
                    Toast.makeText(CreateNewAddress.this, "Name must be from 2 to 100 character", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phone.equals("") || phone.length() != 10) {
                    Toast.makeText(CreateNewAddress.this, "Phone number invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (provinceId == 0) {
                    Toast.makeText(CreateNewAddress.this, "Please select province", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (districtId == 0) {
                    Toast.makeText(CreateNewAddress.this, "Please select district", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (wardId == 0) {
                    Toast.makeText(CreateNewAddress.this, "Please select ward", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (detail.equals("") || detail.length() < 2 || detail.length() > 100) {
                    Toast.makeText(CreateNewAddress.this, "Detail must be from 2 to 100 character", Toast.LENGTH_SHORT).show();
                    return;
                }

                Call<>
            }
        });
    }

    private void setEventChangeProvince() {
        spinnerCnaProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    setDefaultDistrict();
                    provinceId = 0;
                } else {
                    loadSpinnerDistrict(provinceList.get(i).getId());
                    provinceId = provinceList.get(i).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setEventChangeDistrict() {
        spinnerCnaDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    setDefaultWard();
                    districtId = 0;
                } else {
                    loadSpinnerWard(districtList.get(i).getId());
                    districtId = districtList.get(i).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setEventChangeWard() {
        spinnerCnaWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    wardId = 0;
                } else {
                    wardId = wardList.get(i).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void loadSpinnerProvince() {
        Call<GetProvinceResponse> call = ApiClient.getAddressService().getProvinceAddress();
        call.enqueue(new Callback<GetProvinceResponse>() {
            @Override
            public void onResponse(Call<GetProvinceResponse> call, Response<GetProvinceResponse> response) {
                if (response.isSuccessful()) {

                    List<Province> list = response.body().getList();
                    list.add(0, new Province(0, "-- Select province --", ""));

                    provinceList = list;

                    ProvinceAdapter provinceAdapter = new ProvinceAdapter(CreateNewAddress.this, R.layout.item_spinner_new_address, list);
                    spinnerCnaProvince.setAdapter(provinceAdapter);

                    spinnerCnaProvince.setSelection(0);
                }
            }

            @Override
            public void onFailure(Call<GetProvinceResponse> call, Throwable t) {

            }
        });
    }

    private void loadSpinnerDistrict(int id) {
        Call<GetDistrictResponse> call = ApiClient.getAddressService().getDistrictAddress(id);
        call.enqueue(new Callback<GetDistrictResponse>() {
            @Override
            public void onResponse(Call<GetDistrictResponse> call, Response<GetDistrictResponse> response) {
                if (response.isSuccessful()) {
                    List<District> list = response.body().getList();
                    list.add(0, new District(0, "-- Select District --", ""));

                    districtList = list;

                    DistrictAdapter districtAdapter = new DistrictAdapter(CreateNewAddress.this, R.layout.item_spinner_new_address, list);
                    spinnerCnaDistrict.setAdapter(districtAdapter);

                    spinnerCnaDistrict.setSelection(0);
                }
            }

            @Override
            public void onFailure(Call<GetDistrictResponse> call, Throwable t) {

            }
        });
    }

    private void loadSpinnerWard(int id) {
        Call<GetWardResponse> call = ApiClient.getAddressService().getWardAddress(id);
        call.enqueue(new Callback<GetWardResponse>() {
            @Override
            public void onResponse(Call<GetWardResponse> call, Response<GetWardResponse> response) {
                if (response.isSuccessful()) {
                    List<Ward> list = response.body().getList();
                    list.add(0, new Ward(0, "-- Select Ward --", ""));

                    wardList = list;

                    WardAdapter wardAdapter = new WardAdapter(CreateNewAddress.this, R.layout.item_spinner_new_address, list);
                    spinnerCnaWard.setAdapter(wardAdapter);

                    spinnerCnaWard.setSelection(0);
                }
            }

            @Override
            public void onFailure(Call<GetWardResponse> call, Throwable t) {

            }
        });
    }

    private void setDefaultProvince() {
        List<Province> list = new ArrayList<>();
        list.add(new Province(0, "-- Select Province --", ""));

        spinnerCnaProvince.setAdapter(new ProvinceAdapter(CreateNewAddress.this, R.layout.item_spinner_new_address, list));
        spinnerCnaProvince.setSelection(0);
    }

    private void setDefaultDistrict() {
        List<District> list = new ArrayList<>();
        list.add(new District(0, "-- Select District --", ""));

        spinnerCnaDistrict.setAdapter(new DistrictAdapter(CreateNewAddress.this, R.layout.item_spinner_new_address, list));
        spinnerCnaDistrict.setSelection(0);
    }

    private void setDefaultWard() {
        List<Ward> list = new ArrayList<>();
        list.add(new Ward(0, "-- Select Ward --", ""));

        spinnerCnaWard.setAdapter(new WardAdapter(CreateNewAddress.this, R.layout.item_spinner_new_address, list));
        spinnerCnaWard.setSelection(0);
    }
}