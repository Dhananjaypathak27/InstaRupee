package in.xparticle.instarupee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import in.xparticle.instarupee.fragment.FavoriteFragment;
import in.xparticle.instarupee.fragment.HomeFragment;
import in.xparticle.instarupee.fragment.ProfileFragment;
import in.xparticle.instarupee.utils.AppSession;

public class HomeActivity extends AppCompatActivity  {

    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        floatingActionButton = findViewById(R.id.fab);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,PublishAddActivity.class);
                startActivity(intent);
            }
        });

        intent_function();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();



    }

    private void intent_function() {

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.miHome:
                        selectedFragment =new HomeFragment();
                        break;

                    case R.id.miFav:
                        selectedFragment = new FavoriteFragment();
                        break;

                    case R.id.miChat:
                        selectedFragment = new HomeFragment();
                        Toast.makeText(HomeActivity.this, "Currently chat feature is not available", Toast.LENGTH_SHORT).show();
                        break;
//
                    case R.id.miProfile:
                        selectedFragment = new ProfileFragment();
                        break;
                }
                assert selectedFragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            }
        });

    }
}