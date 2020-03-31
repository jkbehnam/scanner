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
import android.widget.ImageView;
import android.widget.TextView;

import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.Objects.Doctor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapterDocotrList extends RecyclerView.Adapter<adapterDocotrList.MyViewHolder> {
    private List<Doctor> data_services_list;
    public int lastCheckedPosition = -1;
    Context context;
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //  public TextView card_title;
        ////   public ImageView img;

        @BindView(R.id.cv_doctor)
        CardView cv_doctor;
        @BindView(R.id.iv_doctor)
        ImageView iv_doctor;

        @BindView(R.id.tv_doctor_expert)
        TextView tv_doctor_expert;
        @BindView(R.id.tv_doctor_name)
        TextView tv_doctor_name;
        @BindView(R.id.lay_doctor)
        ConstraintLayout lay_doctor;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            cv_doctor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastCheckedPosition = getAdapterPosition();
                    //because of this blinking problem occurs so
                    //i have a suggestion to add notifyDataSetChanged();
                    //   notifyItemRangeChanged(0, list.length);//blink list problem
                    notifyDataSetChanged();
                }
            });

            // img = (ImageView) view.findViewById(R.id.itemImage);
        }
    }


    public adapterDocotrList(ArrayList<Doctor> data_services_list) {
        this.data_services_list = data_services_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_doctors_list, parent, false);

        this.context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Doctor data_service = data_services_list.get(position);

        holder.tv_doctor_name.setText(data_service.getName());
        holder.tv_doctor_expert.setText(data_service.getExpert());
        if (position == lastCheckedPosition) {
           holder.lay_doctor.setActivated(true);
        }else {holder.lay_doctor.setActivated(false);}

        Typeface typeface3 = Typeface.createFromAsset(context.getAssets(), "font/iran_sans.ttf");
        holder.tv_doctor_name.setTypeface(typeface3, Typeface.BOLD);


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