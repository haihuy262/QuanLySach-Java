package huynhph30022.fpoly.assignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import huynhph30022.fpoly.assignment.R;
import huynhph30022.fpoly.assignment.dao.PhieuMuonDAO;
import huynhph30022.fpoly.assignment.model.PhieuMuon;

public class AdapterPhieuMuon extends RecyclerView.Adapter<AdapterPhieuMuon.ViewHolder> {
    private final Context context;
    private ArrayList<PhieuMuon> list;

    public AdapterPhieuMuon(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<PhieuMuon> list) {
        this.list = list;
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items_recycler_phieu_muon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhieuMuon phieuMuon = list.get(position);
        if (phieuMuon == null) {
            return;
        }
        holder.tvTenThanhVien.setText(phieuMuon.getTenThanhVien());
        holder.tvTenThuThu.setText(phieuMuon.getTenThuThu());
        holder.tvTenSach.setText(phieuMuon.getTenSach());
        holder.tvNgay.setText(phieuMuon.getNgay());
        holder.tvTien.setText(String.valueOf(phieuMuon.getTienThue()));
        String trangThai = "";
        if (phieuMuon.getTraSach() == 1) {
            trangThai = "Đã trả sách";
            holder.btnTraSach.setVisibility(View.GONE);
        } else {
            trangThai = "Chưa trả sách";
            holder.btnTraSach.setVisibility(View.VISIBLE);
        }
        holder.tvTenTrangThai.setText(trangThai);

        holder.btnTraSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                boolean check = phieuMuonDAO.thayDoiTrangThai(list.get(holder.getAdapterPosition()).getMaPhieuMuon());
                if (check) {
                    list.clear();
                    list = phieuMuonDAO.getDanhSachPhieuMuon();
                    notifyItemChanged(holder.getAdapterPosition());
                    Toast.makeText(context, "Thay đổi trạng thái thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Thay đổi trạng thái không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenThanhVien, tvTenThuThu, tvTenSach, tvNgay, tvTenTrangThai, tvTien;
        AppCompatButton btnTraSach;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenThanhVien = itemView.findViewById(R.id.tvTenThanhVien);
            tvTenThuThu = itemView.findViewById(R.id.tvTenThuThu);
            tvTenSach = itemView.findViewById(R.id.tvTenSach);
            tvNgay = itemView.findViewById(R.id.tvNgay);
            tvTenTrangThai = itemView.findViewById(R.id.tvTenTrangThai);
            tvTien = itemView.findViewById(R.id.tvTien);
            btnTraSach = itemView.findViewById(R.id.btnTraSach);
        }
    }
}
