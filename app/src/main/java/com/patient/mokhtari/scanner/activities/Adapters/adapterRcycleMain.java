package com.patient.mokhtari.scanner.activities.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.Objects.MainList;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapterRcycleMain extends RecyclerView.Adapter<adapterRcycleMain.MyViewHolder> {
    private final List<MainList> data_services_list;

    Context context;
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //  public TextView card_title;
        ////   public ImageView img;

        @BindView(R.id.item_game_cv)
        CardView cv;
        @BindView(R.id.item_game_tv)
        TextView tv;
        @BindView(R.id.item_game_img)
        ImageView iv;
        @BindView(R.id.tv_mainList)
        TextView tv_number;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            Typeface typeface3 = Typeface.createFromAsset(context.getAssets(), "font/iran_sans.ttf");
            tv.setTypeface(typeface3, Typeface.BOLD);
            // img = (ImageView) view.findViewById(R.id.itemImage);
        }
    }


    public adapterRcycleMain(ArrayList<MainList> data_services_list) {
        this.data_services_list = data_services_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_new_request, parent, false);

        this.context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final MainList data_service = data_services_list.get(position);

        holder.tv.setText(data_service.getTitle());
        holder.tv_number.setText(String.valueOf(position + 1));

        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

        // holder.iv.setImageResource(R.drawable.transaction);
        Glide.with(context).load(getImage(data_service.getImg())).into(holder.iv);
        if (!data_service.isState()) {
            holder.iv.setColorFilter(filter);
            holder.tv_number.setBackgroundTintList(context.getResources().getColorStateList(R.color.grey_40));
        }
        holder.cv.setOnClickListener(v -> onCardClickListner.OnCardClicked(v, position));

    }

    @Override
    public int getItemCount() {
        return data_services_list.size();
    }

    public interface OnCardClickListner {
        void OnCardClicked(View view, int position);
    }

    public void setOnCardClickListner(OnCardClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }

    public int getImage(String imageName) {

        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }
}