package com.example.styleshuffle.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.styleshuffle.DataModel.ItemDAO;
import com.example.styleshuffle.DataModel.UserDatabase;
import com.example.styleshuffle.R;
import com.example.styleshuffle.UserRecycler;

import java.util.ArrayList;
import java.util.List;

public class ClosetFragment extends Fragment {

    private Button buttonShirt;
    private RecyclerView recyclerView;
    private ImageAdapter adapter;
    private boolean isGridViewVisible = false;
    ItemDAO itemDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_closet, container, false);

        buttonShirt = view.findViewById(R.id.buttonShirt);
        recyclerView = view.findViewById(R.id.horGridView);


        /*List<Integer> imageResources = new ArrayList<>();
        imageResources.add(R.drawable.shirt1);
        imageResources.add(R.drawable.shirt2);
        imageResources.add(R.drawable.shirt3);
        imageResources.add(R.drawable.shirt4);
        imageResources.add(R.drawable.shirt5);
        imageResources.add(R.drawable.shirt6); */
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
        return view;


    }
}