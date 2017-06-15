package baryberri.a5_2_changebackgroundcolorbycontextmenu;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    LinearLayout baseLayout;
    Button backgroundButton;
    Button shapeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        baseLayout = (LinearLayout)findViewById(R.id.baseLayout);
        backgroundButton = (Button)findViewById(R.id.backgroundButton);
        shapeButton = (Button)findViewById(R.id.shapeButton);

        // Some views have ContextMenu while others does not.
        // Therefore we should register views that have ContextMenu.
        registerForContextMenu(backgroundButton);
        registerForContextMenu(shapeButton);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // As contextMenu differs view by view, we should check the type of given view v,
        // and show the appropriate ContextMenu.
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        if (v == backgroundButton) {
            menu.setHeaderTitle("Change Background Color");
            inflater.inflate(R.menu.background_color_menu, menu);
        } else if (v == shapeButton) {
            menu.setHeaderTitle("Change Button Shape");
            inflater.inflate(R.menu.button_modification_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
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

            case R.id.rotate:
                shapeButton.setRotation(shapeButton.getRotation() + 45);
                return true;

            case R.id.bigger:
                shapeButton.setScaleX(2);
                shapeButton.setScaleY(2);
                return true;

            case R.id.smaller:
                shapeButton.setScaleX((float)0.5);
                shapeButton.setScaleY((float)0.5);
                return true;

            default:
                return false;
        }
    }
}
