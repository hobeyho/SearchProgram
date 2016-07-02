package com.nickjwpark.searchprogram;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editTextSearch;
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = editTextSearch.getText().toString();
                searchStackOverflow(search);
            }
        });

    }

    public void searchStackOverflow(String search) {
        search = search.replaceAll(" ", "+");
        String url = "http://stackoverflow.com/search?q=" + search;
        Activity fromActivity = MainActivity.this;
        Class toActivity = WebBrowserActivity.class;
        Intent intent = new Intent(fromActivity, toActivity);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
