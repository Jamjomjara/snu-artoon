package com.baryberri.a9_1_listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    // This String array becomes the dataSource of the ListView.
    private final String[] listString = {"List1", "List2", "List3", "List4",
                                   "List5", "List6", "List7", "List8"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);

        // ListView is a subclass of AdapterView.
        // AdapterView gets an adapter of its dataSource, and prints it out.
        // Therefore I should make an adapter and register it to the listView to show the content.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listString);
        listView.setAdapter(adapter);

        // to use multiple selection, use the code below.
        //listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        // ListView can set OnItemClickListener.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), listString[position], Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
