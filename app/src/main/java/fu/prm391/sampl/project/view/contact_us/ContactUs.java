package fu.prm391.sampl.project.view.contact_us;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fu.prm391.sampl.project.R;

public class ContactUs extends AppCompatActivity {

    TextView email_1, email_2, email_3, email_4;
    TextView facebook_1, facebook_2, facebook_3, facebook_4;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        email_1 = findViewById(R.id.email_contact);
        email_2 = findViewById(R.id.email_contact1);
        email_3 = findViewById(R.id.email_contact2);
        email_4 = findViewById(R.id.email_contact3);
        facebook_1 = findViewById(R.id.facebook_contact);
        facebook_2 = findViewById(R.id.facebook_contact1);
        facebook_3 = findViewById(R.id.facebook_contact2);
        facebook_4 = findViewById(R.id.facebook_contact3);

        moveToFacebookAction();
        setupSendEmailAction();
        backAction();
    }

    private void moveToFacebookAction() {
        facebook_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = facebook_1.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        facebook_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = facebook_2.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        facebook_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = facebook_3.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        facebook_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = facebook_4.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    private void setupSendEmailAction() {
        email_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(email_1.getText().toString());
            }
        });

        email_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(email_2.getText().toString());
            }
        });

        email_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(email_3.getText().toString());
            }
        });

        email_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(email_4.getText().toString());
            }
        });
    }


    private void sendEmail(String email) {
        Intent emailSelectorIntent = new Intent(Intent.ACTION_SENDTO);
        emailSelectorIntent.setData(Uri.parse("mailto:"));
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        emailIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        emailIntent.setSelector(emailSelectorIntent);
        startActivity(emailIntent);
    }

    private void backAction() {
        imgBack = findViewById(R.id.imageViewBackContactUs);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}