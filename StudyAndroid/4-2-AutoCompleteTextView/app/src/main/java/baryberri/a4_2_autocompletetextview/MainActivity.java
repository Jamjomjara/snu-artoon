package baryberri.a4_2_autocompletetextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

public class MainActivity extends AppCompatActivity {
    AutoCompleteTextView autoComplete;
    MultiAutoCompleteTextView multiComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoComplete = (AutoCompleteTextView)findViewById(R.id.autoComplete);
        multiComplete = (MultiAutoCompleteTextView)findViewById(R.id.multiAutoComplete);

        // The lists to be shown to the user.
        String[] lists = {"Hello-World", "Hello-Thanks", "Hello-Morning",
                          "Bye-World", "Bye-Thanks", "Bye-Morning"};

        // To link the data and the view, we should use 'Adapter'.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                lists);

        // Link the adapter above to the view.
        autoComplete.setAdapter(adapter);


        // Set the tokenizer for the multiComplete.
        multiComplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        multiComplete.setAdapter(adapter);
    }
}
