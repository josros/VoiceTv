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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.jro.androspacelib.Key;

public class TvInput extends AppCompatActivity {

    private static final String PREFS_NAME = "TvPrefs";
    private static final String TV_IP_KEY = "TvIp";
    private static Pattern LONG_PATTERN = Pattern.compile("^-?\\d{1,19}$");

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
            List<Key> keys = queryToKeys(query.toLowerCase());

            // show match result in the UI
            infoView.setText(String.format(getString(R.string.tvinput_outfield_mkey_msg), KeyNamesConverter.toString(keys)));

            // enter key task
            new TvInputTask(getCurrentEditTextIp(), this).execute(keys);

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

    private List<Key> queryToKeys(String queryLowerCase) {

        switch (queryLowerCase) {
            case "0":
            case "null":
            case "zero":
                return Arrays.asList(Key.DIGIT0);
            case "1":
            case "eins":
            case "one":
                return Arrays.asList(Key.DIGIT1);
            case "2":
            case "zwei":
            case "two":
                return Arrays.asList(Key.DIGIT2);
            case "3":
            case "drei":
            case "three":
                return Arrays.asList(Key.DIGIT3);
            case "4":
            case "vier":
            case "four":
                return Arrays.asList(Key.DIGIT4);
            case "5":
            case "fünf":
            case "five":
                return Arrays.asList(Key.DIGIT5);
            case "6":
            case "sechs":
            case "sex":
            case "six":
                return Arrays.asList(Key.DIGIT6);
            case "7":
            case "sieben":
            case "seven":
                return Arrays.asList(Key.DIGIT7);
            case "8":
            case "acht":
            case "eight":
                return Arrays.asList(Key.DIGIT8);
            case "9":
            case "neun":
            case "nine":
                return Arrays.asList(Key.DIGIT9);
            case "anschalten":
            case "ausschalten":
            case "einschalten":
            case "an":
            case "aus":
            case "off":
            case "turn off":
            case "turn on":
            case "on":
                return Arrays.asList(Key.STANDBY);
            case "leiser":
            case "quieter":
            case "less noisy":
                return Arrays.asList(Key.VOLUMEDOWN);
            case "lauter":
            case "louder":
                return Arrays.asList(Key.VOLUMEUP);
            case "weiter":
            case "vor":
            case "hoch":
            case "next":
                return Arrays.asList(Key.NEXT);
            case "zurück":
            case "rückwärts":
            case "runter":
            case "previous":
                return Arrays.asList(Key.PREVIOUS);
            // TODO more mappings, make configurable
            default:
                if(isQueryNumber(queryLowerCase)) {
                    return convertToKeys(queryLowerCase);
                }
                // in case its not even a number, we can not match the query
                String error = String.format("Could not match key for query: %s", queryLowerCase);
                throw new RuntimeException(error);
        }

    }

    private boolean isQueryNumber(String query) {
        Matcher matcher = LONG_PATTERN.matcher(query);
        boolean isANumber = matcher.matches();
        return isANumber;
    }

    private List<Key> convertToKeys(String numberQuery) {
        List<Key> keys = new ArrayList<>();
        for(int i = 0; i < numberQuery.length(); i++) {
            Character digit = numberQuery.charAt(i);
            String keyStr = String.format("DIGIT%s", digit);
            Key key = Key.valueOf(keyStr);
            keys.add(key);
        }
        keys.add(Key.CONFIRM);
        return keys;
    }

}
