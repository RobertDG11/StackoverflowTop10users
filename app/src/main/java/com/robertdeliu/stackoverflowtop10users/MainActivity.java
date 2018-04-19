package com.robertdeliu.stackoverflowtop10users;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    UserAdapter userAdapter;
    ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataFromServer task = new getDataFromServer();
        task.execute("https://api.stackexchange.com/2.2/users?pagesize=10&order=desc" +
                "&sort=reputation&site=stackoverflow");

        listView = findViewById(R.id.listView);
        userAdapter = new UserAdapter(this, users, R.layout.relative_layout);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                startActivity(intent);
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    public class getDataFromServer extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder data = new StringBuilder();
            URL url;
            HttpURLConnection urlConnection;

            try {
                int read;

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                read = reader.read();
                while (read != -1) {
                    char current = (char)read;
                    data.append(current);
                    read = reader.read();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return data.toString();
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);

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

            listView.setAdapter(userAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
