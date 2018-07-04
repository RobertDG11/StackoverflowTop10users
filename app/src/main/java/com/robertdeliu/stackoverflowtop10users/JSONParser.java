package com.robertdeliu.stackoverflowtop10users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParser {
    ArrayList<User> users;
    String data;

    JSONParser(String data, ArrayList<User> users) {
        this.users = users;
        this.data = data;
    }

    private void parseData() {
        try {
            JSONObject jsonObject = new JSONObject(data);
            String usersString = jsonObject.getString("items");
            JSONArray userInfo = new JSONArray(usersString);
            int medal = -1;

            for (int i = 0; i < userInfo.length(); i++) {
                JSONObject item = userInfo.getJSONObject(i);
                String badgesList = item.getString("badge_counts");
                JSONObject badges = new JSONObject(badgesList);
                String[] bdgs = {badges.getString("bronze"),
                        badges.getString("silver"),
                        badges.getString("gold")};

                if (i == 0) {
                    medal = R.drawable.gold;
                } else if (i == 1) {
                    medal = R.drawable.silver;
                } else if (i == 2) {
                    medal = R.drawable.bronze;
                }

                users.add(new User.Builder()
                        .withProfileImage(item.getString("profile_image"))
                        .withName(item.getString("display_name"))
                        .withPositionInTop(medal)
                        .withReputation(item.getString("reputation"))
                        .withLocation(item.getString("location"))
                        .withBadges(bdgs)
                        .build());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getUsers() {
        parseData();
        return users;
    }
}
