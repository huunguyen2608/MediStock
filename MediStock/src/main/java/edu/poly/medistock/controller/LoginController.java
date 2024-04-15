/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.poly.medistock.controller;

import edu.poly.medistock.dao.UserDao;
import edu.poly.medistock.entity.User;
import edu.poly.medistock.view.LoginView;
import edu.poly.medistock.view.PharMedView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author ADMINZ
 */
public class LoginController {
    private UserDao userDao;
    private LoginView loginView;
     
    public LoginController(LoginView view) {
        this.loginView = view;
        this.userDao = new UserDao();
        view.LoginListener(new LoginListener());
    }
     
    public void showLoginPanel() {
        loginView.setVisible(true);
    }
     
    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            User user = loginView.getUser();
            if (userDao.checkUser(user)) {
                PharMedView pharMedView = new PharMedView();
                PharMedController pharMedController = new PharMedController(pharMedView);
                pharMedController.showPharMedView();
                loginView.setVisible(false);
            } else {
                loginView.showMessage("username hoặc password không đúng.");
            }
        }
    }
}
