package com.example.weather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ForecastDaysRecyclerAdapter extends RecyclerView.Adapter<ForecastDaysRecyclerAdapter.ViewHolder> {

    private List<OneDayWeather> data;
    private Context context;

    public ForecastDaysRecyclerAdapter(List<OneDayWeather> data,Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.coming_days_weather_recycler_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.dayNameView.setText(data.get(viewHolder.getAdapterPosition()).getDayName());
        viewHolder.dayMinTempView.setText(data.get(viewHolder.getAdapterPosition()).getMinTemp()+"");
        viewHolder.dayMaxTempView.setText(data.get(viewHolder.getAdapterPosition()).getMaxTemp()+"");

        StringBuilder sb = new StringBuilder();
                sb.append("http://openweathermap.org/img/w/")
                .append(data.get(viewHolder.getAdapterPosition()).getWeatherIcon())
                .append(".png");
        Log.e("iconU",sb.toString());
        Glide
                .with(context)
                .load(sb.toString())
                .into(viewHolder.weatherKindIconView)
                  ;


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView dayNameView;
        private ImageView weatherKindIconView;
        private TextView dayMinTempView;
        private TextView dayMaxTempView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dayNameView = itemView.findViewById(R.id.comingDaysName);
            weatherKindIconView = itemView.findViewById(R.id.comingDaysWeaterKindIcon);
            dayMinTempView = itemView.findViewById(R.id.comingDaysMinTempView);
            dayMaxTempView = itemView.findViewById(R.id.comingDaysMaxTempView);
        }
    }
}
