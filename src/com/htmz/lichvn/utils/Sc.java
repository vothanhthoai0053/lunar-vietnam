/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htmz.lichvn.utils;

import net.jimmc.jshortcut.JShellLink;

/**
 *
 * @author HTMZ
 */
import net.jimmc.jshortcut.JShellLink;

public class Sc {

    JShellLink link;

    String filePath;

    public Sc() {

        try {

            link = new JShellLink();

            filePath = JShellLink.getDirectory("") + System.getProperty("user.dir")+"/widgetCalendar.exe";

        } catch (Exception e) {

        }

    }

    public void createShortcut() {

        try {

            link.setFolder(System.getProperty("user.dir")+"/src/");

            link.setName("widget");

            link.setPath(filePath);

            link.save();

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
}
