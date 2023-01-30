package com.svalero.emission.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.svalero.emission.ChargingPointDetailsActivity;
import com.svalero.emission.R;
import com.svalero.emission.db.AppDatabase;
import com.svalero.emission.domain.ChargingPoint;

import java.util.List;

public class ChargingPointAdapter extends RecyclerView.Adapter<ChargingPointAdapter.ChargingPointHolder>{

    private Context context;
    private List<ChargingPoint> chargingPointList;

    public ChargingPointAdapter(Context context, List<ChargingPoint> dataList) {
        this.context = context;
        this.chargingPointList = dataList;
    }

    @Override
    public ChargingPointHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.charging_point_item, parent, false);
        return new ChargingPointHolder(view);
    }

    @Override
    public void onBindViewHolder(ChargingPointHolder holder, int position) {
        holder.tv_chargingCode.setText(chargingPointList.get(position).getCode());
        holder.cb_chargingOccupied.setChecked(chargingPointList.get(position).isOccupied());
    }

    @Override
    public int getItemCount() {
        return chargingPointList.size();
    }

    public class ChargingPointHolder extends RecyclerView.ViewHolder {

        public TextView tv_chargingCode;
        public CheckBox cb_chargingOccupied;
        public Button bt_occupyCharging;
        public Button bt_viewCharging;
        public View parentView;

        public ChargingPointHolder(View itemView) {
            super(itemView);
            parentView = itemView;

            tv_chargingCode = itemView.findViewById(R.id.tv_viewChargingCode);
            cb_chargingOccupied = itemView.findViewById(R.id.cb_viewChargingOccupy);
            bt_occupyCharging = itemView.findViewById(R.id.bt_occupyChargingPoint);
            bt_viewCharging = itemView.findViewById(R.id.bt_viewChargingPoint);

            bt_occupyCharging.setOnClickListener(v -> occupyChargingPoint(getAdapterPosition()));
            bt_viewCharging.setOnClickListener(v -> viewChargingPoint(getAdapterPosition()));
        }
    }

    public void occupyChargingPoint(int position) {
        ChargingPoint chargingPoint = chargingPointList.get(position);

        if(chargingPoint.isOccupied() == false) {
            chargingPoint.setOccupied(true);
        }
        else {
            chargingPoint.setOccupied(false);
        }

        final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "charging_points")
                .allowMainThreadQueries().build();
        db.chargingPointDao().update(chargingPoint);

        notifyItemChanged(position);
    }

    private void viewChargingPoint(int adapterPosition) {

        ChargingPoint chargingPoint = chargingPointList.get(adapterPosition);

        Intent intent = new Intent(context, ChargingPointDetailsActivity.class);
        intent.putExtra("code", chargingPoint.getCode());
        context.startActivity(intent);
    }
}


