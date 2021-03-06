package com.patient.mokhtari.scanner.activities.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.Objects.Duration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapterdurationList extends RecyclerView.Adapter<adapterdurationList.MyViewHolder> {
    private final List<Duration> data_services_list;
    public int lastCheckedPosition = -1;
    Context context;
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //  public TextView card_title;
        ////   public ImageView img;

        @BindView(R.id.cv_doctor)
        CardView cv_doctor;


        @BindView(R.id.tv_duration)
        TextView tv_duration;

        @BindView(R.id.lay_doctor)
        ConstraintLayout lay_doctor;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            cv_doctor.setOnClickListener(view1 -> {
                lastCheckedPosition = getAdapterPosition();
                onCardClickListner.OnCardClicked(view1, getAdapterPosition());
                //because of this blinking problem occurs so
                //i have a suggestion to add notifyDataSetChanged();
                //   notifyItemRangeChanged(0, list.length);//blink list problem
                notifyDataSetChanged();
            });

            // img = (ImageView) view.findViewById(R.id.itemImage);
        }
    }


    public adapterdurationList(ArrayList<Duration> data_services_list) {
        this.data_services_list = data_services_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_duration_list_test, parent, false);

        this.context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Duration data_service = data_services_list.get(position);

        holder.tv_duration.setText(data_service.getTitle());

        if (position == lastCheckedPosition) {
           holder.lay_doctor.setActivated(true);
        }else {holder.lay_doctor.setActivated(false);}

        Typeface typeface3 = Typeface.createFromAsset(context.getAssets(), "font/iran_sans.ttf");
        holder.tv_duration.setTypeface(typeface3, Typeface.BOLD);


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