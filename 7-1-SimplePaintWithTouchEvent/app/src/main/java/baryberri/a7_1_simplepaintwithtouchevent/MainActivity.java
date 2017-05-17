package baryberri.a7_1_simplepaintwithtouchevent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    public final static int LINE = 1;
    public final static int CIRCLE = 2;
    public static int currentShape = LINE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GraphicsView(this));
        setTitle("Simple Paint");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, LINE, 0, "Draw Line");
        menu.add(0, CIRCLE, 0, "Draw Circle");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        currentShape = item.getItemId();
        return true;
    }
}
