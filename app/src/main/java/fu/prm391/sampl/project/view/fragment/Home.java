package fu.prm391.sampl.project.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.adapter.product.ProductTrendingItemAdapter;
import fu.prm391.sampl.project.model.category.Category;
import fu.prm391.sampl.project.adapter.category.CategoryTop4Adapter;
import fu.prm391.sampl.project.model.category.CategoryResponse;
import fu.prm391.sampl.project.model.product.Product;
import fu.prm391.sampl.project.model.product.get_list_product.ProductListResponse;
import fu.prm391.sampl.project.adapter.product.ProductLinearVerticalItemAdapter;
import fu.prm391.sampl.project.remote.ApiClient;
import fu.prm391.sampl.project.view.category.AllCategory;
import fu.prm391.sampl.project.view.product.NewArrivalProduct;
import fu.prm391.sampl.project.view.product.TopDiscountProduct;
import fu.prm391.sampl.project.view.product.TrendingProduct;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerViewTop4Category, recyclerViewTopTrendingProduct;
    private TextView txtViewAllCategory, txtViewAllTrendingProduct;
    private ImageView imageCart, imageTopDiscount, imageNewArrival;
    private ConstraintLayout loadingLayout;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingLayout = view.findViewById(R.id.loadingConstraintLayoutHome);
        loadingLayout.setVisibility(View.VISIBLE);

        getTop4Category(view);
        getTrendingProducts(view);
        moveToOtherActivities(view);
        moveToOtherNavigationTab(view);
    }

    private void getTop4Category(View view) {
        recyclerViewTop4Category = view.findViewById(R.id.recyclerViewTop4Cate);
        Call<CategoryResponse> categoryResponseCall = ApiClient.getCategoryService().getTop4Categories();
        categoryResponseCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Category> categories = (ArrayList<Category>) response.body().getData();
                    recyclerViewTop4Category.setAdapter(new CategoryTop4Adapter(getContext(), categories));
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false) {
                        @Override
                        public boolean canScrollHorizontally() {
                            return false;
                        }
                    };
                    recyclerViewTop4Category.setLayoutManager(layoutManager);
                    loadingLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
            }
        });
    }

    private void getTrendingProducts(View view) {
        recyclerViewTopTrendingProduct = view.findViewById(R.id.recyclerViewTopTrendingProductHome);
        Call<ProductListResponse> productResponseCall = ApiClient.getProductService().getTopTrendingProduct();
        productResponseCall.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Product> products = (ArrayList<Product>) response.body().getData();
                    recyclerViewTopTrendingProduct.setAdapter(new ProductTrendingItemAdapter(getContext(), products));
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    };
                    recyclerViewTopTrendingProduct.setLayoutManager(layoutManager);
                }
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
            }
        });
    }

    private void moveToOtherActivities(View view) {
        // All Category
        txtViewAllCategory = view.findViewById(R.id.txtViewAllCategoryHome);
        txtViewAllCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AllCategory.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        // Trending
        txtViewAllTrendingProduct = view.findViewById(R.id.txtViewAllTrendingProductHome);
        txtViewAllTrendingProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TrendingProduct.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        // Top discount
        imageTopDiscount = view.findViewById(R.id.imageSuperSale);
        imageTopDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), TopDiscountProduct.class));
                getActivity().finish();
            }
        });
        // New Arrival
        imageNewArrival = view.findViewById(R.id.imageNewArrival);
        imageNewArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), NewArrivalProduct.class));
                getActivity().finish();
            }
        });
    }

    private void moveToOtherNavigationTab(View view) {
        imageCart = view.findViewById(R.id.imageCartHome);
        imageCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomNavigationView bottomNavigationView;
                bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
//                bottomNavigationView.setOnNavigationItemSelectedListener(myNavigationItemListener);
                bottomNavigationView.setSelectedItemId(R.id.cart);
            }
        });
    }
}