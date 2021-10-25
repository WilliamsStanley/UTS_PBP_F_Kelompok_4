package com.example.utf_pbp_f_4.books;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.utf_pbp_f_4.R;
import com.example.utf_pbp_f_4.databinding.FragmentTampilDataBukuBinding;

public class TampilDataBuku extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentTampilDataBukuBinding fragmentTampilDataBukuBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_tampil_data_buku, container, false);
        View view = fragmentTampilDataBukuBinding.getRoot();

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(DaftarBuku.getDataBuku(), this.getContext());
        fragmentTampilDataBukuBinding.rvBuku.setAdapter(recyclerViewAdapter);

        return view;
    }
}
