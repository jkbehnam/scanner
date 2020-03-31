package com.patient.mokhtari.scanner.activities.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.New_request.select_photo.GlideApp;
import com.patient.mokhtari.scanner.activities.Objects.AddImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapterAddPhoto extends RecyclerView.Adapter<adapterAddPhoto.MyViewHolder> {
    private List<AddImage> data_services_list;

    Context context;
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //  public TextView card_title;
        ////   public ImageView img;

        @BindView(R.id.cv_add_photo)
        CardView cv_add_photo;
        @BindView(R.id.iv_add_photo)
        ImageView iv_add_photo;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            // img = (ImageView) view.findViewById(R.id.itemImage);
        }
    }


    public adapterAddPhoto(ArrayList<AddImage> data_services_list) {
        this.data_services_list = data_services_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo_insert, parent, false);

        this.context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final AddImage data_service = data_services_list.get(position);
        if (!data_service.getAddress().equals("")) {
            GlideApp.with(context).load(data_service.getAddress())
                    .into(holder.iv_add_photo);
            holder.iv_add_photo.setColorFilter(ContextCompat.getColor(context, android.R.color.transparent));
        }
        // write code
        holder.cv_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCardClickListner.OnCardClicked(view, position);
            }
        });

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

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        return drawableResourceId;
    }


}