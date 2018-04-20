package com.robertdeliu.stackoverflowtop10users;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.squareup.picasso.Picasso;

public class UserInfoActivity extends AppCompatActivity {

    String name;
    String userPhoto;
    String reputation;
    String location;
    String[] badges;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_layout);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        userPhoto = intent.getStringExtra("user photo");
        reputation = intent.getStringExtra("reputation");
        location = intent.getStringExtra("location");
        badges = intent.getStringArrayExtra("badges");

        ViewHolder holder = new ViewHolder();

        holder.nameTextView = findViewById(R.id.name);
        holder.userImageView = findViewById(R.id.userPhoto);
        holder.location = findViewById(R.id.location);
        holder.reputation = findViewById(R.id.reputation);
        holder.goldBadges = findViewById(R.id.goldBadges);
        holder.silverBadges = findViewById(R.id.silverBadges);
        holder.bronzeBadges = findViewById(R.id.bronzeBadges);

        Picasso.get().load(userPhoto).into(holder.userImageView);

        holder.nameTextView.setText(String.format("Name: %s", name));
        holder.reputation.setText(String.format("%s reputation", reputation));
        holder.location.setText(String.format("Location: %s", location));
        holder.goldBadges.setText(String.format("Gold badges: %s", badges[2]));
        holder.silverBadges.setText(String.format("Silver badges: %s", badges[1]));
        holder.bronzeBadges.setText(String.format("Bronze badges: %s", badges[0]));

        Button back = findViewById(R.id.button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
