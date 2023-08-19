package may.internship;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyHolder> {

    Context context;
    String[] catNameArray;
    int[] categoryImageArray;

    public CategoryAdapter(Context context, String[] categoryNameArray, int[] categoryImageArray) {
        this.context = context;
        catNameArray = categoryNameArray;
        this.categoryImageArray = categoryImageArray;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_category,parent,false);
        return new MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        CircleImageView imageView;
        TextView name;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.custom_category_image);
            name = itemView.findViewById(R.id.custom_category_name);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.imageView.setImageResource(categoryImageArray[position]);
        holder.name.setText(catNameArray[position]);
    }

    @Override
    public int getItemCount() {
        return catNameArray.length;
    }

}
