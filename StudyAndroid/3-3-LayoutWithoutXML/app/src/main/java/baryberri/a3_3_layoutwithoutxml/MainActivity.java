package baryberri.a3_3_layoutwithoutxml;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setContentView just creates the XML layouts
        // defined in R.layout.activity_main.
        // In this example, we'll make the layouts by using the Java code without XML
        // so I just commented out setContentView.
        //setContentView(R.layout.activity_main);

        // 1. Generate Layout Parameter
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        // 2. Generate LinearLayout on this context.
        LinearLayout baseLayout = new LinearLayout(this);
        baseLayout.setOrientation(LinearLayout.VERTICAL);
        baseLayout.setBackgroundColor(Color.rgb(0, 255, 0));

        // 3. Draw the generated view by using setContentView.
        setContentView(baseLayout, params);


        // 4. Likely, make a Button View on the top.
        Button button = new Button(this);
        button.setText("Button");
        button.setBackgroundColor(Color.MAGENTA);

        // To add a view to the layout, use "addView" from the layout.
        baseLayout.addView(button);


        // From now on, we generated Views with Java, not XML.
        // However, the state from now is the same.
        // Therefore, we can use the views as we used before, like setting Listeners.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "You clicked a button!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
