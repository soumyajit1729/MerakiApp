package my.app.meraki;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class peopleAdapter extends ArrayAdapter<User> {

    private  static  final String TAG = "peopleAdapter";
    private  Context mContext;
    int mResource;
    private FirebaseAuth mAuth;

    public peopleAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        String age = getItem(position).getAge();
        String desc = getItem(position).getDesc();
        final String uid = getItem(position).getUid();

        User user = new User(name, desc, age, uid);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tv = (TextView) convertView.findViewById(R.id.name);
        TextView dsc = (TextView) convertView.findViewById(R.id.desc);
        TextView ag = (TextView)convertView.findViewById(R.id.age);
        final Button btn = (Button)convertView.findViewById(R.id.button);
        tv.setText(name);
        dsc.setText(desc);
        ag.setText(age);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                String userId = mAuth.getCurrentUser().getUid();
                DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Corona").child("chats").child(uid).child("requests").child(userId);
                currentUserDb.setValue("True");
                DatabaseReference currentUserDb1 = FirebaseDatabase.getInstance().getReference().child("Corona").child("chats").child(userId).child("sent").child(uid);
                currentUserDb1.setValue("True");
                btn.setText("request sent");
            }
        });

        return convertView;


    }
}
