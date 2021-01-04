package in.xparticle.instarupee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import in.xparticle.instarupee.utils.AppSession;

public class PublishAddActivity extends AppCompatActivity {

    AppSession appSession;
    ImageView backBtn;
    Button submitBtn;
    EditText title,numberOfMonths,productDescription,MRP,sellingPrice;
    Bitmap image;
    ImageView camera;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_add);

        backBtn = findViewById(R.id.publish_backBtn);
        camera = findViewById(R.id.imageView4);
        submitBtn = findViewById(R.id.activity_publish_add_submitBtn);
        title = findViewById(R.id.activity_publish_add_title);
        numberOfMonths = findViewById(R.id.activity_publish_add_numberOfMonths);
        productDescription = findViewById(R.id.activity_publish_add_product_description);
        MRP = findViewById(R.id.activity_publish_add_MRP);
        sellingPrice = findViewById(R.id.activity_publish_add_sellingPrice);
        appSession = new AppSession(this);
        loadingBar = new ProgressDialog(this);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });
    }

    private void validateData() {

        if(title.getText().toString().equals("")){
            title.setError("Enter Title");
        }
        else if(numberOfMonths.toString().equals("")){
            numberOfMonths.setError("Enter Number of months");
        }
        else if(productDescription.toString().equals("")){
            productDescription.setError("Enter Product Description");
        }
        else if(MRP.toString().equals("")){
            MRP.setError("Enter MRP of product");
        }
        else if(sellingPrice.toString().equals("")){
            sellingPrice.setError("Enter Selling Price");
        }
        else{
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            publishProductOnFirebase(title.getText().toString(),numberOfMonths.getText().toString(),productDescription.getText().toString(),
                    MRP.getText().toString(),sellingPrice.getText().toString());
        }

    }

    private String encodedImage(Bitmap image){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encoded;
    }

    private void publishProductOnFirebase(String title, String numberOfMonths,
                                          String productDescription, String MRP, String sellingPrice) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String saveCurrentDate,saveCurrentTime,imageRandomName;
                Calendar calendar = Calendar.getInstance();

                SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                saveCurrentDate = currentDate.format(calendar.getTime());

                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime = currentTime.format(calendar.getTime());

                BitmapDrawable drawable = (BitmapDrawable) camera.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                String encodedImage = encodedImage(bitmap);
                imageRandomName = saveCurrentDate + saveCurrentTime;
                if(!(snapshot.child("Products").child(imageRandomName).exists())){

                    HashMap<String,Object> userdataMap = new HashMap<>();
                    userdataMap.put("title",title);
                    userdataMap.put("numberOfMonths",numberOfMonths);
                    userdataMap.put("productDescription",productDescription);
                    userdataMap.put("image",encodedImage);
                    userdataMap.put("state",appSession.getState());
                    userdataMap.put("sellingPrice",sellingPrice);
                    userdataMap.put("isAvailable","1");
                    userdataMap.put("mobileNumber",appSession.getPhoneNumber());
                    userdataMap.put("city",appSession.getCity());
                    userdataMap.put("MRP",MRP);

                    RootRef.child("Products").child(imageRandomName).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(PublishAddActivity.this, "Congratulation, Your advertisement has been published", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        finish();
                                    }
                                    else {
                                        loadingBar.dismiss();
                                        Toast.makeText(PublishAddActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(PublishAddActivity.this, "This"+ imageRandomName + "product already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(PublishAddActivity.this, "Please try again after sometime", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0 && resultCode == RESULT_OK){
            assert data != null;
            image = (Bitmap) data.getExtras().get("data");
            if(image != null){
                camera.setImageBitmap(image);
            }
        }
    }
}