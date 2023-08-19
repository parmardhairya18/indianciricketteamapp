package may.internship;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.MyHolder> {

    Context context;
    ArrayList<ProductList> productArrayList;
    SharedPreferences sp;

    SQLiteDatabase db;

    public WishlistAdapter(Context context, ArrayList<ProductList> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
        sp = context.getSharedPreferences(ConstantData.PREF,Context.MODE_PRIVATE);

        db = context.openOrCreateDatabase("MayInternship",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS RECORD(NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT BIGINT(10),PASSWORD VARCHAR(15),DOB VARCHAR(10),GENDER VARCHAR(6),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        String wishlistTableQuery = "CREATE TABLE IF NOT EXISTS WISHLIST(CONTACT INT(10),PRODUCTNAME VARCHAR(100))";
        db.execSQL(wishlistTableQuery);

    }

    @NonNull
    @Override
    public WishlistAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_wishlist, parent, false);
        return new WishlistAdapter.MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView imageView,addCart,wishlistFill;
        TextView name, price;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.custom_wishlist_image);
            name = itemView.findViewById(R.id.custom_wishlist_name);
            price = itemView.findViewById(R.id.custom_wishlist_price);
            addCart = itemView.findViewById(R.id.custom_wishlist_cart);
            wishlistFill = itemView.findViewById(R.id.custom_wishlist_fill);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.MyHolder holder, int position) {
        holder.name.setText(productArrayList.get(position).getName());
        holder.price.setText(ConstantData.PRICE_SYMBOL + productArrayList.get(position).getPrice() + "/" + productArrayList.get(position).getUnit());
        holder.imageView.setImageResource(productArrayList.get(position).getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putString(ConstantData.PRODUCT_NAME,productArrayList.get(position).getName()).commit();
                sp.edit().putString(ConstantData.PRODUCT_PRICE,productArrayList.get(position).getPrice()).commit();
                sp.edit().putString(ConstantData.PRODUCT_UNIT,productArrayList.get(position).getUnit()).commit();
                sp.edit().putString(ConstantData.PRODUCT_IMAGE, String.valueOf(productArrayList.get(position).getImage())).commit();
                sp.edit().putString(ConstantData.PRODUCT_DESCRIPTION,productArrayList.get(position).getDescription()).commit();
                new CommonMethod(context,ProductDetailActivity.class);
            }
        });

        holder.addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(context,"Add To Cart");
            }
        });

        holder.wishlistFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String removeQuery = "DELETE FROM WISHLIST WHERE CONTACT='"+sp.getString(ConstantData.CONTACT,"")+"' AND PRODUCTNAME='"+productArrayList.get(position).getName()+"'";
                db.execSQL(removeQuery);

                productArrayList.remove(position);
                notifyDataSetChanged();
                new CommonMethod(context,"Product Removed From Wishlist");
            }
        });

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }
}
