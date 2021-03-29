package my.app.meraki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;

public class first extends AppCompatActivity {
    private Button msignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        msignup = (Button)findViewById(R.id.signup);

        FirebaseApp.initializeApp(this.getApplicationContext());
        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(first.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;

            }
        });
    }
}
