package com.efe.teamstack;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//Player object
public class PlayerSet {

    private ArrayList<Player> playerList;

    public PlayerSet(ArrayList<Player> playerList){

        this.playerList = playerList;

    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }
}
