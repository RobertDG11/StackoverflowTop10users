package com.robertdeliu.stackoverflowtop10users;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    UserAdapter userAdapter;
    public ArrayList<User> users = new ArrayList<>();
    static File dataFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataFromServer task = new getDataFromServer();
        task.execute("https://api.stackexchange.com/2.2/users?pagesize=10&order=desc" +
                "&sort=reputation&site=stackoverflow");

        listView = findViewById(R.id.listView);
        userAdapter = new UserAdapter(this, users, R.layout.relative_layout,
                getResources().getBoolean(R.bool.isTablet));

        if (!getResources().getBoolean(R.bool.isTablet)) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                    intent.putExtra("name", users.get(i).getName());
                    intent.putExtra("user photo", users.get(i).getProfileImage());
                    intent.putExtra("reputation", users.get(i).getReputation());
                    intent.putExtra("location", users.get(i).getLocation());
                    intent.putExtra("badges", users.get(i).getBadges());
                    startActivity(intent);

                }
            });
        }
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
                dataFile = wrtieFileOnInternalStorage(getBaseContext(), "JSONData", data.toString());
            } catch (IOException e) {
                Log.i("No network", e.toString());
            }

            return data.toString();
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);

            if (isNetworkConnected(getBaseContext())) {
                JSONParser parser = new JSONParser(data, users);
                users = parser.getUsers();

            } else {
                if (dataFile == null) {
                    Toast.makeText(getBaseContext(), "Need internet connection!", Toast.LENGTH_LONG).show();
                }
                else {
                    try {
                        BufferedReader fis = new BufferedReader(new FileReader(dataFile));
                        String info = fis.readLine();

                        JSONParser parser = new JSONParser(info, users);
                        users = parser.getUsers();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            listView.setAdapter(userAdapter);
        }
    }

    public static boolean isNetworkConnected(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager)
                ctx.getSystemService (Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    public File wrtieFileOnInternalStorage(Context context,String fileName, String data){
        File file = new File(context.getFilesDir(), fileName);
        FileOutputStream fos;
        try{
            fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();

        }catch (Exception e){
            e.printStackTrace();

        }

        return file;
    }
}
