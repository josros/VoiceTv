package de.jro.androspacelib;

/**
 * Encapsulates jointspace api (http://jointspace.sourceforge.net/projectdata/documentation/jasonApi/1/doc/API.html).
 *
 * However, only for the following endpoint exists an implementation so far:
 * http://jointspace.sourceforge.net/projectdata/documentation/jasonApi/1/doc/API-Method-input-key-POST.html
 *
 * Created by J.Rossa on 25/04/16.
 */
public interface ITvControl {

    void enterKey(Key key);

}
