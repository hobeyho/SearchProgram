package com.nickjwpark.searchprogram;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends ListActivity {

    EditText editTextSearch;
    Button btnSearch;
    ArrayList<String> search_list;
    SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        sharedPref = this.getSharedPreferences("com.nickjwpark.searchprogram", Context.MODE_PRIVATE);
        search_list = new ArrayList<String>();
        loadArrayList();
        populateListview();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = editTextSearch.getText().toString();
                search_list.add(search);
                saveArrayList();
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

    public void populateListview(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, search_list);
        setListAdapter(adapter);
    }

    public void saveArrayList(){
        String search_str = "";
        for(String search : search_list){
            search_str = search_str + search + "&&&";
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("search",search_str);
        editor.commit();
        populateListview();
    }

    public void loadArrayList(){
        String defaultValue = "";
        String todo = sharedPref.getString("search", defaultValue);
        String [] search_arr = todo.split("&&&");
        for(String search : search_arr){
            search_list.add(search);
        }
    }

    protected void onListItemClick(ListView l, View v, final int position, long id) {
        super.onListItemClick(l, v, position, id);
        String search = search_list.get(position);
        searchStackOverflow(search);
    }

}
