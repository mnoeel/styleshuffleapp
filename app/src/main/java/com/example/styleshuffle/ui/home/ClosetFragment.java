package com.example.styleshuffle.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.styleshuffle.DataModel.ClosetItem;
import com.example.styleshuffle.DataModel.ItemDAO;
import com.example.styleshuffle.DataModel.UserDatabase;
import com.example.styleshuffle.R;
import com.example.styleshuffle.UserRecycler;
import com.example.styleshuffle.UserViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ClosetFragment extends Fragment {

    private ToggleButton toggleButton;
    private boolean isDeleteMode = false;


    private Button buttonShirt;
    private RecyclerView recyclerView;
    private ImageAdapter adapter;
    private boolean isGridViewVisible = false;
    ItemDAO itemDAO;
    private List<ClosetItem> items;
    int position;
    int deleteCondition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_closet, container, false);
        buttonShirt = view.findViewById(R.id.buttonShirt);
        recyclerView = view.findViewById(R.id.horGridView);
        itemDAO = UserDatabase.getDBInstance(requireContext()).itemDAO();
        UserRecycler userRecycler = new UserRecycler(itemDAO.getAllClosetItems());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(userRecycler);
        buttonShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGridViewVisible) {
                    recyclerView.setVisibility(View.GONE);
                    isGridViewVisible = false;
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    isGridViewVisible = true;

                }
            }
        });

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Long click the ingredient you want to delete!", Toast.LENGTH_SHORT).show();
                deleteCondition = 1;
            }
        });
        return view;
    }
}