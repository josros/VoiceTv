package de.jro.voicetv;

/**
 * Created by jro on 16/07/16.
 */
public class AsyncTaskResult<T> {
    private T result;
    private Exception error;

    public AsyncTaskResult(T result) {
        this.result = result;
    }

    public AsyncTaskResult(Exception error) {
        this.error = error;
    }

    public T getResult() {
        return result;
    }

    public Exception getError() {
        return error;
    }

    public boolean hasError() {
        return error != null;
    }


}
