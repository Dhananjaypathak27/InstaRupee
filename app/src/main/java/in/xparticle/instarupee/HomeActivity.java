package in.xparticle.instarupee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import in.xparticle.instarupee.fragment.FavoriteFragment;
import in.xparticle.instarupee.fragment.HomeFragment;

public class HomeActivity extends AppCompatActivity  {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);

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

//                    case R.id.nav_procedure:
//                        selectedFragment = new ProcedureFragment();
//                        break;
//
//                    case R.id.nav_info:
//                        selectedFragment = new AboutUsFragment();
//                        break;
                }
                assert selectedFragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;
            }
        });

    }
}