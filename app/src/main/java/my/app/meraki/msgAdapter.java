package my.app.meraki;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class msgAdapter extends ArrayAdapter<msg> {

    private  static  final String TAG = "peopleAdapter";
    private Context mContext;
    int mResource;
    private FirebaseAuth mAuth;

    public msgAdapter(@NonNull Context context, int resource, @NonNull List<msg> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String text = getItem(position).getMsg();
        String sender = getItem(position).getSender();
        //final String uid = getItem(position).getUid();

        //msg msg = new msg(text);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView tv = (TextView) convertView.findViewById(R.id.msgId);
        tv.setText(text);
        if(sender.equals("Me")){
            tv.setBackgroundColor(Color.parseColor("#aaaaaa"));
            tv.setGravity(Gravity.RIGHT);
        }
        return convertView;
    }
}

