package baryberri.a5_1_changebackgroundcolorbymenu;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    LinearLayout baseLayout;
    Button aButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        baseLayout = (LinearLayout)findViewById(R.id.baseLayout);
        aButton = (Button)findViewById(R.id.aButton);
    }

    // The method for creating and showing menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;

        // To make Menu without XML,
        // use menu.add(group, id, order, title) method.
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.orange:
                baseLayout.setBackgroundColor(Color.rgb(253, 128, 35));
                return true;

            case R.id.green:
                baseLayout.setBackgroundColor(Color.GREEN);
                return true;

            case R.id.blue:
                baseLayout.setBackgroundColor(Color.BLUE);
                return true;

            case R.id.white:
                baseLayout.setBackgroundColor(Color.WHITE);
                return true;

            case R.id.clockwise:
                aButton.setRotation(aButton.getRotation() + 45);
                return true;

            case R.id.counterclockwise:
                aButton.setRotation(aButton.getRotation() - 45);
                return true;

            default:
                return false;
        }
    }
}
