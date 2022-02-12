package fu.prm391.sampl.project.view.product;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageButton;

import fu.prm391.sampl.project.R;

public class NewArrivalProduct extends AppCompatActivity {

    private GridView gridView;
    private ImageButton imageButtonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_arrival_product);
    }
}