package pl.piwosz.chat_gui;

import pl.piwosz.chat_gui.ui.controller.MainFrameController;

public class Runner {
    public static void main(String[] args){
        MainFrameController mainFrameController = new MainFrameController();
        mainFrameController.showMainFrameWindow();
    }
}
