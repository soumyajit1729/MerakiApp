package my.app.meraki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {

    Button bio, pref, logout;
    EditText mname, mage;
    String userId;
    Button backEnt, setLocation;
    RadioGroup rd1, rd2;
    RadioButton rb1, rb2;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setTitle("change settings");
        backEnt = (Button)findViewById(R.id.backEnt);
        setLocation = (Button)findViewById(R.id.locationReset);
        final Intent intent = getIntent();
        final String x = intent.getStringExtra("t1");
        final String y = intent.getStringExtra("t2");
        backEnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, showPeople.class);
                intent.putExtra("t1", x);
                intent.putExtra("t2", y);
                startActivity(intent);
                finish();
                return;
            }
        });
        bio = (Button) findViewById(R.id.bioReset);
        pref = (Button) findViewById(R.id.prefReset);
        logout = (Button) findViewById(R.id.logout);
        mname = findViewById(R.id.name);
        mage = findViewById(R.id.age);
        rd1 = findViewById(R.id.type);
        rd2 = findViewById(R.id.type2);
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, ChangeBio.class);
                System.out.println(x + "Hii all" + y);
                intent.putExtra("t1", x);
                intent.putExtra("t2", y);
                startActivity(intent);
                finish();
                return;
            }
        });
        pref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, ChangePref.class);
                intent.putExtra("t1", x);
                intent.putExtra("t2", y);
                startActivity(intent);
                finish();
                return;
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(Settings.this, first.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, MapsActivity.class);
                intent.putExtra("t1", x);
                intent.putExtra("t2", y);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}
