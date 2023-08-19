package may.internship;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyHolder> {

    Context context;
    ArrayList<CartList> productArrayList;
    SharedPreferences sp;
    public CartAdapter(Context context, ArrayList<CartList> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
        sp = context.getSharedPreferences(ConstantData.PREF,Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cart,parent,false);
        return new MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView imageView,removeCart,wishlist,wishlistFill;
        TextView name,price,qty;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.custom_cart_image);
            name = itemView.findViewById(R.id.custom_cart_name);
            qty = itemView.findViewById(R.id.custom_cart_qty);
            price = itemView.findViewById(R.id.custom_cart_price);
            removeCart = itemView.findViewById(R.id.custom_cart_remove);
            wishlist = itemView.findViewById(R.id.custom_cart_wishlist);
            wishlistFill = itemView.findViewById(R.id.custom_cart_wishlist_fill);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.name.setText(productArrayList.get(position).getName());

        holder.qty.setText("Qty : "+productArrayList.get(position).getQty());

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

        holder.removeCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartFragment.iTotalAmount = CartFragment.iTotalAmount - Integer.parseInt(productArrayList.get(position).getPrice());
                CartFragment.totalAmount.setText("Total : "+ConstantData.PRICE_SYMBOL+CartFragment.iTotalAmount);

                if(CartFragment.iTotalAmount>0){
                    CartFragment.totalAmount.setVisibility(View.VISIBLE);
                    CartFragment.checkout.setVisibility(View.VISIBLE);
                }
                else{
                    CartFragment.totalAmount.setVisibility(View.GONE);
                    CartFragment.checkout.setVisibility(View.GONE);
                }

                productArrayList.remove(position);
                new CommonMethod(context,"Remove From Cart");
                notifyDataSetChanged();
            }
        });

        holder.wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.wishlist.setVisibility(View.GONE);
                holder.wishlistFill.setVisibility(View.VISIBLE);
            }
        });

        holder.wishlistFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
