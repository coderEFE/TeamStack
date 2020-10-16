package com.efe.teamstack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlayerSetAdapter extends RecyclerView.Adapter<PlayerSetAdapter.ViewHolder> {

    private ArrayList<PlayerSet> playerSetList;
    View v;

    //Provide a reference to the views for each data item
    //Complex data items may need more than one view per item, and
    //you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //each data item is just a string in this case
        TextView player_names;
        LinearLayout player_set_items;
        public ViewHolder(View v) {
            super(v);
            player_names = v.findViewById(R.id.tv_player_names);
            player_set_items = v.findViewById(R.id.set_card_items);
        }
    }

    //Provide a suitable constructor
    public PlayerSetAdapter(ArrayList<PlayerSet> playerSetList) {
        this.playerSetList = playerSetList;
    }

    //Create new views (invoked by the layout manager)
    @Override
    public PlayerSetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Creating a new view
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_set_card, parent, false);

        //set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //Replace the contents of a view (invoked by the layout manager
    @Override
    public void onBindViewHolder(PlayerSetAdapter.ViewHolder holder, int position) {

        // - get element from arraylist at this position
        // - replace the contents of the view with that element

        PlayerSet playerSet = playerSetList.get(position);
        StringBuilder playerSetNames = new StringBuilder();

        holder.player_set_items.removeAllViews();
        for (int i = 0; i < playerSet.getPlayerList().size(); i++) {
            //System.out.println("pos: " + position + " name: " + playerSet.getPlayerList().get(i).getName());
            ImageView player_icon = new ImageView(v.getContext());
            player_icon.setImageResource(playerSet.getPlayerList().get(i).getPic());
            holder.player_set_items.addView(player_icon);
            if (playerSet.getPlayerList().size() > 1) {
                playerSetNames.append((i == playerSet.getPlayerList().size() - 1) ? ("and " + playerSet.getPlayerList().get(i).getName()) : (playerSet.getPlayerList().get(i).getName() + ", "));
            } else {
                playerSetNames.append(playerSet.getPlayerList().get(i).getName());
            }
        }
        holder.player_names.setText(String.valueOf(playerSetNames.toString()));
    }
    /*@Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        //holder.player_names.removeView();
        int numToDelete = holder.player_set_items.getChildCount();
        for (int i = 0; i < numToDelete; i++) {
            ImageView pic = (ImageView) holder.player_set_items.getChildAt(i);
            //System.out.println(i + ", ");
            ((ViewGroup) pic.getParent()).removeView(pic);
        }
    }*/

    @Override
    public int getItemCount() {
        return playerSetList.size();
    }
}