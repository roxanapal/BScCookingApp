package com.easy.cooking.learneat.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.easy.cooking.learneat.AdviceActivity;
import com.easy.cooking.learneat.R;
import com.easy.cooking.learneat.firebase.FirebaseController;
import com.easy.cooking.learneat.models.Advice;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdviceAdapter extends RecyclerView.Adapter<AdviceAdapter.AdviceViewHolder>{

    private List<Advice> adviceList;
    private Context context;
    private Advice advice;

    public AdviceAdapter(List<Advice> adviceList, Context context) {
        this.adviceList = adviceList;
        this.context = context;
    }


    @NonNull
    @Override
    public AdviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.item_advice, parent, false);
        return new AdviceViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdviceViewHolder holder, int position) {
        advice = adviceList.get(position);
        holder.tvNameChef.setText(advice.getNameChef());
        holder.tvAdviceDescription.setText(advice.getDescriptionAdvice());
        Picasso.get()
                .load(advice.getUrlImageChef())
                .into(holder.ivAdviceChef, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.adviceProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

        if(advice.isFavoriteAdvice()){
            holder.ivAdviceStar.setImageDrawable(ActivityCompat.getDrawable(context, R.drawable.ic_star_yellow));
        } else {
            holder.ivAdviceStar.setImageDrawable(ActivityCompat.getDrawable(context, R.drawable.ic_star));
        }
    }

    @Override
    public int getItemCount() {
        return adviceList.size();
    }

    public class AdviceViewHolder extends RecyclerView.ViewHolder {

        private boolean isFavorite = false;

        @BindView(R.id.advice_tv_name_chef)
        TextView tvNameChef;

        @BindView(R.id.advice_tv_description)
        TextView tvAdviceDescription;

        @BindView(R.id.advice_iv_chef)
        ImageView ivAdviceChef;

        @BindView(R.id.advice_iv_star)
        ImageView ivAdviceStar;

        @BindView(R.id.item_advice_progressbar)
        ProgressBar adviceProgressBar;

        public AdviceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.advice_iv_star)
        public void addAdviceToFavorites(ImageView imageView) {
            imageView.setImageDrawable(ActivityCompat.getDrawable(context,
                    isFavorite
                            ? R.drawable.ic_star
                            : R.drawable.ic_star_yellow));
            isFavorite = !isFavorite;
            //FirebaseController.getInstance().updateFavoriteAdvice(advice);
        }
    }
}
