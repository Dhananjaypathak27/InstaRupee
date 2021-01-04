package in.xparticle.instarupee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import in.xparticle.instarupee.model.Users;
import in.xparticle.instarupee.utils.AppSession;

public class ManageProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner spinnerState,spinnerCity;
    String state ="",city="";
    private ProgressDialog loadingBar;
    private String parentDbName = "Users";
    TextView currentCity,currentState;
    private Button updateProfileBtn;
    private EditText firstName,lastName,password;
    AppSession appSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_profile);


        appSession =new AppSession(this);
        updateProfileBtn = findViewById(R.id.activity_manageProfile_update);
        firstName = findViewById(R.id.activity_manageProfile_firstname);
        currentCity = findViewById(R.id.activity_manageProfile_currentCity);
        currentState = findViewById(R.id.activity_manageProfile_currentState);
        lastName = findViewById(R.id.activity_manageProfile_lastname);
        password = findViewById(R.id.activity_manageProfile_password);
        loadingBar = new ProgressDialog(this);
        spinnerCity = findViewById(R.id.activity_manageProfile_city);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.india_top_places,R.layout.support_simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapter1);
        spinnerCity.setOnItemSelectedListener(this);

        spinnerState = findViewById(R.id.activity_manageProfile_state);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.india_states,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerState.setAdapter(adapter);
        spinnerState.setOnItemSelectedListener(this);

        setDefalultValue();

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });


    }

    private void updateProfile() {
        if(firstName.getText().equals(appSession.getFirstName())
                || lastName.getText().equals(appSession.getLastName())
                || city.equals(appSession.getCity())
                || state.equals(appSession.getState())){
            Toast.makeText(this, "No change in user profile", Toast.LENGTH_SHORT).show();
        }
        else if (password.getText().toString().length()<10){
            password.setError("Password must be at least 10 digit");
        }
        else{
            loadingBar.setTitle("Updating Profile");
            loadingBar.setMessage("Please wait, while we are updating your profile.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            userValidation(city,state);
        }
    }

    private void userValidation(String city,String state) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("firstName",firstName.getText().toString());
        userMap.put("lastName",lastName.getText().toString());
        if(!city.equals("Select your current City")){
            userMap.put("city",city);
        }
        if(!state.equals("Select your current State")){
            userMap.put("state",state);
        }
        userMap.put("password",password.getText().toString());

        ref.child(appSession.getPhoneNumber()).updateChildren(userMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            if(!city.equals("Select your current City")){
                                appSession.setCity(city);
                            }

                            if(!state.equals("Select your current State")){
                                appSession.setState(state);
                            }
                            appSession.setFirstName(firstName.getText().toString());
                            appSession.setLastName(lastName.getText().toString());
                            appSession.setPassword(password.getText().toString());

                            Toast.makeText(ManageProfileActivity.this, "Congratulation, your profile has been updated.", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            finish();
                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(ManageProfileActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void setDefalultValue() {

        firstName.setText(appSession.getFirstName());
        lastName.setText(appSession.getLastName());
        password.setText(appSession.getPassword());
        currentState.setText(appSession.getState());
        currentCity.setText(appSession.getCity());

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.activity_manageProfile_state)
            state = adapterView.getItemAtPosition(i).toString();

        if(adapterView.getId() == R.id.activity_manageProfile_city)
            city = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}