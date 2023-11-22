package com.example.styleshuffle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.styleshuffle.DataModel.Outfit;
import com.example.styleshuffle.DataModel.OutfitDAO;
import com.example.styleshuffle.DataModel.UserDatabase;
import com.example.styleshuffle.Recycler.OutfitAdapter;

import java.util.List;

public class SavedOutfitsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    OutfitDAO outfitDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_outfits);
        outfitDao = UserDatabase.getDBInstance(this).outfitDAO();
        List<Outfit> outfits = outfitDao.getAllOutfits();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        OutfitAdapter adapter = new OutfitAdapter(this,outfits,outfitDao);
        recyclerView.setAdapter(adapter);
    }
}