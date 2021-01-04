package in.xparticle.instarupee;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, price, title,city;
    public ImageView imageView;
    public ItemClickListner listner;


    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.item_ImageView);
        price = itemView.findViewById(R.id.item_rupee);
        title = itemView.findViewById(R.id.item_product_title);
        city = itemView.findViewById(R.id.item_cityname);
    }

    public void setItemClickListner(ItemClickListner listner){

        this.listner = listner;

    }



    @Override
    public void onClick(View view) {
        listner.onClick(view,getAdapterPosition(),false);
    }
}
