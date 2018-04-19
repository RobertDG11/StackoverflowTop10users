package com.robertdeliu.stackoverflowtop10users;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<User> {
    UserAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    private class ViewHolder {
        private TextView nameTextView;
        private ImageView userImageView;
        private ImageView positionInTop;
        private TextView reputation;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null || position < 3) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.relative_layout, parent, false);
            holder = new ViewHolder();
            holder.nameTextView = convertView.findViewById(R.id.name);
            holder.userImageView = convertView.findViewById(R.id.userPhoto);
            holder.positionInTop = convertView.findViewById(R.id.position);
            holder.reputation = convertView.findViewById(R.id.reputation);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        User user = getItem(position);

        Picasso.with(getContext()).load(user.getProfileImage()).into(holder.userImageView);

        assert user != null;
        holder.nameTextView.setText(user.getName());
        if (position >= 3) {
            holder.positionInTop.setVisibility(View.INVISIBLE);
        } else {
            holder.positionInTop.setImageResource(user.getPositionInTop());
        }
        holder.reputation.setText(user.getReputation() + " reputation");

        return convertView;
    }
}
