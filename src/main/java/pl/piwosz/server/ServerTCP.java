package pl.piwosz.server;

import pl.piwosz.chat_gui.ui.controller.MainFrameController;
import pl.piwosz.chat_gui.ui.model.Database;
import pl.piwosz.server.ui_server.controller.ServerMainController;
import pl.piwosz.server.ui_server.view.MainFrameServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static pl.piwosz.chat_gui.ui.model.Database.createStatement;
import static pl.piwosz.chat_gui.ui.model.Database.executeUpdate;
import static pl.piwosz.chat_gui.ui.model.Database.getConnection;

public class ServerTCP {
	public static void main(String args[]) {
            ServerMainController serverMainController = new ServerMainController();
            serverMainController.showMainFrameWindow();
            serverMainController.initServer();
		}

}
