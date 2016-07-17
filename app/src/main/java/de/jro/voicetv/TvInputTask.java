package de.jro.voicetv;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import de.jro.androspacelib.ITvControl;
import de.jro.androspacelib.Key;
import de.jro.androspacelib.TvControlFactory;

/**
 * Created by J.Rossa on 20/06/16.
 */
public class TvInputTask extends AsyncTask<List<Key>, Void, AsyncTaskResult<List<Key>>> {

    private final ITvControl control;
    private final TvInput tvInputActivity;

    public TvInputTask(String ipAddr, TvInput tvInputActivity) {
        this.control = TvControlFactory.getInstance().createTvControl(ipAddr);
        this.tvInputActivity = tvInputActivity;
    }

    @Override
    protected AsyncTaskResult<List<Key>> doInBackground(List<Key> ... keys) {
        try {
            List<Key> keysFirst = keys[0];
            for(Key k : keysFirst) {
                control.enterKey(k);
            }
            return new AsyncTaskResult<List<Key>>(keysFirst);
        } catch (Exception e) {
            Log.e(TvInputTask.class.getSimpleName(), e.getMessage(), e);
            return new AsyncTaskResult<List<Key>>(e);
        }
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<List<Key>> result) {
        TextView enteredKeyView = (TextView) tvInputActivity.findViewById(R.id.enteredKey);
        if(!result.hasError()) {
            enteredKeyView.setText(String.format(
                    tvInputActivity.getString(R.string.tvinput_outfield_ekey_msg),
                    KeyNamesConverter.toString(result.getResult())));
        } else {
            String error = String.format(tvInputActivity.getString(R.string.tvinput_err_enter_key),
                    result.getError().getMessage());
            enteredKeyView.setText(error);
        }
    }

}
