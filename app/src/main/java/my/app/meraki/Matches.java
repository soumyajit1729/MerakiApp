package my.app.meraki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Matches extends AppCompatActivity {

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref, ref1, ref2;
    ArrayList<User> list, list1;
    Button backMat;
    matchAdapter adapter;
    User user;
    Double lat,lon;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        backMat = (Button) findViewById(R.id.backMatch);
        mAuth = FirebaseAuth.getInstance();
        user = new User();
        listView = findViewById(R.id.matchid);
        database = FirebaseDatabase.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        Intent intent = getIntent();
        final String x = intent.getStringExtra("t1");
        final String y = intent.getStringExtra("t2");
        String x1;
        if (x.equals("Contributor")) {
            x1 = "Recipient";
        }else {
            x1 = "Contributor";
        }
        backMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Matches.this, showPeople.class);
                intent.putExtra("t1", x);
                intent.putExtra("t2", y);
                startActivity(intent);
                finish();
                return;
            }
        });
        System.out.println("x is :" + x );
        ref = database.getReference("Corona").child("AllUsers").child(x1).child(y);
        ref1 = database.getReference("Corona").child("chats").child(userId).child("matches");
        list = new ArrayList<User>();
        list1 = new ArrayList<User>();
        ref2 = database.getReference("Corona").child("AllUsers").child(x).child(y).child(userId);
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lat = (Double) dataSnapshot.child("lat").getValue();
                lon = (Double) dataSnapshot.child("lon").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapter = new matchAdapter(this,R.layout.peopleinfo, list1);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    user = ds.getValue(User.class);
                    if(Math.pow((user.getLat()-lat), 2) + Math.pow((user.getLon()-lon), 2) <= 0.8){
                        user.setDist(Math.pow((user.getLat()-lat), 2) + Math.pow((user.getLon()-lon), 2));
                        list.add(user);
                    }
                    Collections.sort(list, new Comparator<User>() {
                        @Override
                        public int compare(User o1, User o2) {
                            return o1.getDist().compareTo(o2.getDist());
                        }
                    });
                }
                ref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(int i=0; i<list.size(); ++i){
                            if(dataSnapshot.hasChild(list.get(i).getUid())){
                                list1.add(list.get(i));
                            }
                        }
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
