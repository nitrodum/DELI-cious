package com.pluralsight;

import com.pluralsight.UI.LoginUserInterface;
import com.pluralsight.UI.UserInterface;
import com.pluralsight.filemanger.UserFileManager;

public class Main {
    public static void main(String[] args) {
        User user = LoginUserInterface.runLogin();
        UserInterface.run(user);
    }
}