package fu.prm391.sampl.project.view.category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fu.prm391.sampl.project.R;
import fu.prm391.sampl.project.model.category.Category;
import fu.prm391.sampl.project.view.MainActivity;
import fu.prm391.sampl.project.view.product.NewArrivalProduct;

public class SpecifyCategory extends AppCompatActivity {

    private TextView txtTitle;
    private ImageView imageViewBack;
    private Category category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specify_category);

        txtTitle = findViewById(R.id.txtSpecifyCategoryTitle);

        Intent intent = getIntent();
        category = (Category) intent.getSerializableExtra("specifyCategory");
        txtTitle.setText(category.getName());
        imageViewBack = findViewById(R.id.imageViewBackSpecifyCategory);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}