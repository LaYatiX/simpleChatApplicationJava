package pl.piwosz.server;

import java.io.Serializable;

public class Message implements Serializable {
    String text;
    String nick;
    boolean isPrivate;

    public Message(String text, String nick, boolean isPrivate) {
        this.text = text;
        this.nick = nick;
        this.isPrivate = isPrivate;
    }
    @Override
    public String toString(){
        return nick + ": " + text;
    }
}
