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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private Button mregister, otpBtn;
    private EditText mail, mName, mAge;
    private  EditText mPass;
    String otp, code;
    Double lon,lat;
    int a = 0;
    String isReg = "true";
    FirebaseDatabase database;
    DatabaseReference ref1;
    RadioGroup radioGroup, section;
    RadioButton radioButton, typesec;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mregister = (Button) findViewById(R.id.mRegister);
        mail = (EditText) findViewById(R.id.mail);
        mPass = (EditText) findViewById(R.id.pass);
        otpBtn = (Button)findViewById(R.id.mSendOTP);
        otpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a = 1;
                Toast.makeText(MainActivity.this, "Soon you will get a varification code", Toast.LENGTH_SHORT).show();
                sendVerficationCode();
            }
        });;

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener  = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    if(isReg.equals("true")){
                        Intent intent = new Intent(MainActivity.this, WaitPage2.class);
                        intent.putExtra("isReg", isReg);
                        startActivity(intent);
                        finish();
                        return;
                    }else{
                        Intent intent = new Intent(MainActivity.this, WaitPage2.class);
                        intent.putExtra("isReg", isReg);
                        startActivity(intent);
                        finish();
                        return;
                    }

                }
            }
        };

        mregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mail.getText().toString();
                final String pass = mPass.getText().toString();
                isReg = "false";
                if(a == 1){
                    verifyCode(pass);
                }else{
                    Toast.makeText(MainActivity.this, "Please first submit your mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
    public void sendVerficationCode(){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+mail.getText().toString(),        // Phone number to verify
                60,                                     // Timeout duration
                TimeUnit.SECONDS,                          // Unit of timeout
                this,                              // Activity (for callback binding)
                mCallbacks);                               // OnVerificationStateChangedCallbacks
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Toast.makeText(MainActivity.this, "code is sent!!!", Toast.LENGTH_SHORT).show();
            /*code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                verifyCode(code);
            }*/
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(MainActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            Toast.makeText(MainActivity.this, "sent", Toast.LENGTH_SHORT).show();
            System.out.println(s);
            super.onCodeSent(s, forceResendingToken);
            otp = s;
        }
    };

    private  void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otp, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                }
            }
        });
    }
}
