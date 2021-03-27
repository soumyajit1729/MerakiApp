package my.app.meraki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeBio extends AppCompatActivity {

    String x, y;
    EditText name, age, desc;
    Button btn, popup;
    FirebaseDatabase database;
    DatabaseReference db;
    String userId;
    User user;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_bio);

        Intent intent = getIntent();
        x = intent.getStringExtra("t1");
        y = intent.getStringExtra("t2");
        popup = (Button) findViewById(R.id.popup);
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDial();
            }
        });
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        desc = (EditText) findViewById(R.id.desc);
        btn = (Button) findViewById(R.id.changeBio);
        System.out.println(x + "Hii all" + y);
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        db = database.getReference("Corona").child("AllUsers").child(x).child(y).child(userId);
        user = new User();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                name.setText(user.getName());
                age.setText(user.getAge());
                desc.setText(user.getDesc());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.child("name").setValue(name.getText().toString());
                db.child("age").setValue(age.getText().toString());
                db.child("desc").setValue(desc.getText().toString());
                Toast.makeText(ChangeBio.this, "Your data is successfully saved.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChangeBio.this, Settings.class);
                intent.putExtra("t1", x);
                intent.putExtra("t2", y);
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
