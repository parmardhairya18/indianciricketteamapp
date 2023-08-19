package may.internship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class DashboardActivity extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;

    int HOME_BOTTOM = 1;
    int CART_BOTTOM = 2;
    int WISHLIST_BOTTOM = 3;
    int PROFILE_BOTTOM = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNavigation = findViewById(R.id.home_bottom);

        bottomNavigation.add(new MeowBottomNavigation.Model(HOME_BOTTOM, R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(CART_BOTTOM, R.drawable.add_cart));
        bottomNavigation.add(new MeowBottomNavigation.Model(WISHLIST_BOTTOM, R.drawable.wishlist_fill));
        bottomNavigation.add(new MeowBottomNavigation.Model(PROFILE_BOTTOM, R.drawable.user));

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                if (item.getId() == HOME_BOTTOM) {
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.home_main, new HomeFragment()).commit();
                }
                if (item.getId() == CART_BOTTOM) {
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.home_main, new CartFragment()).commit();
                }
                if (item.getId() == WISHLIST_BOTTOM) {
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.home_main, new WishlistFragment()).commit();
                }
                if (item.getId() == PROFILE_BOTTOM) {
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.home_main, new ProfileFragment()).commit();
                }
                //Toast.makeText(DashboardActivity.this, "clicked item : " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //Toast.makeText(HomeActivity.this, "showing item : " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                // your codes
            }
        });

        bottomNavigation.show(HOME_BOTTOM, true);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.home_main, new HomeFragment()).commit();

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finishAffinity();
    }

}