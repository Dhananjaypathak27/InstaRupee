package in.xparticle.instarupee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private Button mLoginBtn,mSignUpBtn;
    private EditText firstName,lastName,mobileNumber,email,city,state,password;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstName = findViewById(R.id.activity_signup_firstname);
        lastName = findViewById(R.id.activity_signup_lastname);
        mobileNumber = findViewById(R.id.activity_signup_phonenumber);
        email = findViewById(R.id.activity_signup_email);
        city = findViewById(R.id.activity_signup_city);
        state = findViewById(R.id.activity_signup_state);
        password = findViewById(R.id.activity_signup_password);
        mLoginBtn = findViewById(R.id.activity_signup_loginBtn);
        mSignUpBtn = findViewById(R.id.activity_signup_signupbtn);
        loadingBar = new ProgressDialog(this);

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUpActivity.this, "sign up", Toast.LENGTH_SHORT).show();
                createAccount();
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUpActivity.this, "login in", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void createAccount() {

        if (firstName.getText().toString().equals("")){
            firstName.setError("Enter First Name");
        }
        else if (lastName.getText().toString().equals("")){
            lastName.setError("Enter Last Name");
        }
        else if (mobileNumber.getText().toString().equals("")){
            mobileNumber.setError("Enter Mobile number");
        }
        else if (mobileNumber.getText().toString().length()!=10){
            firstName.setError("Enter valid Mobile Number");
        }
        else if (email.getText().toString().equals("")){
            email.setError("Enter Email");
        }
        else if (city.getText().toString().equals("")){
            city.setError("Enter Your City Name");
        }
        else if (state.getText().toString().equals("")){
            state.setError("Enter state");
        }
        else if (password.getText().toString().length()<10){
            password.setError("Password must be at least 10 digit");
        }
        else{
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            userValidation(firstName.getText().toString(),lastName.getText().toString(),mobileNumber.getText().toString(),email.getText().toString(),
                    city.getText().toString(),state.getText().toString(),password.getText().toString());

        }


    }

    private void userValidation(String firstName, String lastName, String mobileNumber,
                                String email, String city, String state, String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("Users").child(mobileNumber).exists())){

                    HashMap<String,Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone",mobileNumber);
                    userdataMap.put("firstName",firstName);
                    userdataMap.put("lastName",lastName);
                    userdataMap.put("email",email);
                    userdataMap.put("city",city);
                    userdataMap.put("state",state);
                    userdataMap.put("password",password);

                    RootRef.child("Users").child(mobileNumber).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this, "Congratulation, your account has been created.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        loadingBar.dismiss();
                                        Toast.makeText(SignUpActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(SignUpActivity.this, "This"+ mobileNumber + " already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(SignUpActivity.this, "Please try again using another phone number", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}