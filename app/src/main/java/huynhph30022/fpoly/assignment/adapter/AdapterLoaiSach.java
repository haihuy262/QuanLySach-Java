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
import huynhph30022.fpoly.assignment.dao.LoaiSachDAO;
import huynhph30022.fpoly.assignment.model.LoaiSach;

public class AdapterLoaiSach extends RecyclerView.Adapter<AdapterLoaiSach.ViewHolder> {
    private final Context context;
    private ArrayList<LoaiSach> list;
    private LoaiSachDAO loaiSachDAO;

    public AdapterLoaiSach(Context context) {
        this.context = context;
        loaiSachDAO = new LoaiSachDAO(context);
    }

    public void setData(ArrayList<LoaiSach> list) {
        this.list = list;
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items_recycler_loai_sach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiSach loaiSach = list.get(position);
        if (loaiSach == null) {
            return;
        }
        holder.tvTenLoaiSach.setText(loaiSach.getTenLoaiSach());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
                int check = loaiSachDAO.deleteLoaiSach(list.get(holder.getAdapterPosition()).getId());
                switch (check) {
                    case 1:
                        list.clear();
                        list = loaiSachDAO.getDanhSachLoaiSach();
                        notifyItemRemoved(holder.getAdapterPosition());
                        break;
                    case -1:
                        Toast.makeText(context, "Có sách trong thể loại này, không thể xoá", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(context, "Xoá thất bại", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.dialog_sua_loai_sach, null);
                builder.setView(view);
                builder.setCancelable(false);

                AppCompatEditText edSuaLoaiSach = view.findViewById(R.id.edSuaLoaiSach);
                edSuaLoaiSach.setText(list.get(holder.getAdapterPosition()).getTenLoaiSach());
                builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoaiSach editLoaiSach = list.get(holder.getAdapterPosition());
                        String suaLoaiSach = Objects.requireNonNull(edSuaLoaiSach.getText()).toString().trim();
                        editLoaiSach.setTenLoaiSach(suaLoaiSach);
                        boolean check = loaiSachDAO.editLoaiSach(editLoaiSach);
                        if (check) {
                            Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                            notifyItemChanged(holder.getAdapterPosition());
                        } else {
                            Toast.makeText(context, "Sửa thất bại!", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenLoaiSach;
        ImageView imgDelete, imgEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenLoaiSach = itemView.findViewById(R.id.tvTenLoaiSach);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgEdit = itemView.findViewById(R.id.imgEdit);
        }
    }
}
