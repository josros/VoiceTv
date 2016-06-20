package de.jro.androspacelib;

/**
 * Created by J.Rossa on 25/04/16.
 */
public class TvControlFactory {

    private static final TvControlFactory SINGLETON = new TvControlFactory();
    private TvControlFactory() {}

    public static TvControlFactory getInstance() {
        return SINGLETON;
    }

    public ITvControl createTvControl(String deviceIp) {
        return new TvControlImpl(deviceIp);
    }

}
