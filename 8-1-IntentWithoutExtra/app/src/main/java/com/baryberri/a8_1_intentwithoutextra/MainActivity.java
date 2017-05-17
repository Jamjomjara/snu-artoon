package com.baryberri.a8_1_intentwithoutextra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button goToNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToNextButton = (Button)findViewById(R.id.goToNext);

        goToNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // An intent is used for communicating with other Activities.
                // Therefore, to run a new activity we should make an intent of that other activity.
                // This is an explicit intent, and we should add the activity to the Manifest.
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);

                startActivity(intent);
            }
        });
    }
}
