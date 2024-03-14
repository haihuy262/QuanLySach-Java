package huynhph30022.fpoly.assignment.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

import huynhph30022.fpoly.assignment.R;
import huynhph30022.fpoly.assignment.adapter.AdapterLoaiSach;
import huynhph30022.fpoly.assignment.dao.LoaiSachDAO;
import huynhph30022.fpoly.assignment.model.LoaiSach;
import huynhph30022.fpoly.assignment.model.PhieuMuon;

public class QuanLyLoaiSachFragment extends Fragment {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    ArrayList<LoaiSach> list;
    LoaiSachDAO loaiSachDAO;
    AdapterLoaiSach adapterLoaiSach;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_loai_sach, container, false);

        fab = view.findViewById(R.id.fab_loai_sach);
        recyclerView = view.findViewById(R.id.recycler_loai_sach);
        list = new ArrayList<>();
        loaiSachDAO = new LoaiSachDAO(requireContext());
        adapterLoaiSach = new AdapterLoaiSach(requireContext());
        loadData();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                LayoutInflater inflaterFab = LayoutInflater.from(requireContext());
                View viewFab = inflaterFab.inflate(R.layout.dialog_quan_ly_loai_sach, null);

                builder.setView(viewFab);
                builder.setCancelable(false);
                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppCompatEditText edThemLoaiSach = viewFab.findViewById(R.id.edThemLoaiSach);
                        String themLoaiSach = Objects.requireNonNull(edThemLoaiSach.getText()).toString().trim();
                        if (loaiSachDAO.addLoaiSach(themLoaiSach)) {
                            Toast.makeText(requireContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                            loadData();
                        } else {
                            Toast.makeText(requireContext(), "Không thành công!", Toast.LENGTH_SHORT).show();
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
        return view;
    }

    private void loadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        list = loaiSachDAO.getDanhSachLoaiSach();
        adapterLoaiSach.setData(list);
        recyclerView.setAdapter(adapterLoaiSach);
    }
}