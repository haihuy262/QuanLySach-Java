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
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import huynhph30022.fpoly.assignment.R;
import huynhph30022.fpoly.assignment.adapter.AdapterQuanLySach;
import huynhph30022.fpoly.assignment.dao.LoaiSachDAO;
import huynhph30022.fpoly.assignment.dao.SachDAO;
import huynhph30022.fpoly.assignment.model.LoaiSach;
import huynhph30022.fpoly.assignment.model.Sach;

public class QuanLySachFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    ArrayList<Sach> list;
    SachDAO sachDAO;
    AdapterQuanLySach adapterQuanLySach;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_sach, container, false);
        recyclerView = view.findViewById(R.id.recycler_quan_ly_sach);
        fab = view.findViewById(R.id.fab_quan_ly_sach);
        list = new ArrayList<>();
        sachDAO = new SachDAO(requireContext());
        adapterQuanLySach = new AdapterQuanLySach(requireContext(), getDsLoaiSach(), sachDAO);
        loadData();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                LayoutInflater inflater1 = LayoutInflater.from(requireContext());
                View view1 = inflater1.inflate(R.layout.dialog_them_sach, null);
                builder.setView(view1);
                builder.setCancelable(false);
                AppCompatEditText edTenSach = view1.findViewById(R.id.edTenSach);
                AppCompatEditText edGiaThue = view1.findViewById(R.id.edGiaTien);
                Spinner spnLoaiSach = view1.findViewById(R.id.spnLoaiSach);
                SimpleAdapter simpleAdapter = new SimpleAdapter(requireContext(), getDsLoaiSach(), android.R.layout.simple_list_item_1, new String[]{"TenLoai"}, new int[]{android.R.id.text1});
                spnLoaiSach.setAdapter(simpleAdapter);
                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tenSach = Objects.requireNonNull(edTenSach.getText()).toString().trim();
                        int giaThue = Integer.parseInt(Objects.requireNonNull(edGiaThue.getText()).toString().trim());
                        HashMap<String, Object> hs = (HashMap<String, Object>) spnLoaiSach.getSelectedItem();
                        int maLoai = (int) hs.get("MaLoai");
                        boolean check = sachDAO.addSach(tenSach, giaThue, maLoai);
                        if (check) {
                            Toast.makeText(requireContext(), "Thêm sách thành công!", Toast.LENGTH_SHORT).show();
                            loadData();
                        } else {
                            Toast.makeText(requireContext(), "Thêm thất bại!", Toast.LENGTH_SHORT).show();
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
        list = sachDAO.getDSDauSach();
        adapterQuanLySach.setData(list);
        recyclerView.setAdapter(adapterQuanLySach);
    }

    private ArrayList<HashMap<String, Object>> getDsLoaiSach() {
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(requireContext());
        ArrayList<LoaiSach> list1 = loaiSachDAO.getDanhSachLoaiSach();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (LoaiSach loaiSach : list1) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("MaLoai", loaiSach.getId());
            hs.put("TenLoai", loaiSach.getTenLoaiSach());
            listHM.add(hs);
        }
        return listHM;
    }
}