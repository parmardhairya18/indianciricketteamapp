package may.internship;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class WishlistFragment extends Fragment {

    RecyclerView productTrendingRecyclerview;
    /*String[] productTrendingNameArray = {"Travel Package","Cloth","Butter","Makup Kit","Bread"};
    int[] productTrendingImageArray = {R.drawable.travel_package,R.drawable.cloth,R.drawable.butter,R.drawable.makup_kit,R.drawable.bread};
    String[] productTrendingPriceArray = {"10000","2000","150","1500","100"};
    String[] productTrendingUnitArray = {"Package","Pair","500 GM","Box","Packet"};
    String[] productTrendingDescriptionArray = {
            "Choose from dozens of Holiday Packages &amp; Book your Dream Vacation with MakeMyTrip. Grab exciting discounts for your upcoming Holidays to our most-loved destinations. Customized Tour Packages. Best Deals Guaranteed. Special Online Discounts.",
            "Cloth is fabric, a woven material. When you sew your own clothes, you start with a piece of cloth. Cloth is made from some sort of fiber, often cotton or wool",
            "Butter, a yellow-to-white solid emulsion of fat globules, water, and inorganic salts produced by churning the cream from cows' milk. Butter has long been used as a spread and as a cooking fat. It is an important edible fat in northern Europe, North America, and other places where cattle are the primary dairy animals.",
            "According to Oxford.com, makeup is defined as cosmetics such as lipstick or powder applied to the face, used to enhance or alter the appearance",
            "Bread, baked food product made of flour or meal that is moistened, kneaded, and sometimes fermented. A major food since prehistoric times, it has been made in various forms using a variety of ingredients and methods throughout the world."
    };*/

    ArrayList<ProductList> productTrendingArrayList;

    SQLiteDatabase db;
    SharedPreferences sp;

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


    public WishlistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        sp = getActivity().getSharedPreferences(ConstantData.PREF,MODE_PRIVATE);

        db = getActivity().openOrCreateDatabase("MayInternship",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS RECORD(NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT BIGINT(10),PASSWORD VARCHAR(15),DOB VARCHAR(10),GENDER VARCHAR(6),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        String wishlistTableQuery = "CREATE TABLE IF NOT EXISTS WISHLIST(CONTACT INT(10),PRODUCTNAME VARCHAR(100))";
        db.execSQL(wishlistTableQuery);

        productTrendingRecyclerview = view.findViewById(R.id.wishlist_recyclerview);
        productTrendingRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        productTrendingRecyclerview.setItemAnimator(new DefaultItemAnimator());
        productTrendingRecyclerview.setNestedScrollingEnabled(false);

        String selectQuery = "SELECT * FROM WISHLIST WHERE CONTACT='"+sp.getString(ConstantData.CONTACT,"")+"'";
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.getCount()>0){
            productTrendingArrayList = new ArrayList<>();
            while (cursor.moveToNext()){
                for(int i =0 ; i<productNameArray.length;i++){
                    if(cursor.getString(1).equalsIgnoreCase(productNameArray[i])){
                        ProductList list = new ProductList();
                        list.setName(productNameArray[i]);
                        list.setImage(productImageArray[i]);
                        list.setPrice(productPriceArray[i]);
                        list.setUnit(productUnitArray[i]);
                        list.setDescription(productDescriptionArray[i]);
                        productTrendingArrayList.add(list);
                    }
                }
            }
            WishlistAdapter prodAdapter = new WishlistAdapter(getActivity(),productTrendingArrayList);
            productTrendingRecyclerview.setAdapter(prodAdapter);
        }

        /*productTrendingArrayList = new ArrayList<>();
        for(int i=0;i<productTrendingNameArray.length;i++){
            ProductList list = new ProductList();
            list.setName(productTrendingNameArray[i]);
            list.setImage(productTrendingImageArray[i]);
            list.setPrice(productTrendingPriceArray[i]);
            list.setUnit(productTrendingUnitArray[i]);
            list.setDescription(productTrendingDescriptionArray[i]);
            productTrendingArrayList.add(list);
        }
        WishlistAdapter prodAdapter = new WishlistAdapter(getActivity(),productTrendingArrayList);
        productTrendingRecyclerview.setAdapter(prodAdapter);*/
        return view;
    }
}