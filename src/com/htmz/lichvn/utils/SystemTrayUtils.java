/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htmz.lichvn.utils;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 *
 * @author vtt0053
 */
public class SystemTrayUtils {
    
    public static void initialSystemTray(BufferedImage image, final Window calendarWiget) {
        //checking for support
        if (!SystemTray.isSupported()) {
            System.out.println("System tray is not supported !!! ");
            return;
        }
        //get the systemTray of the system
        SystemTray systemTray = SystemTray.getSystemTray();
        
        TrayIcon[] trayIcons = systemTray.getTrayIcons();
        
        Dimension trayIconSize = systemTray.getTrayIconSize();
        PopupMenu trayPopupMenu = new PopupMenu();
        //1t menuitem for popupmenu
        final MenuItem action = new MenuItem("Luôn hiển thị");
        action.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("Luôn hiển thị".equalsIgnoreCase(action.getLabel())) {
                    calendarWiget.setAlwaysOnTop(true);
                    action.setLabel("Mặc định");
                } else {
                    calendarWiget.setAlwaysOnTop(false);
                    action.setLabel("Luôn hiển thị");
                }
            }
        });
        //trayPopupMenu.add(action);

        //2nd menuitem of popupmenu
        MenuItem close = new MenuItem("Đóng");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        trayPopupMenu.add(close);
        
        //int trayIconWidth = new TrayIcon(image).getSize().width;
        //System.out.println("Icon set: " + trayIconWidth + " System set: " + trayIconSize.getWidth());

//setting tray icon
        TrayIcon trayIcon = new TrayIcon(image, "Lịch Việt Nam", trayPopupMenu);
        //adjust to default size as per sytrayPopupMenustem recommendation 
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final boolean isActive = calendarWiget.isAlwaysOnTop();
                if (isActive) {
                    calendarWiget.setAlwaysOnTop(false);
                } else {
                    calendarWiget.setAlwaysOnTop(true);
                }
            }
        });
        
        boolean isTrayExist = false;
        for (TrayIcon trIc : trayIcons) {
            final Image imgCase = trIc.getImage();
            System.out.println(trayIcon.getImage() + "==================" + imgCase);
            if (trayIcon.getImage().equals(imgCase)) {
                System.out.println("sIZE OF System " + trayIcon.getImage());
            }
        }
        try {
            if (trayIcons.length == 0) {
                systemTray.add(trayIcon);
            }
        } catch (AWTException awtException) {
            awtException.printStackTrace();
        }
        System.out.println("end of main");
    }
    
    public static Image imageForTray(Image trayImage, SystemTray theTray) {
        Dimension trayIconSize = theTray.getTrayIconSize();
        trayImage = trayImage.getScaledInstance(24, 24, Image.SCALE_FAST);
        
        return trayImage;
    }
}
