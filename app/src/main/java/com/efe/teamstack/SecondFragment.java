package com.efe.teamstack;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecondFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private PlayerSetAdapter playerSetAdapter;

    View view;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_second, container, false);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        //generate all combinations of players for the given number per set
        Generation generation = new Generation();
        ArrayList<ArrayList<Player>> allSets = generation.GenerateSets();

        MainActivity.getPlayerSetList().clear();
        if (MainActivity.getPlayerList().size() != 0) {
            for (int i = 0; i < allSets.size(); i++) {
                MainActivity.addPlayerSet(new PlayerSet(allSets.get(i)));
            }
        }

        mRecyclerView = view.findViewById(R.id.set_recycler_view);

        //Use this setting to improve performance if you know that changes in
        //the content do not change the layout size of the RecyclerView
        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
        }

        //using a linear layout manager
        mLayoutManager = new LinearLayoutManager(view.getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);

        //print
        for (int i = 0; i < MainActivity.getPlayerSetList().size(); i++) {
            System.out.println("Player Set: " + i);
            for (int j = 0; j < MainActivity.getPlayerSetList().get(i).getPlayerList().size(); j++) {
                System.out.print(MainActivity.getPlayerSetList().get(i).getPlayerList().get(j).getName() + ", ");
            }
            System.out.println();
        }

        //initializing adapter
        playerSetAdapter = new PlayerSetAdapter(MainActivity.getPlayerSetList());

        System.out.println("sets:" + MainActivity.getPlayerSetList().size());

        //specifying an adapter to access data, create views and replace the content
        mRecyclerView.setAdapter(playerSetAdapter);
        playerSetAdapter.notifyDataSetChanged();

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(view.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(view.getContext(), "Player Set at " + position + " is clicked", Toast.LENGTH_SHORT).show();
            }
        }));
    }
}