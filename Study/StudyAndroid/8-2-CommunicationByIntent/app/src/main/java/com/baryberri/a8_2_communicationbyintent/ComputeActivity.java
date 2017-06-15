package com.baryberri.a8_2_communicationbyintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ComputeActivity extends AppCompatActivity {

    Button backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compute_activity);

        backButton = (Button)findViewById(R.id.goBack);

        // To access the intent, we get the intent.
        Intent inIntent = getIntent();

        // Get the extras by using getExtra methods.
        String number1 = inIntent.getStringExtra("number1");
        String number2 = inIntent.getStringExtra("number2");

        int op1 = Integer.parseInt(number1);
        int op2 = Integer.parseInt(number2);
        final int result = op1 + op2;

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // To return the value to the previous activity, make another intent.
                Intent outIntent = new Intent(getApplicationContext(), MainActivity.class);
                outIntent.putExtra("Result", result);

                // Set the outIntent as the result of this activity.
                setResult(RESULT_OK, outIntent);

                // finish this activity.
                finish();
            }
        });
    }
}
