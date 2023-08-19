package may.internship;

import static android.content.Context.MODE_PRIVATE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    TextView name;
    SharedPreferences sp;

    RecyclerView categoryRecyclerview;

    String[] categoryNameArray = {"Virat Kohli","Rohit Sharma","Ravindra Jadeja","KL Rahul","Jasprit Bumrah","Mohd. Siraj"};
    int[] categoryImageArray = {R.drawable.kohli,R.drawable.rohit,R.drawable.jaddu,R.drawable.rahul,R.drawable.bumrah1,R.drawable.siraj};

    RecyclerView productRecyclerview;

    String[] productNameArray = {"Test Jersey (Men)","ODI Jersey (Men)","T20 Jersey (Men)","T20 Jersey (Women)","ODI Jersey (Women)"};
    int[] productImageArray = {R.drawable.testman,R.drawable.manodi,R.drawable.t20man,R.drawable.t20woman,R.drawable.womenodi};
    String[] productPriceArray = {"4999","4999","4999","4999","4999"};
    String[] productUnitArray = {"OneT-shirt","OneT-shirt","OneT-shirt","OneT-shirt","OneT-shirt"};
    String[] productDescriptionArray = {
            "AN INSPIRING JERSEY FOR AVID CRICKET FANS MADE WITH RECYCLED MATERIALS . The India Test Jersey has landed and its yours for the taking. Test Jersey comes with the iconic 3 Stripes on the shoulder. This comes with mesh insert on the sides for optimum ventilation. Designed in the adidas HEAT.RDY technology, this breathable fabric technology absorbs moisture, dries quickly & keeps you cool while performing.",
            "A VIBRANT JERSEY MADE TO SHARE THE EXCELLENCE OF INDIAN CRICKET TEAM. Celebrate the excellence of team India with a vibrant design to reflect their impact in the community. This astonishing men jersey let amid cricket fans share the players' look. Moisture-wicking AEROREADY ensures you stay comfortable during nerve-shredding games. Made with 100% recycled materials, this product represents just one of our solutions to help end plastic waste.",
            "Butter, a yellow-to-white solid emulsion of fat globules, water, and inorganic salts produced by churning the cream from cows' milk. Butter has long been used as a spread and as a cooking fat. It is an important edible fat in northern Europe, North America, and other places where cattle are the primary dairy animals.",
            "A CRICKET JERSEY THAT LETS YOU DOMINATE THE PITCH MADE WITH PARLEY OCEAN PLASTIC. Women's T20 Jersey has been engineered to ensure a comfortable fit. Each stripe on the shoulder symbolizes the indomitable spirit, one that makes you believe “impossible is nothing.” Designed in the adidas HEAT.RDY technology, this breathable fabric technology absorbs moisture, dries quickly & keeps you cool while performing.",
            "SHOUT OUT LOUD FOR INDIA IN THIS A MOISTURE-WICKING CRICKET JERSEY MADE WITH RECYCLED MATERIALS. Women’s ODI CRICKET jersey is for every Indian Cricket Fan. The ODI jersey design represents the raw power, fierce beauty and undeniable strength of the tiger. You will find it in the traditional ikat pattern that adorns the fabric and brings to life the tiger’s stripes. Each stripe is imbued with an indomitable spirit, one that makes you believe “impossible is nothing.” Designed in the adidas HEAT.RDY technology, this breathable fabric technology absorbs moisture, dries quickly & keeps you cool while performing."
    };


    ArrayList<ProductList> productArrayList;
    TextView productViewAll;

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
    TextView trendingProductViewAll;

    SQLiteDatabase db;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        sp = getActivity().getSharedPreferences(ConstantData.PREF,MODE_PRIVATE);

        db = getActivity().openOrCreateDatabase("MayInternship",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS RECORD(NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT BIGINT(10),PASSWORD VARCHAR(15),DOB VARCHAR(10),GENDER VARCHAR(6),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        String wishlistTableQuery = "CREATE TABLE IF NOT EXISTS WISHLIST(CONTACT INT(10),PRODUCTNAME VARCHAR(100))";
        db.execSQL(wishlistTableQuery);

        String cartTableQuery = "CREATE TABLE IF NOT EXISTS CART(CONTACT INT(10),ORDERID INT(10),PRODUCTNAME VARCHAR(100),QTY INT(10),PRODUCTPRICE INT(100),PRODUCTUNIT VARCHAR(100),PRODUCTIMAGE INT(100))";
        db.execSQL(cartTableQuery);

        name = view.findViewById(R.id.home_name);

        /*Bundle bundle = getIntent().getExtras();
        name.setText("Welcome "+bundle.getString("NAME"));*/

        name.setText("Welcome "+sp.getString(ConstantData.NAME,""));

        setCategoryData(view);
        setProductData(view);
        setProductTrendingData(view);

        return view;
    }

    private void setCategoryData(View view) {
        categoryRecyclerview = view.findViewById(R.id.home_category);
        categoryRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        categoryRecyclerview.setItemAnimator(new DefaultItemAnimator());

        CategoryAdapter catAdapter = new CategoryAdapter(getActivity(),categoryNameArray,categoryImageArray);
        categoryRecyclerview.setAdapter(catAdapter);
    }

    private void setProductData(View view) {

        productViewAll = view.findViewById(R.id.home_product_view_all);
        productViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(getActivity(),ProductActivity.class);
            }
        });

        productRecyclerview = view.findViewById(R.id.home_product_recyclerview);
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
        ProductAdapter prodAdapter = new ProductAdapter(getActivity(),productArrayList);
        productRecyclerview.setAdapter(prodAdapter);
    }

    private void setProductTrendingData(View view) {

        trendingProductViewAll = view.findViewById(R.id.home_product_trending_view_all);
        trendingProductViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(getActivity(),TrendingProductActivity.class);
            }
        });

        productTrendingRecyclerview = view.findViewById(R.id.home_product_trending_recyclerview);
        productTrendingRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        ProductTrendingAdapter prodAdapter = new ProductTrendingAdapter(getActivity(),productTrendingArrayList);
        productTrendingRecyclerview.setAdapter(prodAdapter);
    }

}