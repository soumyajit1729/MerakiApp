package my.app.meraki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePref extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference db;
    TextView head;
    User user;
    String userId, x, y, newT1, newT2;
    RadioButton rb1, rb2;
    RadioGroup rg1, rg2;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pref);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        Intent intent = getIntent();
        x = intent.getStringExtra("t1");
        y = intent.getStringExtra("t2");
        rg1 = (RadioGroup)findViewById(R.id.needhelp);
        rg2 = (RadioGroup)findViewById(R.id.fmd);
        rb1 = (RadioButton)findViewById(R.id.needy);
        head = (TextView) findViewById(R.id.heading);
        head.setText("You are currently a " + x + " in " + y);
        btn = (Button) findViewById(R.id.save);
        database = FirebaseDatabase.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rg1.getCheckedRadioButtonId() == -1){
                    newT1 = x;
                }else{
                    int radioId = rg1.getCheckedRadioButtonId();
                    rb1 = findViewById(radioId);
                    newT1 = rb1.getText().toString();
                }
                if(rg2.getCheckedRadioButtonId() == -1){
                    newT2 = y;
                }else{
                    int radioId2 = rg2.getCheckedRadioButtonId();
                    rb2 = findViewById(radioId2);
                    user = new User();
                    newT2 = rb2.getText().toString();
                }
                db = database.getReference("Corona").child("AllUsers").child(x).child(y).child(userId);
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        user = dataSnapshot.getValue(User.class);
                        DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Corona").child("AllUsers").child(rb1.getText().toString()).child(rb2.getText().toString()).child(userId).child("name");
                        currentUserDb.setValue(user.getName());
                        DatabaseReference currentUserDb1 = FirebaseDatabase.getInstance().getReference().child("Corona").child("AllUsers").child(rb1.getText().toString()).child(rb2.getText().toString()).child(userId).child("age");
                        currentUserDb1.setValue(user.getAge());
                        DatabaseReference currentUserDb2 = FirebaseDatabase.getInstance().getReference().child("Corona").child("AllUsers").child(rb1.getText().toString()).child(rb2.getText().toString()).child(userId).child("desc");
                        currentUserDb2.setValue(user.getDesc());
                        DatabaseReference currentUserDb3 = FirebaseDatabase.getInstance().getReference().child("Corona").child("AllUsers").child(rb1.getText().toString()).child(rb2.getText().toString()).child(userId).child("lon");
                        currentUserDb3.setValue(user.getLon());
                        DatabaseReference currentUserDb4 = FirebaseDatabase.getInstance().getReference().child("Corona").child("AllUsers").child(rb1.getText().toString()).child(rb2.getText().toString()).child(userId).child("lat");
                        currentUserDb4.setValue(user.getLat());
                        DatabaseReference currentUserDb5 = FirebaseDatabase.getInstance().getReference().child("Corona").child("AllUsers").child(rb1.getText().toString()).child(rb2.getText().toString()).child(userId).child("uid");
                        currentUserDb5.setValue(user.getUid());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Corona").child("Users").child(userId);
                db1.child("firstType").setValue(newT1);
                db1.child("secondType").setValue(newT2);

                Toast.makeText(ChangePref.this, "You are now a " + newT1 + " in " + newT2 , Toast.LENGTH_SHORT).show();
                //db.removeValue();
                Intent intent = new Intent(ChangePref.this, Settings.class);
                intent.putExtra("t1", newT1);
                intent.putExtra("t2", newT2);
                startActivity(intent);
                finish();
                return;
            }
        });



    }
}
