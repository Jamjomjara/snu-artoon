package com.baryberri.a10_1_simplesqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 1. Enter to the app by using adb.
    // 2. <sqlite3 testDB> to make a database. It enters to the sqlite and makes a database.
    // 3. Use <CREATE TABLE name (Column type, Column type, ...);> to make a table.
    //      CREATE TABLE test(id char(4), userName char(15), birthday int);
    //      c.f., to remove a table, use <DROP TABLE test;>
    // 4. Insert values using <INSERT INTO test VALUES('Hello', 'World!', 010203);>
    // 5. Make a query using <SELECT id, userName FROM test WHERE id = 'a';>
    //      SELECT * FROM test WHERE birthday >= 3;
    // 6. To quit the SQLite, type <.exit>
    //      SQLite reserved words start with period, and don't have following semicolons.


}
