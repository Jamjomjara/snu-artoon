package com.baryberri.a8_1_intentwithoutextra;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {
    Button goToPreviousButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_page);
        goToPreviousButton = (Button)findViewById(R.id.goToPrevious);

        goToPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to finish an activity, just run finish() method.
                finish();
            }
        });
    }
}
