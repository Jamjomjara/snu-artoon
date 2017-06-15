package baryberri.a6_2_readingrawfolder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.button);
        editText = (EditText)findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputStream is = getResources().openRawResource(R.raw.rawfile);

                    // is.available() returns the byte size that can be read in the given input stream.
                    byte[] readText = new byte[is.available()];
                    is.read(readText);
                    is.close();

                    editText.setText(new String(readText));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
