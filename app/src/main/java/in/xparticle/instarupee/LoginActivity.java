package in.xparticle.instarupee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import in.xparticle.instarupee.model.Users;
import in.xparticle.instarupee.utils.AppSession;

public class LoginActivity extends AppCompatActivity {

    Button mLoginBtn,mSignUpBtn;
    ImageView backBtn;
    private String parentDbName = "Users";
    private static final String TAG = "LoginActivity";
    private EditText mPhoneNumber,mPassword;
    private ProgressDialog loadingBar;
    AppSession appSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        backBtn = findViewById(R.id.login_backbtn);
        mLoginBtn = findViewById(R.id.activity_login_loginBtn);
        mSignUpBtn = findViewById(R.id.activity_login_signUpBtn);
        mPhoneNumber = findViewById(R.id.activity_login_phonenumber);
        mPassword = findViewById(R.id.activity_login_password);
        loadingBar = new ProgressDialog(this);
        appSession = new AppSession(this);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void LoginUser() {

        String phone = mPhoneNumber.getText().toString();
        String password = mPassword.getText().toString();

        if(phone.equals("")){
            mPhoneNumber.setError("Enter Phone Number");
        }
        else if(password.equals("")){
            mPassword.setError("Enter your Password");
        }
        else{
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            allowAccessToAccount(phone,password);
        }

    }

    private void allowAccessToAccount(String phone, String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(parentDbName).child(phone).exists()){

                    Users usersData = snapshot.child(parentDbName).child(phone).getValue(Users.class);
                    Log.d(TAG, "onDataChange: "+usersData.toString());
                    if(usersData.getPhone().equals(phone)){
                        if(usersData.getPassword().equals(password)){
                            if(parentDbName.equals("Users")){
                                Toast.makeText(LoginActivity.this, "login successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Log.d(TAG, "onDataChange: is here"+ usersData.getCity()+ usersData.getEmail() + usersData.getPhone()+ usersData);
                                Log.d(TAG, "onDataChange: by name "+usersData.firstName+ usersData.getPhone()+ usersData.email+ usersData.state);
                                appSession.setLogin(true);
                                appSession.setPhoneNumber(phone);
                                appSession.setPassword(password);
                                appSession.setCity(usersData.getCity());
                                appSession.setEmail(usersData.getEmail());
                                appSession.setState(usersData.getState());
                                appSession.setFirstName(usersData.getFirstName());
                                appSession.setLastName(usersData.getLastName());

                                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }

                }
                else{
                    Toast.makeText(LoginActivity.this, "Account with "+ phone+" number does not exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}