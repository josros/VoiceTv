package de.jro.voicetv;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import de.jro.androspacelib.ITvControl;
import de.jro.androspacelib.Key;
import de.jro.androspacelib.TvControlFactory;

/**
 * Created by J.Rossa on 20/06/16.
 */
public class TvInputTask extends AsyncTask<Key, Void, AsyncTaskResult<Key>> {

    private final ITvControl control;
    private final TextView enteredKeyView;

    public TvInputTask(String ipAddr, TextView enteredKeyView) {
        this.control = TvControlFactory.getInstance().createTvControl(ipAddr);
        this.enteredKeyView = enteredKeyView;
    }

    @Override
    protected AsyncTaskResult<Key> doInBackground(Key... keys) {
        try {
            Key key = keys[0];
            control.enterKey(key);
            return new AsyncTaskResult<Key>(key);
        } catch (Exception e) {
            Log.e("TvInputTask", e.getMessage(), e);
            return new AsyncTaskResult<Key>(e);
        }
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<Key> result) {
        if(!result.hasError()) {
            enteredKeyView.setText("Entered key: " + result.getResult().name());
        } else {
            String error = String.format("Entering key failed due to the following reason: %s",
                    result.getError().getMessage());
            enteredKeyView.setText(error);
        }
    }


}
