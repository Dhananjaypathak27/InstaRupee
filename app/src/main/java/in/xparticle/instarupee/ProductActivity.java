package in.xparticle.instarupee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import in.xparticle.instarupee.model.Products;
import in.xparticle.instarupee.utils.AppSession;

public class ProductActivity extends AppCompatActivity {
    TextView title,price,description,city,state,MRP,numberOfMonth;
    ImageView image;
    Button callBtn;
    AppSession appSession;
    Products product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        product= getIntent().getParcelableExtra("product");
        appSession = new AppSession(this);
        title = findViewById(R.id.ap_title);
        price = findViewById(R.id.ap_price);
        description = findViewById(R.id.ap_description);
        city = findViewById(R.id.ap_city);
        state = findViewById(R.id.ap_state);
        MRP = findViewById(R.id.ap_MRP);
        numberOfMonth = findViewById(R.id.ap_numberOfMonth);
        callBtn = findViewById(R.id.ap_callBtn);
        image = findViewById(R.id.ap_imageview);

        if(!appSession.getState().equals(product.getState())){
            callBtn.setBackgroundColor(0xFFAAAAAA);
        }

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClick();
            }
        });
        setData();
    }

    private void btnClick() {

        if(appSession.getState().equals(product.getState())){
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:"+product.getMobileNumber()));
            startActivity(callIntent);
        }

        else{
            Toast.makeText(ProductActivity.this, "You are in Different State", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {

        title.setText(product.getTitle());
        price.setText("₹"+product.getSellingPrice());
        description.setText(product.getProductDescription());
        city.setText(product.getCity());
        state.setText(product.getState());
        MRP.setText("₹"+product.getMRP());
        numberOfMonth.setText(product.getNumberOfMonths());

        byte[] decodedString = Base64.decode(product.getImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        image.setImageBitmap(decodedByte);
        Toast.makeText(this, "session"+appSession.getState()+ "product"+product.getState(), Toast.LENGTH_SHORT).show();
        Log.d("TAG", "setData: "+ "session"+appSession.getState()+ "product"+product.getState());


    }
}