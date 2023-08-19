package may.internship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class TrendingProductActivity extends AppCompatActivity {

    RecyclerView productTrendingRecyclerview;
    String[] productTrendingNameArray = {"Test Jersey (Men)","ODI Jersey (Men)","T20 Jersey (Men)","T20 Jersey (Women)","ODI Jersey (Women)"};
    int[] productTrendingImageArray = {R.drawable.testman,R.drawable.manodi,R.drawable.t20man,R.drawable.t20woman,R.drawable.womenodi};
    String[] productTrendingPriceArray = {"4999","4999","4999","4999","4999"};
    String[] productTrendingUnitArray = {"OneT-shirt","OneT-shirt","OneT-shirt","OneT-shirt","OneT-shirt"};
    String[] productTrendingDescriptionArray = {
            "AN INSPIRING JERSEY FOR AVID CRICKET FANS MADE WITH RECYCLED MATERIALS . The India Test Jersey has landed and its yours for the taking. Test Jersey comes with the iconic 3 Stripes on the shoulder. This comes with mesh insert on the sides for optimum ventilation. Designed in the adidas HEAT.RDY technology, this breathable fabric technology absorbs moisture, dries quickly & keeps you cool while performing.",
            "A VIBRANT JERSEY MADE TO SHARE THE EXCELLENCE OF INDIAN CRICKET TEAM. Celebrate the excellence of team India with a vibrant design to reflect their impact in the community. This astonishing men jersey let amid cricket fans share the players' look. Moisture-wicking AEROREADY ensures you stay comfortable during nerve-shredding games. Made with 100% recycled materials, this product represents just one of our solutions to help end plastic waste.",
            "Butter, a yellow-to-white solid emulsion of fat globules, water, and inorganic salts produced by churning the cream from cows' milk. Butter has long been used as a spread and as a cooking fat. It is an important edible fat in northern Europe, North America, and other places where cattle are the primary dairy animals.",
            "A CRICKET JERSEY THAT LETS YOU DOMINATE THE PITCH MADE WITH PARLEY OCEAN PLASTIC. Women's T20 Jersey has been engineered to ensure a comfortable fit. Each stripe on the shoulder symbolizes the indomitable spirit, one that makes you believe “impossible is nothing.” Designed in the adidas HEAT.RDY technology, this breathable fabric technology absorbs moisture, dries quickly & keeps you cool while performing.",
            "SHOUT OUT LOUD FOR INDIA IN THIS A MOISTURE-WICKING CRICKET JERSEY MADE WITH RECYCLED MATERIALS. Women’s ODI CRICKET jersey is for every Indian Cricket Fan. The ODI jersey design represents the raw power, fierce beauty and undeniable strength of the tiger. You will find it in the traditional ikat pattern that adorns the fabric and brings to life the tiger’s stripes. Each stripe is imbued with an indomitable spirit, one that makes you believe “impossible is nothing.” Designed in the adidas HEAT.RDY technology, this breathable fabric technology absorbs moisture, dries quickly & keeps you cool while performing."
    };
    ArrayList<ProductList> productTrendingArrayList;

    SharedPreferences sp;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending_product);

        sp = getSharedPreferences(ConstantData.PREF,MODE_PRIVATE);

        db = openOrCreateDatabase("MayInternship",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS RECORD(NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT BIGINT(10),PASSWORD VARCHAR(15),DOB VARCHAR(10),GENDER VARCHAR(6),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        String wishlistTableQuery = "CREATE TABLE IF NOT EXISTS WISHLIST(CONTACT INT(10),PRODUCTNAME VARCHAR(100))";
        db.execSQL(wishlistTableQuery);

        productTrendingRecyclerview = findViewById(R.id.trending_product_recyclerview);
        productTrendingRecyclerview.setLayoutManager(new LinearLayoutManager(TrendingProductActivity.this));
        productTrendingRecyclerview.setItemAnimator(new DefaultItemAnimator());
        productTrendingRecyclerview.setNestedScrollingEnabled(false);

        productTrendingArrayList = new ArrayList<>();
        for(int i=0;i<productTrendingNameArray.length;i++){
            ProductList list = new ProductList();
            list.setName(productTrendingNameArray[i]);
            list.setImage(productTrendingImageArray[i]);
            list.setPrice(productTrendingPriceArray[i]);
            list.setUnit(productTrendingUnitArray[i]);
            list.setDescription(productTrendingDescriptionArray[i]);

            String wishlishCheckQuery = "SELECT * FROM WISHLIST WHERE CONTACT='"+sp.getString(ConstantData.CONTACT,"")+"' AND PRODUCTNAME='"+productTrendingNameArray[i]+"'";
            Cursor cursor = db.rawQuery(wishlishCheckQuery,null);
            if(cursor.getCount()>0){
                list.setWishlist(true);
            }
            else{
                list.setWishlist(false);
            }

            String cartCheckQuery = "SELECT * FROM CART WHERE CONTACT='"+sp.getString(ConstantData.CONTACT,"")+"' AND PRODUCTNAME='"+productTrendingNameArray[i]+"' AND ORDERID='0'";
            Cursor cursorCart = db.rawQuery(cartCheckQuery,null);
            if(cursorCart.getCount()>0){
                list.setCart(true);
            }
            else{
                list.setCart(false);
            }

            productTrendingArrayList.add(list);
        }
        ProductTrendingAdapter prodAdapter = new ProductTrendingAdapter(TrendingProductActivity.this,productTrendingArrayList);
        productTrendingRecyclerview.setAdapter(prodAdapter);
    }
}