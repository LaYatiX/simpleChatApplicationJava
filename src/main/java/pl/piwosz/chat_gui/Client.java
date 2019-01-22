package pl.piwosz.chat_gui;

import pl.piwosz.chat_gui.ui.controller.MainFrameController;

public class Client {
    public static void main(String[] args){
        try{
            MainFrameController mainFrameController = new MainFrameController();
            mainFrameController.showMainFrameWindow();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
