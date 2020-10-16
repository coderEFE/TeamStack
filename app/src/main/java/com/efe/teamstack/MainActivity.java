package com.efe.teamstack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static ArrayList<Player> playerList;
    private static ArrayList<PlayerSet> playerSetList;
    SharedPreferences sharedPref;
    public static int numPerSet;
    public static boolean randomize;
    public static boolean notify;
    //mutually exclusive sets
    public static boolean mut_exc;
    public static boolean show_leftover_players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        numPerSet = Integer.parseInt(sharedPref.getString("num_per_set", "4"));
        randomize = sharedPref.getBoolean("randomize", true);
        notify = sharedPref.getBoolean("notify", true);
        mut_exc = sharedPref.getBoolean("mutually_exclusive", false);
        show_leftover_players = sharedPref.getBoolean("leftover_players", false);
        //System.out.println(numPerSet + ", " + randomize + ", " + notify + ", " + mut_exc + ".");
        playerList = new ArrayList<>();
        playerSetList = new ArrayList<>();
    }
@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }
        if (id == R.id.action_info) {
            startActivity(new Intent(MainActivity.this, InfoActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public static ArrayList<Player> getPlayerList() {
        return playerList;
    }
    public static void updatePlayerList(ArrayList<Player> newList) {
        playerList = newList;
    }
    public static void addPlayer(Player newPlayer) {
        playerList.add(newPlayer);
    }
    public static ArrayList<PlayerSet> getPlayerSetList() {
        return playerSetList;
    }
    public static void addPlayerSet(PlayerSet newPlayerSet) {
        playerSetList.add(newPlayerSet);
    }
}