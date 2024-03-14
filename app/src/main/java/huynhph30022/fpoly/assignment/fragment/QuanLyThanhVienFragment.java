package huynhph30022.fpoly.assignment.fragment;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import huynhph30022.fpoly.assignment.R;
import huynhph30022.fpoly.assignment.adapter.AdapterThanhVien;
import huynhph30022.fpoly.assignment.dao.ThanhVienDAO;
import huynhph30022.fpoly.assignment.model.ThanhVien;

public class QuanLyThanhVienFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    ThanhVienDAO thanhVienDAO;
    ArrayList<ThanhVien> list;
    AdapterThanhVien adapterThanhVien;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_thanh_vien, container, false);

        recyclerView = view.findViewById(R.id.recycler_quan_ly_thanh_vien);
        fab = view.findViewById(R.id.fab_quan_ly_thanh_vien);
        thanhVienDAO = new ThanhVienDAO(requireContext());
        list = new ArrayList<>();
        adapterThanhVien = new AdapterThanhVien(requireContext());
        loadData();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                LayoutInflater inflater1 = LayoutInflater.from(requireContext());
                View view1 = inflater1.inflate(R.layout.dialog_them_thanh_vien, null);
                builder.setView(view1);
                builder.setCancelable(false);
                AppCompatEditText edHoTen = view1.findViewById(R.id.edHoTenTV);
                AppCompatEditText edNamSinh = view1.findViewById(R.id.edNamSinhTV);
                edNamSinh.setFocusable(false);
                edNamSinh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyy", Locale.getDefault());
                                edNamSinh.setText(dateFormat.format(calendar.getTime()));
                            }
                        }, year, month, day);
                        datePickerDialog.show();
                    }
                });
                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String hoTen = Objects.requireNonNull(edHoTen.getText()).toString().trim();
                        String namSinh = Objects.requireNonNull(edNamSinh.getText()).toString().trim();
                        boolean check = thanhVienDAO.addThanhVien(hoTen, namSinh);
                        if (check) {
                            Toast.makeText(requireContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
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
        list = thanhVienDAO.getDanhSachThanhVien();
        adapterThanhVien.setData(list);
        recyclerView.setAdapter(adapterThanhVien);
    }
}