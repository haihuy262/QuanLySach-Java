package huynhph30022.fpoly.assignment.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import huynhph30022.fpoly.assignment.R;
import huynhph30022.fpoly.assignment.activity.LoginActivity;
import huynhph30022.fpoly.assignment.dao.ThuThuDAO;

public class DoiMatKhauFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
        EditText edOldPass = view.findViewById(R.id.edMatKhauCu);
        EditText edNewPass = view.findViewById(R.id.edMatKhauMoi);
        EditText edReNewPass = view.findViewById(R.id.edNhapLaiMatKhau);
        AppCompatButton btnDoiMatKhau = view.findViewById(R.id.btnDoiMatKhau);
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = edOldPass.getText().toString().trim();
                String newPass = edNewPass.getText().toString().trim();
                String reNewPass = edReNewPass.getText().toString().trim();

                if (newPass.equals(reNewPass)) {
                    SharedPreferences sharedPreferences = requireContext().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
                    String maThuThu = sharedPreferences.getString("MaTT", null);
                    ThuThuDAO thuThuDAO = new ThuThuDAO(requireContext());
                    boolean check = thuThuDAO.doiMatKhau(maThuThu, oldPass, newPass);
                    if (check) {
                        Toast.makeText(requireContext(), "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(requireActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(requireContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}