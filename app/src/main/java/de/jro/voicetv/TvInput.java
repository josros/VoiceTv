package de.jro.voicetv;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.jro.androspacelib.Key;

public class TvInput extends AppCompatActivity {

    private static final String PREFS_NAME = "TvPrefs";
    private static final String TV_IP_KEY = "TvIp";

    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_input);

        EditText ipAddressEditText = (EditText) findViewById(R.id.ip_address);
        // load and set tv ip-address
        initIpSharedState(ipAddressEditText);

        final Button button = (Button) findViewById(R.id.remember_button);
        button.setOnClickListener(new RememberButtonListener());


        Intent intent = this.getIntent();

        Log.d(TvInput.class.getSimpleName(), intent == null ? "Null" : intent.toString());

        String action = intent.getAction();
        String query = null;

        // check whether intents action is create note action
        if (action.equals(com.google.android.gms.actions.NoteIntents.ACTION_CREATE_NOTE)) {
            // extract users voice query
            query = intent.getStringExtra(Intent.EXTRA_TEXT);

            // show query in the UI
            TextView intendedKeyView = (TextView) findViewById(R.id.intendedKey);
            intendedKeyView.setText(String.format(getString(R.string.tvinput_outfield_query_msg), query));
        }

        this.query = query == null ? "No query" : query;
        Log.i(TvInput.class.getSimpleName(), "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView infoView = (TextView) findViewById(R.id.infoView);
        try {
            // match query to key
            Key key = queryToKey(query.toLowerCase());

            // show match result in the UI
            infoView.setText(String.format(getString(R.string.tvinput_outfield_mkey_msg), key));

            // enter key task
            new TvInputTask(getCurrentEditTextIp(), this).execute(key);

        } catch (Exception ex) {
            Log.e(TvInput.class.getSimpleName(), ex.getMessage());
            infoView.setText(String.format(getString(R.string.tvinput_err_matching_key), ex.getMessage()));
        }
        Log.i(TvInput.class.getSimpleName(), "onStart");
    }

    /*
    PRIVATE METHODS
     */

    private void initIpSharedState(EditText ipAddressEditText) {
        String tfTvIp = getCurrentEditTextIp();

        // either takes ip from edit field (alternative) or value set in preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String sharedSettingsIp = settings.getString(TV_IP_KEY, tfTvIp);

        if(!sharedSettingsIp.equalsIgnoreCase(tfTvIp)) {
            ipAddressEditText.setText(sharedSettingsIp, TextView.BufferType.EDITABLE);
        }

        String logMsg = String.format("Loaded Tv ip-address: '%s'", sharedSettingsIp);
        Log.i(TvInput.class.getSimpleName(), logMsg);
    }

    private class RememberButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String tfTvIp = getCurrentEditTextIp();
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(TV_IP_KEY, tfTvIp);
            editor.commit();
        }
    }

    private String getCurrentEditTextIp() {
        EditText ipAddressEditText = (EditText) findViewById(R.id.ip_address);
        return ipAddressEditText.getText().toString();
    }

    private Key queryToKey(String queryLowerCase) {

        switch (queryLowerCase) {
            case "0":
            case "null":
            case "zero":
                return Key.DIGIT0;
            case "1":
            case "eins":
            case "one":
                return Key.DIGIT1;
            case "2":
            case "zwei":
            case "two":
                return Key.DIGIT2;
            case "3":
            case "drei":
            case "three":
                return Key.DIGIT3;
            case "4":
            case "vier":
            case "four":
                return Key.DIGIT4;
            case "5":
            case "fünf":
            case "five":
                return Key.DIGIT5;
            case "6":
            case "sechs":
            case "sex":
            case "six":
                return Key.DIGIT6;
            case "7":
            case "sieben":
            case "seven":
                return Key.DIGIT7;
            case "8":
            case "acht":
            case "eight":
                return Key.DIGIT8;
            case "9":
            case "neun":
            case "nine":
                return Key.DIGIT9;
            case "anschalten":
            case "ausschalten":
            case "einschalten":
            case "an":
            case "aus":
            case "off":
            case "turn off":
            case "turn on":
            case "on":
                return Key.STANDBY;
            case "leiser":
            case "quieter":
            case "less noisy":
                return Key.VOLUMEDOWN;
            case "lauter":
            case "louder":
                return Key.VOLUMEUP;
            case "weiter":
            case "vor":
            case "hoch":
            case "next":
                return Key.NEXT;
            case "zurück":
            case "rückwärts":
            case "runter":
            case "previous":
                return Key.PREVIOUS;
            // TODO more mappings, make configurable
            default:
                String error = String.format("Could not match key for query: %s", queryLowerCase);
                throw new RuntimeException(error);
        }

    }

}
