package com.efe.teamstack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    private ArrayList<Player> playerList;

    //Provide a reference to the views for each data item
    //Complex data items may need more than one view per item, and
    //you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //each data item is just a string in this case
        public TextView tvName, tvAge, tvPlayerID, tvRoundsPlayed;
        public ImageView ivPlayerPic;
        public LinearLayout cardItems;

        public ViewHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.tv_name);
            ivPlayerPic = (ImageView) v.findViewById(R.id.iv_player_pic);
            cardItems = v.findViewById(R.id.card_items);
        }
    }

    //Provide a suitable constructor
    public PlayerAdapter(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    //Create new views (invoked by the layout manager)
    @Override
    public PlayerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Creating a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_card, parent, false);

        //set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //Replace the contents of a view (invoked by the layout manager
    @Override
    public void onBindViewHolder(final PlayerAdapter.ViewHolder holder, final int position) {

        // - get element from arraylist at this position
        // - replace the contents of the view with that element

        final Player player = playerList.get(position);
        holder.tvName.setText(String.valueOf(player.getName()));
        holder.ivPlayerPic.setImageResource(player.getPic());

        //get option to delete player on long press
        holder.cardItems.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Snackbar.make(v, "Do you want to remove player \"" + player.getName() + "\"?", Snackbar.LENGTH_LONG)
                        .setAction("Delete", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //if player clicks on delete button
                                //System.out.println(MainActivity.getPrefs().getBoolean("notify", true));
                                if (MainActivity.notify) {
                                    //notify if preferences is true
                                    Toast.makeText(v.getContext(), "Player \"" + player.getName() + "\" was removed", Toast.LENGTH_SHORT).show();
                                }
                                playerList.remove(position);
                                MainActivity.updatePlayerList(playerList);
                                notifyItemRemoved(position);
                                notifyDataSetChanged();
                            }
                        }).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }
}