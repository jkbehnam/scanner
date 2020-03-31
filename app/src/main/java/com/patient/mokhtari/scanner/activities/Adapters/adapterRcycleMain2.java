package com.patient.mokhtari.scanner.activities.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.Objects.requests;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by behnam_b on 7/5/2016.
 */
public class adapterRcycleMain2 extends RecyclerView.Adapter<adapterRcycleMain2.MyViewHolder> {
    private List<requests> data_services_list;

    Context context;
    OnCardClickListner onCardClickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //  public TextView card_title;
        ////   public ImageView img;

        @BindView(R.id.cv_request)
        CardView cv_request;
        @BindView(R.id.tv_reqiest_bodypart)
        TextView tv_reqiest_bodypart;
        @BindView(R.id.tv_request_date)
        TextView tv_request_date;
        @BindView(R.id.tv_request_doctor)
        TextView tv_request_doctor;
        @BindView(R.id.tv_request_state)
        TextView tv_request_state;
        @BindView(R.id.iv_requet)
        ImageView iv_requet;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            // img = (ImageView) view.findViewById(R.id.itemImage);
        }
    }


    public adapterRcycleMain2(ArrayList<requests> data_services_list) {
        this.data_services_list = data_services_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_request_list, parent, false);

        this.context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final requests data_service = data_services_list.get(position);

        holder.tv_reqiest_bodypart.setText(data_service.getReqiest_bodypart());
        holder.tv_request_doctor.setText(data_service.getRequest_doctor());
        holder.tv_request_state.setText(data_service.getRequest_state());
        holder.tv_request_date.setText(data_service.getRequest_date());

if(data_service.getRequest_state().equals("دریافت پاسخ")){
    holder.tv_request_state.setTextColor(ContextCompat.getColor(context, R.color.correctItem));
}
        Typeface typeface3 = Typeface.createFromAsset(context.getAssets(), "font/iran_sans.ttf");
        holder.tv_request_state.setTypeface(typeface3, Typeface.BOLD);

        Glide.with(context).load(getImage(data_service.getRequest_img())).into(holder.iv_requet);
        holder.cv_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardClickListner.OnCardClicked(v, position);
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