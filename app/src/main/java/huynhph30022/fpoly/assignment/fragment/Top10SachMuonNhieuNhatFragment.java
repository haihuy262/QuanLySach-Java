package huynhph30022.fpoly.assignment.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import huynhph30022.fpoly.assignment.R;
import huynhph30022.fpoly.assignment.adapter.AdapterTop10;
import huynhph30022.fpoly.assignment.dao.ThongKeDAO;
import huynhph30022.fpoly.assignment.model.Sach;

public class Top10SachMuonNhieuNhatFragment extends Fragment {
    RecyclerView recyclerView;
    ThongKeDAO thongKeDAO;
    ArrayList<Sach> list;
    AdapterTop10 adapterTop10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top10_sach_muon_nhieu_nhat, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewTop10);
        thongKeDAO = new ThongKeDAO(requireContext());
        list = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        adapterTop10 = new AdapterTop10(requireContext());

        list = thongKeDAO.getTop10MuonSach();
        adapterTop10.setData(list);
        recyclerView.setAdapter(adapterTop10);
        return view;
    }
}