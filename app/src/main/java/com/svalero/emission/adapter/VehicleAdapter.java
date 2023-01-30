package com.svalero.emission.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.emission.R;
import com.svalero.emission.VehicleDetailsActivity;
import com.svalero.emission.domain.Vehicle;

import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleHolder>{

    private Context context;
    private List<Vehicle> vehicleList;

    public VehicleAdapter(Context context, List<Vehicle> vehicleList) {
        this.context = context;
        this.vehicleList = vehicleList;
    }

    @NonNull
    @Override
    public VehicleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vehicle_item, parent, false);
        return new VehicleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleHolder holder, int position) {
        holder.tv_licensePlate.setText(vehicleList.get(position).getLicensePlate());
        holder.tv_brandVehicle.setText(vehicleList.get(position).getBrand());
        holder.tv_modelVehicle.setText(vehicleList.get(position).getModel());
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public class VehicleHolder extends RecyclerView.ViewHolder {

        TextView tv_licensePlate;
        TextView tv_brandVehicle;
        TextView tv_modelVehicle;
        Button bt_viewVehicle;
        View parentView;

        public VehicleHolder(@NonNull View view) {
            super(view);
            parentView = view;

            tv_licensePlate = itemView.findViewById(R.id.tv_vehicleLicensePlate);
            tv_brandVehicle = itemView.findViewById(R.id.tv_vehicleBrand);
            tv_modelVehicle = itemView.findViewById(R.id.tv_vehicleModel);
            bt_viewVehicle = itemView.findViewById(R.id.bt_viewVehicle);

            bt_viewVehicle.setOnClickListener(v -> viewVehicle(getAdapterPosition()));
        }

        private void viewVehicle(int adapterPosition) {

            Vehicle vehicle = vehicleList.get(adapterPosition);

            Intent intent = new Intent(context, VehicleDetailsActivity.class);
            intent.putExtra("licensePlate", vehicle.getLicensePlate());
            context.startActivity(intent);
        }

    }

}
