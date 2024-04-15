/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package edu.poly.medistock;

import edu.poly.medistock.controller.LoginController;
import edu.poly.medistock.view.LoginView;

/**
 *
 * @author ADMINZ
 */
public class MediStock {

    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginView);
        
        loginController.showLoginPanel();
       }
    
}
