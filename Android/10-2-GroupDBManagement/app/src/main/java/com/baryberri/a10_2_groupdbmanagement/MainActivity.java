package com.baryberri.a10_2_groupdbmanagement;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    EditText nameEditText;
    EditText sizeEditText;
    Button resetButton;
    Button insertButton;
    Button searchButton;
    TextView nameResult;
    TextView sizeResult;
    SQLiteDatabase groupDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(getApplicationContext());
        nameEditText = (EditText)findViewById(R.id.nameEditText);
        sizeEditText = (EditText)findViewById(R.id.numberEditText);
        resetButton = (Button)findViewById(R.id.resetButton);
        insertButton = (Button)findViewById(R.id.insertButton);
        searchButton = (Button)findViewById(R.id.searchButton);
        nameResult = (TextView)findViewById(R.id.nameResult);
        sizeResult = (TextView)findViewById(R.id.sizeResult);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupDB = dbHelper.getWritableDatabase();
                dbHelper.onUpgrade(groupDB, 1, 2);
                groupDB.close();
            }
        });

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the DB from the DB server and execute the SQLite query.
                groupDB = dbHelper.getWritableDatabase();
                String groupName = nameEditText.getText().toString();
                String groupSize = sizeEditText.getText().toString();
                groupDB.execSQL("INSERT INTO groupDB VALUES("
                        + "'" + groupName + "', "
                        + groupSize + ");");

                // close the DB server
                groupDB.close();

                Toast.makeText(getApplicationContext(), "Succesfully Saved!", Toast.LENGTH_SHORT).show();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupDB = dbHelper.getReadableDatabase();

                // To search the DB, we use a method using a class called 'Cursor'.
                // when we give a search query, it gives the result in Cursor form.
                Cursor cursor = groupDB.rawQuery("SELECT * FROM groupDB", null);

                String nameResultString = "Name\n----------\n";
                String sizeResultString = "Size\n----------\n";

                while (cursor.moveToNext()) {
                    // can get the result using the cursor, indexed by the column number.
                    nameResultString += cursor.getString(0) + "\n";
                    sizeResultString += cursor.getString(1) + "\n";
                }

                nameResult.setText(nameResultString);
                sizeResult.setText(sizeResultString);

                cursor.close();
                groupDB.close();
            }
        });
    }

    // A class needed to communicate with SQLite DB server.
    private class DBHelper extends SQLiteOpenHelper {
        DBHelper(Context context) {
            super(context, "groupDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // execSQL runs the given query in String form.
            // what I have to do at onCreate is to make a DB server, so I made it here.
            // groupName is set as 'PRIMARY KEY', which means is is always needed.
            db.execSQL("CREATE TABLE groupDB(groupName CHAR(20) PRIMARY KEY, groupSize INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS groupDB");
            onCreate(db);
        }
    }
}
