package de.jro.androspacelib;

/**
 * Represents the input keys as stated here:
 * http://jointspace.sourceforge.net/projectdata/documentation/jasonApi/1/doc/API-Method-input-key-POST.html
 *
 * Created by J.Rossa on 25/04/16.
 */
public enum Key {

    STANDBY("Standby"),
    BACK("Back"),
    FIND("Find"),
    REDCOLOUR("RedColour"),
    GREENCOLOUR("GreenColour"),
    YELLOWCOLOUR("YellowColour"),
    BLUECOLOUR("BlueColour"),
    HOME("Home"),
    VOLUMEUP("VolumeUp"),
    VOLUMEDOWN("VolumeDown"),
    MUTE("Mute"),
    OPTIONS("Options"),
    DOT("Dot"),
    DIGIT0("Digit0"),
    DIGIT1("Digit1"),
    DIGIT2("Digit2"),
    DIGIT3("Digit3"),
    DIGIT4("Digit4"),
    DIGIT5("Digit5"),
    DIGIT6("Digit6"),
    DIGIT7("Digit7"),
    DIGIT8("Digit8"),
    DIGIT9("Digit9"),
    INFO("Info"),
    CURSORUP("CursorUp"),
    CURSORDOWN("CursorDown"),
    CURSORLEFT("CursorLeft"),
    CURSORRIGHT("CursorRight"),
    CONFIRM("Confirm"),
    NEXT("Next"),
    PREVIOUS("Previous"),
    ADJUST("Adjust"),
    WATCHTV("WatchTV"),
    VIEWMODE("Viewmode"),
    TELETEXT("Teletext"),
    SUBTITLE("Subtitle"),
    CHANNELSTEPUP("ChannelStepUp"),
    CHANNELSTEPDOWN("ChannelStepDown"),
    SOURCE("Source"),
    AMBILIGHTONOFF("AmbilightOnOff"),
    PLAYPAUSE("PlayPause"),
    PAUSE("Pause"),
    FASTFORWARD("FastForward"),
    STOP("Stop"),
    REWIND("Rewind"),
    RECORD("Record"),
    ONLINE("Online");

    private String jointspaceValue;

    Key(String jointspaceValue) {
        this.jointspaceValue = jointspaceValue;
    }

    String getJointspaceValue() {
        return jointspaceValue;
    }
}
