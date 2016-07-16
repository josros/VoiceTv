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
            //tv in local network: 192.168.0.11 int test endpoint: 192.168.0.105
            new TvInputTask("192.168.0.11", enteredKeyView).execute(key);
        } catch (Exception ex) {
            Log.e(TvInput.class.getSimpleName(), ex.getMessage());
        }
        Log.i(TvInput.class.getSimpleName(), "onStart");
    }

    private Key queryToKey(String query) {

        switch (query) {
            case "0":
                return Key.DIGIT0;
            case "1":
            case "eins":
            case "Eins":
                return Key.DIGIT1;
            case "2":
            case "zwei":
            case "Zwei":
                return Key.DIGIT2;
            case "3":
            case "drei":
            case "Drei":
                return Key.DIGIT3;
            case "4":
            case "vier":
            case "Vier":
                return Key.DIGIT4;
            case "5":
            case "fünf":
            case "Fünf":
                return Key.DIGIT5;
            case "6":
            case "sechs":
            case "Sechs":
            case "sex":
            case "Sex":
                return Key.DIGIT6;
            case "7":
            case "sieben":
            case "Sieben":
                return Key.DIGIT7;
            case "8":
            case "acht":
            case "Acht":
                return Key.DIGIT8;
            case "9":
            case "neun":
            case "Neun":
                return Key.DIGIT9;
            case "Anschalten":
            case "Ausschalten":
            case "anschalten":
            case "ausschalten":
            case "einschalten":
            case "Einschalten":
            case "an":
            case "aus":
            case "An":
            case "Aus":
                return Key.STANDBY;
            case "Leiser":
            case "leiser":
                return Key.VOLUMEDOWN;
            case "Lauter":
            case "lauter":
                return Key.VOLUMEUP;
            // TODO more mappings
            default:
                String error = String.format("Could not match key for query: %s", query);
                throw new RuntimeException(error);
        }

    }

}
