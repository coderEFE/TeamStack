package com.efe.teamstack;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.math.MathUtils;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private PlayerAdapter playerAdapter;

    String[] names = {"Ethan", "Josiah", "Hudson", "Owen", "Zach", "Ben", "Dexter"};

    /*String[] singers = {"Mike Posner", "Lukas Graham", "Zayn", "Fifth Harmony", "Zara Larsson & MNEK", "The Chainsmokers",
            "Justin Bieber", "G-Eazy x Bebe Rexha", "DNCE", "Ariana Grande", "Flo Rida",
            "Twenty one Pilots", "Drake", "DJ Snake", "Meghan Trainer"};*/

    ArrayList<Integer> pics = new ArrayList<>();
    /*int[] pics = {
            R.drawable.ic_user2,
            R.drawable.ic_user3,
            R.drawable.ic_user4,
            R.drawable.ic_user5,
            R.drawable.ic_user6,
            R.drawable.ic_user7,
            R.drawable.ic_user8,
            R.drawable.ic_user9,
            R.drawable.ic_user10,
            R.drawable.ic_user11,
            R.drawable.ic_user12,
            R.drawable.ic_user13,
            R.drawable.ic_user14,
            R.drawable.ic_user15
    };*/

    View view;
    private PopupWindow window;
    Button addBtn;
    EditText playerName;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        //ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_first, container);
        //return inflater.inflate(R.layout.fragment_first, container, false);
        view = inflater.inflate(R.layout.fragment_first, container, false);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindow();
            }
        });

        pics.add(R.drawable.ic_user2);
        pics.add(R.drawable.ic_user3);
        pics.add(R.drawable.ic_user4);
        pics.add(R.drawable.ic_user5);
        pics.add(R.drawable.ic_user6);
        pics.add(R.drawable.ic_user7);
        pics.add(R.drawable.ic_user8);
        pics.add(R.drawable.ic_user9);
        pics.add(R.drawable.ic_user10);
        pics.add(R.drawable.ic_user11);
        pics.add(R.drawable.ic_user12);
        pics.add(R.drawable.ic_user13);
        pics.add(R.drawable.ic_user14);
        pics.add(R.drawable.ic_user15);


        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update num-per-set from preferences
                /*SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(getContext());
                System.out.println("num: " + sharedPreferences.getString("num_per_set", "4"));
                MainActivity.numPerSet = Integer.parseInt(sharedPreferences.getString("num_per_set", "4"));*/
                //MainActivity.numPerSet = MainActivity.getPrefs().getInt("num_per_set", 4);

                //navigate to second fragment if there are any players in player list
                if (MainActivity.getPlayerList().size() >= MainActivity.numPerSet) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_SecondFragment);
                } else {
                    Toast.makeText(view.getContext(), "You must have at least the number of players per set!", Toast.LENGTH_LONG).show();
                }
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        //Use this setting to improve performance if you know that changes in
        //the content do not change the layout size of the RecyclerView
        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
        }

        //using a linear layout manager
        mLayoutManager = new LinearLayoutManager(view.getContext());
        /*
        use this in case of gridlayoutmanager
        mLayoutManager = new GridLayoutManager(this,2);
         */
        /*
        use this in case of Staggered GridLayoutManager
         */
        //mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);


        mRecyclerView.setLayoutManager(mLayoutManager);

        //adding data from arrays to playerList
        /*if (MainActivity.getPlayerList().size() == 0) {
            for (int i = 0; i < names.length; i++) {
                addPlayer(names[i], i, 16);
            }
        }*/
        //initializing adapter
        playerAdapter = new PlayerAdapter(MainActivity.getPlayerList());

        //specifying an adapter to access data, create views and replace the content
        mRecyclerView.setAdapter(playerAdapter);
        playerAdapter.notifyDataSetChanged();

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(view.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(view.getContext(), "Player Card at " + position + " is clicked", Toast.LENGTH_SHORT).show();
            }
        }));
    }
    private void addPlayer (String name, int playerID, int age) {
        if (pics.size() > 0) {
            int picIndex = (int) (Math.random() * pics.size());
            Player player = new Player(name, playerID, age, pics.get(picIndex));
            MainActivity.addPlayer(player);
            pics.remove(picIndex);
            System.out.println("Player " + name + " added");
        } else {
            System.out.println("There are no more pictures to be used");
        }
    }
    private void showPopupWindow(){
        View layout = getLayoutInflater().inflate(R.layout.new_player_popup, null);
        window = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setOutsideTouchable(true);
        window.showAtLocation(layout, Gravity.CENTER, 0, 0);
        addBtn = layout.findViewById(R.id.addBtn);
        playerName = layout.findViewById(R.id.playerName);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!playerName.getText().toString().equals("")) {
                    addPlayer(playerName.getText().toString(), MainActivity.getPlayerList().size(), 16);
                    window.dismiss();
                } else {
                    Toast.makeText(view.getContext(), "Player must have a name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}