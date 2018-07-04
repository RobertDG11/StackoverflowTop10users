package com.robertdeliu.stackoverflowtop10users;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<User> {
    int layout;
    private boolean isTablet;
    UserAdapter(Context context, ArrayList<User> users, int layout, boolean isTablet) {
        super(context, 0, users);
        this.layout = layout;
        this.isTablet = isTablet;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;
        User user = getItem(position);

        if (convertView == null || position < 3) {
            convertView = LayoutInflater.from(getContext()).inflate(layout, parent, false);
            holder = new ViewHolder();
            holder.nameTextView = convertView.findViewById(R.id.name);
            holder.userImageView = convertView.findViewById(R.id.userPhoto);
            holder.positionInTop = convertView.findViewById(R.id.position);
            holder.reputation = convertView.findViewById(R.id.reputation);
            if (isTablet) {
                holder.location = convertView.findViewById(R.id.location);
                holder.goldBadges = convertView.findViewById(R.id.goldBadges);
                holder.silverBadges = convertView.findViewById(R.id.silverBadges);
                holder.bronzeBadges = convertView.findViewById(R.id.bronzeBadges);
            }
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        assert user != null;
        Picasso.get().load(user.getProfileImage()).into(holder.userImageView);

        holder.nameTextView.setText(user.getName());
        if (position >= 3) {
            holder.positionInTop.setVisibility(View.INVISIBLE);
        } else {
            holder.positionInTop.setImageResource(user.getPositionInTop());
        }
        holder.reputation.setText(String.format("%s reputation", user.getReputation()));
        if (isTablet) {
            holder.location.setText(String.format("Location: %s", user.getLocation()));
            holder.goldBadges.setText(String.format("Gold badges: %s", user.getBadges()[2]));
            holder.silverBadges.setText(String.format("Silver badges: %s", user.getBadges()[1]));
            holder.bronzeBadges.setText(String.format("Bronze badges: %s", user.getBadges()[0]));
        }

        return convertView;
    }
}
