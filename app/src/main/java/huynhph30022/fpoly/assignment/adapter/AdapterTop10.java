package huynhph30022.fpoly.assignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import huynhph30022.fpoly.assignment.R;
import huynhph30022.fpoly.assignment.model.Sach;

public class AdapterTop10 extends RecyclerView.Adapter<AdapterTop10.ViewHolder> {
    private final Context context;
    private ArrayList<Sach> list;

    public AdapterTop10(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Sach> list) {
        this.list = list;
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items_recycler_top10, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sach sach = list.get(position);
        if (sach == null) {
            return;
        }
        holder.tvTenSach.setText(sach.getTenSach());
        holder.tvSoLuongMuon.setText(String.valueOf(sach.getSoLuongSachMuon()));
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSach, tvSoLuongMuon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSach = itemView.findViewById(R.id.tvTenSachTop10);
            tvSoLuongMuon = itemView.findViewById(R.id.tvSoLuongMuon);
        }
    }
}
