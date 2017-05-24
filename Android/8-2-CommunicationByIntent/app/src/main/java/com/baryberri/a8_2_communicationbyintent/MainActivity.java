package com.baryberri.a8_2_communicationbyintent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText number1;
    EditText number2;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number1 = (EditText)findViewById(R.id.number1);
        number2 = (EditText)findViewById(R.id.number2);
        addButton = (Button)findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Make a new intent.
                Intent intent = new Intent(getApplicationContext(), ComputeActivity.class);

                // If I want to send some data to the new activity,
                // we can send the data by adding <extra> to the intent that the new activity will get.
                intent.putExtra("number1", number1.getText().toString()); // sent in string form.
                intent.putExtra("number2", number2.getText().toString());

                // Call the new activity.
                // As I want the result to come back to this activity,
                // I should call the activity by using the method below.
                startActivityForResult(intent, 0);
            }
        });
    }

    // onActivityResult is called when the invoked activity returns to this activity.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Integer result = data.getIntExtra("Result", 0);
            Toast.makeText(getApplicationContext(), "result : " + result.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
