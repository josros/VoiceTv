package de.jro.voicetv;

import java.util.Iterator;
import java.util.List;

import de.jro.androspacelib.Key;

/**
 * Created by jro on 23/07/16.
 */
public class KeyNamesConverter {

    public static String toString(List<Key> keys) {
        StringBuilder builder = new StringBuilder();
        Iterator<Key> keyIterator = keys.iterator();
        while(keyIterator.hasNext()) {
            builder.append(keyIterator.next().name());
            if(keyIterator.hasNext()) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }

}
