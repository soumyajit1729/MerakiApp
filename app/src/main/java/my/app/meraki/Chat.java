package my.app.meraki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chat extends AppCompatActivity {

    ListView listView;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference ref, ref1, ref2;
    msg msg;
    private ArrayList<msg> arrayList;
    msgAdapter adapter;
    Button btn, backChat;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mAuth = FirebaseAuth.getInstance();
        msg = new msg();
        backChat = (Button)findViewById(R.id.backChat);
        backChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Chat.this, WaitingPage.class);
                startActivity(intent);
                finish();
                return;
            }
        });
        listView = (ListView)findViewById(R.id.msgs);
        database = FirebaseDatabase.getInstance();
        final String userId = mAuth.getCurrentUser().getUid();
        Intent intent = getIntent();
        final String x = intent.getStringExtra("frontUser");
        final String userName = intent.getStringExtra("frontUserName");
        //getActionBar().setTitle(userName);
        getSupportActionBar().setTitle(userName);
        System.out.println("x is :" + x );
        ref = database.getReference("Corona").child("chats").child(userId).child("matches").child(x);
        arrayList = new ArrayList<msg>();
        adapter = new msgAdapter(this,R.layout.message, arrayList);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    msg = ds.getValue(msg.class);
                    arrayList.add(msg);
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        text = findViewById(R.id.editText);
        btn = (Button)findViewById(R.id.send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = text.getText().toString();
                text.setText("");
                ref1 = database.getReference("Corona").child("chats").child(userId).child("matches").child(x).push();
                ref1.child("msg").setValue(txt);
                ref1.child("sender").setValue("Me");
                ref2 = database.getReference("Corona").child("chats").child(x).child("matches").child(userId).push();
                ref2.child("msg").setValue(txt);
                ref2.child("sender").setValue("NotMe");
            }
        });


    }
}
