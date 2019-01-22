package pl.piwosz.server.ui_server.controller;

import pl.piwosz.server.ui_server.view.MainFrameServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainFrameController {
    private MainFrameServer mainFrameServer;

    public MainFrameController() {
        initComponents();
    }

    public void showMainFrameWindow() {
        mainFrameServer.setVisible(true);
    }

    private void initComponents() {
        this.mainFrameServer = new MainFrameServer();
    }

}
