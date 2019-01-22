package pl.piwosz.server;

import java.io.Serializable;

public class Message implements Serializable {

    private String text;
    private String nick;
    private String reciver;
    private boolean isPrivate;

    public Message(String text, String nick, String reciver, boolean isPrivate) {
        this.text = text;
        this.nick = nick;
        this.reciver = reciver;
        this.isPrivate = isPrivate;
    }
    @Override
    public String toString(){
        return nick + ": " + text;
    }
    public String getText() {
        return text;
    }

    public String getNick() {
        return nick;
    }

    public String getReciver() {
        return reciver;
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}
