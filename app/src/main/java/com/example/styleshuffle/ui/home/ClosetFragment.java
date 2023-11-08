package com.example.styleshuffle.ui.home;

import android.content.Intent;
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

import com.example.styleshuffle.DataModel.BottomItem;
import com.example.styleshuffle.DataModel.BottomItemDAO;
import com.example.styleshuffle.DataModel.ShoeItemDAO;
import com.example.styleshuffle.DataModel.TopItemDAO;
import com.example.styleshuffle.DataModel.UserDatabase;
import com.example.styleshuffle.R;
import com.example.styleshuffle.Recycler.BottomUserRecycler;
import com.example.styleshuffle.SavedOutfitsActivity;

import java.util.List;

public class ClosetFragment extends Fragment {
    private RecyclerView bottomRecyclerView;
    private boolean isGridViewVisible = false;
    BottomItemDAO bottomItemDAO;
    TopItemDAO topItemDAO;
    ShoeItemDAO shoeItemDAO;
    private List<BottomItem> items;
    Button startOutfitsActivityBtn, buttonBottoms;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_closet, container, false);
        buttonBottoms = view.findViewById(R.id.buttonBottoms);
        bottomRecyclerView = view.findViewById(R.id.horGridView);
        bottomItemDAO = UserDatabase.getDBInstance(requireContext()).bottomItemDAO();
        BottomUserRecycler bottomUserRecycler = new BottomUserRecycler(bottomItemDAO.getAllBottomItems());
        bottomRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        bottomRecyclerView.setAdapter(bottomUserRecycler);

        startOutfitsActivityBtn = view.findViewById(R.id.outfitsActivityBtn);

        buttonBottoms.setOnClickListener(v -> {
            if (isGridViewVisible) {
                bottomRecyclerView.setVisibility(View.GONE);
                isGridViewVisible = false;
            } else {
                bottomRecyclerView.setVisibility(View.VISIBLE);
                isGridViewVisible = true;

            }
        });

        startOutfitsActivityBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SavedOutfitsActivity.class);
            startActivity(intent);
        });
        return view;

    }
}