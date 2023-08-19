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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {

    Context context;
    ArrayList<ProductList> productArrayList;
    SharedPreferences sp;

    SQLiteDatabase db;

    public ProductAdapter(Context context, ArrayList<ProductList> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
        sp = context.getSharedPreferences(ConstantData.PREF, MODE_PRIVATE);

        db = context.openOrCreateDatabase("MayInternship",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS RECORD(NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT BIGINT(10),PASSWORD VARCHAR(15),DOB VARCHAR(10),GENDER VARCHAR(6),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        String wishlistTableQuery = "CREATE TABLE IF NOT EXISTS WISHLIST(CONTACT INT(10),PRODUCTNAME VARCHAR(100))";
        db.execSQL(wishlistTableQuery);

        String cartTableQuery = "CREATE TABLE IF NOT EXISTS CART(CONTACT INT(10),ORDERID INT(10),PRODUCTNAME VARCHAR(100),QTY INT(10),PRODUCTPRICE INT(100),PRODUCTUNIT VARCHAR(100),PRODUCTIMAGE INT(100))";
        db.execSQL(cartTableQuery);

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product,parent,false);
        return new MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView imageView,addCart,removeCart,wishlist,wishlistFill;
        TextView name,price;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.custom_product_image);
            name = itemView.findViewById(R.id.custom_product_name);
            price = itemView.findViewById(R.id.custom_product_price);
            addCart = itemView.findViewById(R.id.custom_product_cart);
            removeCart = itemView.findViewById(R.id.custom_product_cart_remove);
            wishlist = itemView.findViewById(R.id.custom_product_wishlist);
            wishlistFill = itemView.findViewById(R.id.custom_product_wishlist_fill);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.name.setText(productArrayList.get(position).getName());
        holder.price.setText(ConstantData.PRICE_SYMBOL+productArrayList.get(position).getPrice()+"/"+productArrayList.get(position).getUnit());
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

        if(productArrayList.get(position).isCart()){
            holder.addCart.setVisibility(View.GONE);
            holder.removeCart.setVisibility(View.VISIBLE);
        }
        else{
            holder.addCart.setVisibility(View.VISIBLE);
            holder.removeCart.setVisibility(View.GONE);
        }

        holder.addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int iQty = 1;
                int productPrice = Integer.parseInt(productArrayList.get(position).getPrice());
                int iTotalPrice = iQty * productPrice;
                String insertQuery = "INSERT INTO CART VALUES('"+sp.getString(ConstantData.CONTACT,"")+"','0','"+productArrayList.get(position).getName()+"','"+iQty+"','"+iTotalPrice+"','"+productArrayList.get(position).getUnit()+"','"+productArrayList.get(position).getImage()+"')";
                db.execSQL(insertQuery);
                holder.addCart.setVisibility(View.GONE);
                holder.removeCart.setVisibility(View.VISIBLE);
            }
        });

        holder.removeCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String removeQuery = "DELETE FROM CART WHERE CONTACT='"+sp.getString(ConstantData.CONTACT,"")+"' AND PRODUCTNAME='"+productArrayList.get(position).getName()+"' AND ORDERID='0'";
                db.execSQL(removeQuery);
                holder.addCart.setVisibility(View.VISIBLE);
                holder.removeCart.setVisibility(View.GONE);
            }
        });

        if(productArrayList.get(position).isWishlist()){
            holder.wishlist.setVisibility(View.GONE);
            holder.wishlistFill.setVisibility(View.VISIBLE);
        }
        else{
            holder.wishlist.setVisibility(View.VISIBLE);
            holder.wishlistFill.setVisibility(View.GONE);
        }

        holder.wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String insertQuery = "INSERT INTO WISHLIST VALUES('"+sp.getString(ConstantData.CONTACT,"")+"','"+productArrayList.get(position).getName()+"')";
                db.execSQL(insertQuery);
                holder.wishlist.setVisibility(View.GONE);
                holder.wishlistFill.setVisibility(View.VISIBLE);
            }
        });

        holder.wishlistFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String removeQuery = "DELETE FROM WISHLIST WHERE CONTACT='"+sp.getString(ConstantData.CONTACT,"")+"' AND PRODUCTNAME='"+productArrayList.get(position).getName()+"'";
                db.execSQL(removeQuery);
                holder.wishlist.setVisibility(View.VISIBLE);
                holder.wishlistFill.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }
}
