package huynhph30022.fpoly.assignment.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import huynhph30022.fpoly.assignment.R;
import huynhph30022.fpoly.assignment.dao.ThanhVienDAO;
import huynhph30022.fpoly.assignment.model.ThanhVien;

public class AdapterThanhVien extends RecyclerView.Adapter<AdapterThanhVien.ViewHolder> {
    private final Context context;
    private ArrayList<ThanhVien> list;
    private ThanhVienDAO thanhVienDAO;

    public AdapterThanhVien(Context context) {
        this.context = context;
        thanhVienDAO = new ThanhVienDAO(context);
    }

    public void setData(ArrayList<ThanhVien> list) {
        this.list = list;
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items_recycler_thanh_vien, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThanhVien thanhVien = list.get(position);
        if (thanhVien == null) {
            return;
        }
        holder.tvHoTen.setText(thanhVien.getHoTenThanhVien());
        holder.tvNgaySinh.setText(thanhVien.getNamSinhThanhVien());
        holder.imgEditTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.dialog_sua_thanh_vien, null);
                builder.setView(view);

                AppCompatEditText edHoTenTV = view.findViewById(R.id.edHoTenTVEdit);
                AppCompatEditText edNamSinhTV = view.findViewById(R.id.edNamSinhTVEdit);

                edHoTenTV.setText(list.get(holder.getAdapterPosition()).getHoTenThanhVien());
                edNamSinhTV.setText(list.get(holder.getAdapterPosition()).getNamSinhThanhVien());

                builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String hoTenTV = Objects.requireNonNull(edHoTenTV.getText()).toString().trim();
                        String namSinhTV = Objects.requireNonNull(edNamSinhTV.getText()).toString().trim();
                        boolean check = thanhVienDAO.updateThanhVien(list.get(holder.getAdapterPosition()).getMaThanhVien(), hoTenTV, namSinhTV);
                        if (check) {
                            Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list = thanhVienDAO.getDanhSachThanhVien();
                            notifyItemChanged(holder.getAdapterPosition());
                        } else {
                            Toast.makeText(context, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        holder.imgDeleteTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = thanhVienDAO.deleteThanhVien(list.get(holder.getAdapterPosition()).getMaThanhVien());
                switch (check) {
                    case 1:
                        Toast.makeText(context, "Xoá thành công!", Toast.LENGTH_SHORT).show();
                        list.clear();
                        list = thanhVienDAO.getDanhSachThanhVien();
                        notifyItemRemoved(holder.getAdapterPosition());
                        break;
                    case 0:
                        Toast.makeText(context, "Xoá thất bại!", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(context, "Thành viên có phiếu mượn, không được xoá", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
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
        TextView tvHoTen, tvNgaySinh;
        ImageView imgDeleteTV, imgEditTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHoTen = itemView.findViewById(R.id.tvHoTen);
            tvNgaySinh = itemView.findViewById(R.id.tvNgaySinh);
            imgDeleteTV = itemView.findViewById(R.id.imgDeleteTV);
            imgEditTV = itemView.findViewById(R.id.imgEditTV);
        }
    }
}
