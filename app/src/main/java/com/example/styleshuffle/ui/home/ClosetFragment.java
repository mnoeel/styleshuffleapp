package com.example.styleshuffle.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.styleshuffle.DataModel.BottomItem;
import com.example.styleshuffle.DataModel.BottomItemDAO;
import com.example.styleshuffle.DataModel.ShoeItem;
import com.example.styleshuffle.DataModel.ShoeItemDAO;
import com.example.styleshuffle.DataModel.TopItem;
import com.example.styleshuffle.DataModel.TopItemDAO;
import com.example.styleshuffle.DataModel.UserDatabase;
import com.example.styleshuffle.R;
import com.example.styleshuffle.Recycler.BottomUserRecycler;
import com.example.styleshuffle.Recycler.ShoeUserRecycler;
import com.example.styleshuffle.Recycler.TopUserRecycler;
import com.example.styleshuffle.SavedOutfitsActivity;

import java.util.List;

public class ClosetFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView topRecyclerView;
    private RecyclerView bottomRecyclerView;
    private RecyclerView shoeRecyclerView;
    private boolean isGridViewVisible = false;
    BottomItemDAO bottomItemDAO;
    TopItemDAO topItemDAO;
    ShoeItemDAO shoeItemDAO;
    private List<BottomItem> items;
    private List<TopItem> topItems;
    private List<ShoeItem> shoeItems;

    Button startOutfitsActivityBtn, buttonBottoms, buttonTops, buttonShoes, closetButton;
    ImageView closetDoor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_closet, container, false);

        //closet door and button
        closetDoor = view.findViewById(R.id.closetDoor);
        closetButton = view.findViewById(R.id.closetButton);

        closetButton.setOnClickListener(v -> {
            closetDoor.setVisibility(View.GONE);
            closetButton.setVisibility(View.GONE);
            startOutfitsActivityBtn.setVisibility(View.VISIBLE);
            buttonTops.setVisibility(View.VISIBLE);
            buttonBottoms.setVisibility(View.VISIBLE);
            buttonShoes.setVisibility(View.VISIBLE);
        });


        //tops
        buttonTops = view.findViewById(R.id.buttonTops);
        topRecyclerView = view.findViewById(R.id.topGridView);
        topItemDAO = UserDatabase.getDBInstance(requireContext()).topItemDAO();
        TopUserRecycler topUserRecycler = new TopUserRecycler(topItemDAO.getAllTopItems());
        topRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        topRecyclerView.setAdapter(topUserRecycler);

        //bottoms
        buttonBottoms = view.findViewById(R.id.buttonBottoms);
        bottomRecyclerView = view.findViewById(R.id.bottomGridView);
        bottomItemDAO = UserDatabase.getDBInstance(requireContext()).bottomItemDAO();
        BottomUserRecycler bottomUserRecycler = new BottomUserRecycler(requireContext(), bottomItemDAO.getAllBottomItems(), bottomItemDAO);
        bottomRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        bottomRecyclerView.setAdapter(bottomUserRecycler);

        //shoes
        buttonShoes = view.findViewById(R.id.buttonShoes);
        shoeRecyclerView = view.findViewById(R.id.shoeGridView);
        shoeItemDAO = UserDatabase.getDBInstance(requireContext()).shoeItemDAO();
        ShoeUserRecycler shoeUserRecycler = new ShoeUserRecycler(shoeItemDAO.getAllShoeItems());
        shoeRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        shoeRecyclerView.setAdapter(bottomUserRecycler);


        startOutfitsActivityBtn = view.findViewById(R.id.outfitsActivityBtn);

        buttonTops.setOnClickListener(v -> toggleVisibility(topRecyclerView));
        buttonBottoms.setOnClickListener(v -> toggleVisibility(bottomRecyclerView));
        buttonShoes.setOnClickListener(v -> toggleVisibility(shoeRecyclerView));


        startOutfitsActivityBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SavedOutfitsActivity.class);
            startActivity(intent);
        });
        return view;

    }

    private void toggleVisibility(RecyclerView recyclerView) {
        if (isGridViewVisible) {
            recyclerView.setVisibility(View.GONE);
            isGridViewVisible = false;
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            isGridViewVisible = true;
        }
    }
}