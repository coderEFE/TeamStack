package com.efe.teamstack;

import java.util.ArrayList;
import java.util.Collections;

class Generation {
    int totalNumPlayers = MainActivity.getPlayerList().size();
    int numPerSet = MainActivity.numPerSet;
    //int totalCombinations = fact(totalNumPlayers) / (fact(totalNumPlayers - numPerSet) * fact(numPerSet));
    //Player[] players = (Player[]) MainActivity.getPlayerList().toArray();
    ArrayList<Player> players = MainActivity.getPlayerList();
    ArrayList<Player> playerSet = new ArrayList<>(numPerSet);
    ArrayList<ArrayList<Player>> allSets = new ArrayList<>();

    //used to keep track of players used if using mut_exc setting
    ArrayList<Player> playersLeft = (ArrayList) players.clone();

    public ArrayList<ArrayList<Player>> GenerateSets () {
        //System.out.println(totalCombinations);
        //log stuff
        System.out.println("total players: " + totalNumPlayers + " numPerSet: " + numPerSet);
        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).getName());
        }

        //check if generation settings is mutually exclusive or not
        if (MainActivity.mut_exc) {
            //loop through number of players per set and add players until all are used
            while (playersLeft.size() - numPerSet >= 0) {
                for (int i = 0; i < numPerSet; i++) {
                    //get random player and add it to array
                    int randPlayerIndex = (int)(Math.random()*playersLeft.size());
                    playerSet.add(playersLeft.get(randPlayerIndex));
                    playersLeft.remove(randPlayerIndex);
                }
                addSet();
                playerSet.clear();
            }
            //if show leftover sets setting is on
            if (MainActivity.show_leftover_players && playersLeft.size() > 0) {
                for (int i = 0; i < playersLeft.size(); i++) {
                    //get random player and add it to array
                    int randPlayerIndex = (int)(Math.random()*playersLeft.size());
                    playerSet.add(playersLeft.get(randPlayerIndex));
                    playersLeft.remove(randPlayerIndex);
                }
                addSet();
            }
        } else {
            //first set is the first numPerSet of players
            for (int i = 0; i < numPerSet; i++) {
                playerSet.add(players.get(i));
                players.get(i).setRoundsPlayed(players.get(i).getRoundsPlayed() + 1);
            }
            addSet();
            //TODO: get this not to crash when inputting num of players per set as 1
            while (whereToStartIncrement() != -1) {
                incrementLastIndex();
                orderNumericallyUp(whereToStartIncrement(), getPlayerIndex(playerSet.get(whereToStartIncrement())) + 1);
            }
        }
        //swaps every other set to make it even
        /*for (int i = 0; i < Math.floor(totalCombinations / 2); i+=2) {
            Collections.swap(allSets, i, totalCombinations - i - 1);
        }*/
        //randomize from preferences
        if (MainActivity.randomize) {
            Collections.shuffle(allSets);
        }
        return allSets;
    }

    void addSet() {
        ArrayList tempSet;
        tempSet = (ArrayList) playerSet.clone();
        allSets.add(tempSet);
    }

    void incrementLastIndex () {
        //replace the last index in the set with increasing player nums
        int changeIndex = playerSet.size() - 1;
        for (int i = getPlayerIndex(playerSet.get(changeIndex)) + 1; i < totalNumPlayers; i++) {
            playerSet.set(changeIndex, players.get(i));
            players.get(i).setRoundsPlayed(players.get(i).getRoundsPlayed() + 1);
            addSet();
        }
    }

    //orders numerically from smallest to greatest starting a specific index and number
    void orderNumericallyUp (int startIndex, int startNum) {
        //int startIndex = _startIndex;
        //int startNum = _startNum;

        if (startIndex < playerSet.size()) { //-1
            playerSet.set(startIndex, players.get(startNum));
            players.get(startIndex).setRoundsPlayed(players.get(startIndex).getRoundsPlayed() + 1);
            orderNumericallyUp(startIndex + 1, startNum + 1);
        } else {
            addSet();
            return;
        }
    }

    //this function looks down the playerSet and finds an index that can be increased to make a new combination
    int whereToStartIncrement () {
        int index = -1;
        for (int i = 0; i < playerSet.size(); i++) {
            if (getPlayerIndex(playerSet.get((numPerSet - 1) - i)) != (totalNumPlayers - 1) - i) {
                index = (numPerSet - 1) - i;
                return index;
            }
        }
        return index;
    }

    /*public int getTotalCombinations () {
        return totalCombinations;
    }*/

    int getPlayerIndex (Player player) {
        return players.indexOf(player);
    }

    static final int fact(int num) {
        return num == 1? 1 : fact(num - 1)*num;
    }
}
