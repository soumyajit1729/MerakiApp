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

public class showPeople extends AppCompatActivity {
    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref, ref1;
    ArrayList<User> list, list1;
    peopleAdapter adapter;
    User user;
    Button setAct, setData, seeReqests;
    private FirebaseAuth mAuth;
    Double lat,lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_people);
        mAuth = FirebaseAuth.getInstance();
        user = new User();
        listView = (ListView)findViewById(R.id.people);
        database = FirebaseDatabase.getInstance();
        final String userId = mAuth.getCurrentUser().getUid();
        Intent intent = getIntent();
        final String x = intent.getStringExtra("t1");
        final String y = intent.getStringExtra("t2");
        final String x1;
        if (x.equals("Contributor")) {
            x1 = "Recipient";
        }else {
            x1 = "Contributor";
        }
        System.out.println("x is :" + x );

        ref = database.getReference("Corona").child("AllUsers").child(x1).child(y);
        ref1 = database.getReference("Corona").child("AllUsers").child(x).child(y).child(userId);
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lat = (Double) dataSnapshot.child("lat").getValue();
                lon = (Double) dataSnapshot.child("lon").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref1 = database.getReference("Corona").child("chats").child(userId).child("sent");
        list = new ArrayList<User>();
        list1 = new ArrayList<User>();
        adapter = new peopleAdapter(this,R.layout.peopleinfo, list1);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    user = ds.getValue(User.class);
                    if(Math.pow((user.getLat()-lat), 2) + Math.pow((user.getLon()-lon), 2) <= 14.2){
                        user.setDist(Math.pow((user.getLat()-lat), 2) + Math.pow((user.getLon()-lon), 2));
                        list.add(user);
                        list1.add(user);
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
                            System.out.println(i);
                            if(dataSnapshot.hasChild(list.get(i).getUid())){
                                list1.remove(list.get(i));
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                ref1 = database.getReference("Corona").child("chats").child(userId).child("matches");
                ref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(int i=0; i<list.size(); ++i){
                            System.out.println(i);
                            if(dataSnapshot.hasChild(list.get(i).getUid())){
                                list1.remove(list.get(i));
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                ref1 = database.getReference("Corona").child("chats").child(userId).child("requests");
                ref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(int i=0; i<list.size(); ++i){
                            System.out.println(i);
                            if(dataSnapshot.hasChild(list.get(i).getUid())){
                                list1.remove(list.get(i));
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
        setAct = findViewById(R.id.setAct);
        setAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(showPeople.this, Settings.class);
                intent.putExtra("t1", x);
                intent.putExtra("t2", y);
                startActivity(intent);
                finish();
            }
        });
        setData = findViewById(R.id.seeMatches);
        setData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(showPeople.this, Matches.class);
                intent.putExtra("t1", x);
                intent.putExtra("t2", y);
                startActivity(intent);
                finish();
                return;
            }
        });
        seeReqests = findViewById(R.id.seeReq);
        seeReqests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(showPeople.this, Requests.class);
                intent.putExtra("t1", x);
                intent.putExtra("t2", y);
                //intent.putExtra("list", list);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}

