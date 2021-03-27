package my.app.meraki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InputInfo extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText mail, mName, mAge, mDesc;
    RadioGroup radioGroup, section;
    RadioButton radioButton, typesec;
    Button submit, popup;
    String userId, isReg;
    Double lat = 28.7041, lon = 77.1025;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_info);

        radioGroup = findViewById(R.id.type);
        section = findViewById(R.id.type2);
        submit = (Button) findViewById(R.id.button2);
        popup = (Button) findViewById(R.id.popup);
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDial();
            }
        });
        mDesc = (EditText) findViewById(R.id.desc);
        mAge = (EditText) findViewById(R.id.age);
        mName = (EditText) findViewById(R.id.name);
        Intent intent = getIntent();
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = mName.getText().toString();
                final String age = mAge.getText().toString();
                final String desc = mDesc.getText().toString();
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                int radioId2 = section.getCheckedRadioButtonId();
                typesec = findViewById(radioId2);

                DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Corona").child("AllUsers").child(radioButton.getText().toString()).child(typesec.getText().toString()).child(userId).child("name");
                currentUserDb.setValue(name);
                DatabaseReference currentUserDb1 = FirebaseDatabase.getInstance().getReference().child("Corona").child("AllUsers").child(radioButton.getText().toString()).child(typesec.getText().toString()).child(userId).child("age");
                currentUserDb1.setValue(age);
                currentUserDb = FirebaseDatabase.getInstance().getReference().child("Corona").child("AllUsers").child(radioButton.getText().toString()).child(typesec.getText().toString()).child(userId).child("desc");
                currentUserDb.setValue(desc);
                currentUserDb1 = FirebaseDatabase.getInstance().getReference().child("Corona").child("AllUsers").child(radioButton.getText().toString()).child(typesec.getText().toString()).child(userId).child("uid");
                currentUserDb1.setValue(userId);
                currentUserDb = FirebaseDatabase.getInstance().getReference().child("Corona").child("AllUsers").child(radioButton.getText().toString()).child(typesec.getText().toString()).child(userId).child("lon");
                currentUserDb.setValue(lon);
                currentUserDb1 = FirebaseDatabase.getInstance().getReference().child("Corona").child("AllUsers").child(radioButton.getText().toString()).child(typesec.getText().toString()).child(userId).child("lat");
                currentUserDb1.setValue(lat);
                Toast.makeText(InputInfo.this, "Your default location is set in Delhi, Please change it from settings if you are not from Delhi.", Toast.LENGTH_LONG).show();
                DatabaseReference currentUserDb2 = FirebaseDatabase.getInstance().getReference().child("Corona").child("Users").child(userId).child("firstType");
                currentUserDb2.setValue(radioButton.getText().toString());
                currentUserDb2 = FirebaseDatabase.getInstance().getReference().child("Corona").child("Users").child(userId).child("secondType");
                currentUserDb2.setValue(typesec.getText().toString());

                Intent intent = new Intent(InputInfo.this, MapsActivity.class);
                intent.putExtra("t1", radioButton.getText().toString());
                intent.putExtra("t2", typesec.getText().toString());
                startActivity(intent);
                finish();
                return;
            }
        });
    }

    private void openDial() {
        SampleDesc sd = new SampleDesc();
        sd.show(getSupportFragmentManager(), "example description");
    }
}
