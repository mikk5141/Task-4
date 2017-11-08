package com.example.mongia.task4private;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Gson gson = new Gson();

    String searchText;
    private TextView tv;
    private String url = "https://api.github.com/search/repositories?q=";



    public MainActivity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchText = query;
                url = url + "[" + searchText + "]";
                try {
                    // The Respond method
                    String getResponse = doGetRequest(url);
                    JsonObject json = new Gson().fromJson(getResponse, JsonObject.class);
                    // parse the the json object to Recyler veiw
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tv.setText(url);
                url = "https://api.github.com/search/repositories?q=";
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /********* OkHttpClient ***********************/

    //avoid creating serveral instances, should be singleton.
    OkHttpClient client = new OkHttpClient();

    // code Request
    String doGetRequest(String url) throws IOException{
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }



    /********************************************/
}
