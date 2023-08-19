package may.internship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    RecyclerView productRecyclerview;

    String[] productNameArray = {"TestManJersey","ODIManJersey","T20ManJersey","T20WomenJersey","ODIWomenJersey"};
    int[] productImageArray = {R.drawable.travel_package,R.drawable.cloth,R.drawable.butter,R.drawable.makup_kit,R.drawable.bread};
    String[] productPriceArray = {"4999","4999","4999","4999","4999"};
    String[] productUnitArray = {"OneTshirt","OneTshirt","OneTshirt","OneTshirt","OneTshirt"};
    String[] productDescriptionArray = {
            "AN INSPIRING JERSEY FOR AVID CRICKET FANS MADE WITH RECYCLED MATERIALS . The India Test Jersey has landed and its yours for the taking. Test Jersey comes with the iconic 3 Stripes on the shoulder. This comes with mesh insert on the sides for optimum ventilation. Designed in the adidas HEAT.RDY technology, this breathable fabric technology absorbs moisture, dries quickly & keeps you cool while performing.",
            "A VIBRANT JERSEY MADE TO SHARE THE EXCELLENCE OF INDIAN CRICKET TEAM. Celebrate the excellence of team India with a vibrant design to reflect their impact in the community. This astonishing men jersey let amid cricket fans share the players' look. Moisture-wicking AEROREADY ensures you stay comfortable during nerve-shredding games. Made with 100% recycled materials, this product represents just one of our solutions to help end plastic waste.",
            "Butter, a yellow-to-white solid emulsion of fat globules, water, and inorganic salts produced by churning the cream from cows' milk. Butter has long been used as a spread and as a cooking fat. It is an important edible fat in northern Europe, North America, and other places where cattle are the primary dairy animals.",
            "A CRICKET JERSEY THAT LETS YOU DOMINATE THE PITCH MADE WITH PARLEY OCEAN PLASTIC. Women's T20 Jersey has been engineered to ensure a comfortable fit. Each stripe on the shoulder symbolizes the indomitable spirit, one that makes you believe “impossible is nothing.” Designed in the adidas HEAT.RDY technology, this breathable fabric technology absorbs moisture, dries quickly & keeps you cool while performing.",
            "SHOUT OUT LOUD FOR INDIA IN THIS A MOISTURE-WICKING CRICKET JERSEY MADE WITH RECYCLED MATERIALS. Women’s ODI CRICKET jersey is for every Indian Cricket Fan. The ODI jersey design represents the raw power, fierce beauty and undeniable strength of the tiger. You will find it in the traditional ikat pattern that adorns the fabric and brings to life the tiger’s stripes. Each stripe is imbued with an indomitable spirit, one that makes you believe “impossible is nothing.” Designed in the adidas HEAT.RDY technology, this breathable fabric technology absorbs moisture, dries quickly & keeps you cool while performing."
    };

    ArrayList<ProductList> productArrayList;

    SharedPreferences sp;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        sp = getSharedPreferences(ConstantData.PREF,MODE_PRIVATE);

        db = openOrCreateDatabase("MayInternship",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS RECORD(NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT BIGINT(10),PASSWORD VARCHAR(15),DOB VARCHAR(10),GENDER VARCHAR(6),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        String wishlistTableQuery = "CREATE TABLE IF NOT EXISTS WISHLIST(CONTACT INT(10),PRODUCTNAME VARCHAR(100))";
        db.execSQL(wishlistTableQuery);

        productRecyclerview = findViewById(R.id.product_recyclerview);
        productRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        productRecyclerview.setItemAnimator(new DefaultItemAnimator());
        productRecyclerview.setNestedScrollingEnabled(false);

        productArrayList = new ArrayList<>();
        for(int i=0;i<productNameArray.length;i++){
            ProductList list = new ProductList();
            list.setName(productNameArray[i]);
            list.setImage(productImageArray[i]);
            list.setPrice(productPriceArray[i]);
            list.setUnit(productUnitArray[i]);
            list.setDescription(productDescriptionArray[i]);

            String wishlishCheckQuery = "SELECT * FROM WISHLIST WHERE CONTACT='"+sp.getString(ConstantData.CONTACT,"")+"' AND PRODUCTNAME='"+productNameArray[i]+"'";
            Cursor cursor = db.rawQuery(wishlishCheckQuery,null);
            if(cursor.getCount()>0){
                list.setWishlist(true);
            }
            else{
                list.setWishlist(false);
            }

            String cartCheckQuery = "SELECT * FROM CART WHERE CONTACT='"+sp.getString(ConstantData.CONTACT,"")+"' AND PRODUCTNAME='"+productNameArray[i]+"' AND ORDERID='0'";
            Cursor cursorCart = db.rawQuery(cartCheckQuery,null);
            if(cursorCart.getCount()>0){
                list.setCart(true);
            }
            else{
                list.setCart(false);
            }

            productArrayList.add(list);
        }
        ProductAdapter prodAdapter = new ProductAdapter(ProductActivity.this,productArrayList);
        productRecyclerview.setAdapter(prodAdapter);
    }
}