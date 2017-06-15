package com.baryberri.a9_2_dynamiclistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText newFieldEditText;
    Button addButton;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newFieldEditText = (EditText)findViewById(R.id.newFieldEditText);
        addButton = (Button)findViewById(R.id.addNew);
        listView = (ListView)findViewById(R.id.listView);

        // To make an adapter, the list should be declared as final.
        // Therefore, to make it dynamically allocated, we should use ArrayList, not an Array.
        final ArrayList<String> list = new ArrayList<>();
        list.add("You can add something");
        list.add("To the list.");
        list.add("Long press to remove a field.");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                list);
        listView.setAdapter(adapter);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newFieldString = newFieldEditText.getText().toString();
                list.add(newFieldString);
                // let the adapter know that the content is changed.
                adapter.notifyDataSetChanged();
            }
        });


        // set an listener that removes an item when it is long pressed.
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), list.get(position), Toast.LENGTH_SHORT).show();
                list.remove(position);
                adapter.notifyDataSetChanged();;
                return false;
            }
        });
    }


}
