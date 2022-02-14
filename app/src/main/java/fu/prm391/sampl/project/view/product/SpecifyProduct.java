package fu.prm391.sampl.project.view.product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.model.product.Product;

public class SpecifyProduct extends AppCompatActivity {

    private TextView textView;
    private Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specify_product);
        textView = findViewById(R.id.testProductName);

        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("specifyProduct");
        textView.setText(product.getName());
    }
}