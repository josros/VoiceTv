package de.jro.voicetv;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import de.jro.androspacelib.Key;

public class TvInput extends AppCompatActivity {

    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_input);

        TextView intendedKeyView = (TextView) findViewById(R.id.intendedKey);

        Intent intent = this.getIntent();

        Log.i(TvInput.class.getSimpleName(), intent == null ? "Null" : intent.toString());


        String action = intent.getAction();
        String query = null;
        if (action.equals(com.google.android.gms.actions.NoteIntents.ACTION_CREATE_NOTE)) {
            query = intent.getStringExtra(Intent.EXTRA_TEXT);
            intendedKeyView.setText(String.format("User query: %s", query));
        }


        this.query = query == null ? "No query" : query;

        Log.i(TvInput.class.getSimpleName(), "onCreate");

    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            Key key = queryToKey(query);
            TextView infoView = (TextView) findViewById(R.id.infoView);
            infoView.setText(String.format("Matched key: %s", key));
            TextView enteredKeyView = (TextView) findViewById(R.id.enteredKey);
            // TODO improve configuration
            //192.168.0.11
            new TvInputTask("192.168.0.105", enteredKeyView).execute(key);
        } catch (Exception ex) {
            Log.e(TvInput.class.getSimpleName(), ex.getMessage());
        }
        Log.i(TvInput.class.getSimpleName(), "onStart");
    }

    private Key queryToKey(String query) {

        switch (query) {
            case "1":
                return Key.DIGIT1;
            // TODO more mappings
            default:
                String error = String.format("Could not match key for query: %s", query);
                throw new RuntimeException(error);
        }

    }

}
