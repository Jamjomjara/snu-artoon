package com.baryberri.a1_2_buttonlaunchingwebpage;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // objects
    EditText addressEdit;
    Button button1;
    Button button2;
    RadioGroup radios;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // linking objects
        addressEdit = (EditText)findViewById(R.id.address_edit);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        radios = (RadioGroup)findViewById(R.id.radios);
        imageView = (ImageView)findViewById(R.id.image_view);

        // When the app starts, the imageView should show google image.
        // and therefore, the radio button 1 should be selected.
        imageView.setImageResource(R.drawable.google);
        radios.check(R.id.radio1);

        // give them some jobs
        // when button1 is clicked, show the text on the screen.
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), addressEdit.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        // button2: launch the web page.
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(addressEdit.getText().toString()));
                startActivity(mIntent);
            }
        });

        // radio button 1, 2: change the imageView.
        radios.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedID) {
                switch (checkedID) {
                    case R.id.radio1:
                        imageView.setImageResource(R.drawable.google);
                        break;

                    case R.id.radio2:
                        imageView.setImageResource(R.drawable.android);
                        break;

                    default:
                        imageView.setImageResource(R.drawable.google);
                }
            }
        });
    }
}
