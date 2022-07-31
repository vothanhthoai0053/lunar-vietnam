/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htmz.lichvn.main;

import com.htmz.lichvn.utils.LunarUtils;
import com.htmz.lichvn.utils.SystemTrayUtils;
import com.htmz.lichvn.utils.Utils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.Timer;
import javax.swing.UIManager;

public class CalendarMonth extends javax.swing.JDialog implements ActionListener {

    private ArrayList<JLabel> jls, jal, jrand;
    private JMenuItem thoat = new JMenuItem("Thoát"), chuyenngay = new JMenuItem("Điều hướng"), about = new JMenuItem("Thông tin"), trolai = new JMenuItem("Ẩn điều hướng");
    private JCheckBoxMenuItem ngay = new JCheckBoxMenuItem("Ngày"), thang = new JCheckBoxMenuItem("Tháng");
    private CalendarWiget wgcalendar;
    private Timer timer;
    private Font fontDefault;
    private final int WIDTH_SIZE = 630;
    private final int HEIGHT_SIZE = 320;
    private final int HEIGHT_FIND_DATE = 360;
    private final int HEIGHT_LESS_DATE = 270;

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                System.out.println("com.htmz.lichvn.main.CalendarWiget.main() " + info.getName());
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
                } else {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CalendarWiget.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CalendarWiget.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CalendarWiget.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CalendarWiget.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CalendarMonth dialog = new CalendarMonth(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    public CalendarMonth(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        try {
            InputStream is = this.getClass().getResourceAsStream("/fonts/Quicksand-Medium.ttf");
            this.fontDefault = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //      this.setUndecorated(true);
        this.setUndecorated(true);
        initComponents();
        //this.setResizable(false);
        coverLabel();
        modellAllCombo();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        int maxX = (int) (width - WIDTH_SIZE - 0.9);
        System.out.println("====>: " + maxX);
        this.setLocation(maxX, 0);

        comboDate.setVisible(false);
        comboMonth.setVisible(false);
        comboYear.setVisible(false);
        trolai.setVisible(false);
        chuyenngay.setVisible(true);

        this.setSize(WIDTH_SIZE, HEIGHT_SIZE);

        BufferedImage bfi;
        try {
            bfi = ImageIO.read(getClass().getResource("/IMG/root.jpg").toURI().toURL());
            ImageIcon imc = new ImageIcon(bfi.getScaledInstance(400, 380, bfi.SCALE_SMOOTH));
            jLabel1.setIcon(imc);
        } catch (Exception ex) {
            // jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/background/mother.jpg"))); // NOI18N
        }
        changeDate();
        this.getRootPane().setBorder(BorderFactory.createLineBorder(Color.RED));
        SystemTrayUtils.initialSystemTray(Utils.trayIconDefault(), this);
    }

    private void changeDate() {
        setNullAll();
        setFontDefault();
        Date d = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("MM");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy");
        int month = Integer.parseInt(format1.format(d.getTime()));
        int year = Integer.parseInt(format2.format(d.getTime()));
        long begin = Calendar.getInstance().getTimeInMillis();
        firstDay(1, 11, year);
        //   System.out.println("Time cahnge : " + (Calendar.getInstance().getTimeInMillis() - begin));
        setColor();
        SimpleDateFormat format3 = new SimpleDateFormat("dd");
        int day = Integer.parseInt(format3.format(d.getTime()));
        changeColorDateNow(day);
        //updateTime();
        //timer.start();
        popupMenu();
        comboDate.setSelectedItem(day + "");
        comboMonth.setSelectedItem(month + "");
        comboYear.setSelectedItem(year + "");
        fixSize();

    }

    private void updateTime() {
        int giay, phut, gio;
        timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String a[] = getDay();
                if (a[5].equals("00") && a[4].equals("00") && a[3].equals("00")) {
                    changeDate();
                } else {           //         System.out.print("CCC");

                    //        System.out.println(a[5] + "-" + a[4] + "-" + a[3]);
                }
            }
        });
    }

    private void setColor() {
        day1.setForeground(Color.BLUE);
        day2.setForeground(Color.BLUE);
        day3.setForeground(Color.BLUE);
        day4.setForeground(Color.BLUE);
        day5.setForeground(Color.BLUE);
        day6.setForeground(Color.BLUE);
        day7.setForeground(Color.BLUE);
        day8.setForeground(Color.BLUE);
        day9.setForeground(Color.BLUE);
        day10.setForeground(Color.BLUE);
        day11.setForeground(Color.BLUE);
        day12.setForeground(Color.BLUE);
        day13.setForeground(Color.BLUE);
        day14.setForeground(Color.BLUE);
        day15.setForeground(Color.BLUE);
        day16.setForeground(Color.BLUE);
        day17.setForeground(Color.BLUE);
        day18.setForeground(Color.BLUE);
        day19.setForeground(Color.BLUE);
        day20.setForeground(Color.BLUE);
        day21.setForeground(Color.BLUE);
        day22.setForeground(Color.BLUE);
        day23.setForeground(Color.BLUE);
        day24.setForeground(Color.BLUE);
        day25.setForeground(Color.BLUE);
        day26.setForeground(Color.BLUE);
        day27.setForeground(Color.BLUE);
        day28.setForeground(Color.BLUE);
        day29.setForeground(Color.BLUE);
        day30.setForeground(Color.BLUE);
        day31.setForeground(Color.BLUE);
        day32.setForeground(Color.BLUE);
        day33.setForeground(Color.BLUE);
        day34.setForeground(Color.BLUE);
        day36.setForeground(Color.BLUE);
        day35.setForeground(Color.BLUE);
        day37.setForeground(Color.BLUE);
        day38.setForeground(Color.BLUE);
        day39.setForeground(Color.BLUE);
        day4.setForeground(Color.BLUE);
        day41.setForeground(Color.BLUE);
        day42.setForeground(Color.BLUE);

        su.setForeground(Color.GREEN);
        mo.setForeground(Color.GREEN);
        tu.setForeground(Color.GREEN);
        we.setForeground(Color.GREEN);
        th.setForeground(Color.GREEN);
        fr.setForeground(Color.GREEN);
        sa.setForeground(Color.GREEN);

        al1.setForeground(Color.RED);
        al2.setForeground(Color.RED);
        al3.setForeground(Color.RED);
        al4.setForeground(Color.RED);
        al5.setForeground(Color.RED);
        al6.setForeground(Color.RED);
        al7.setForeground(Color.RED);
        al8.setForeground(Color.RED);
        al9.setForeground(Color.RED);
        al10.setForeground(Color.RED);
        al11.setForeground(Color.RED);
        al12.setForeground(Color.RED);
        al13.setForeground(Color.RED);
        al14.setForeground(Color.RED);
        al15.setForeground(Color.RED);
        al16.setForeground(Color.RED);
        al17.setForeground(Color.RED);
        al18.setForeground(Color.RED);
        al19.setForeground(Color.RED);
        al20.setForeground(Color.RED);
        al21.setForeground(Color.RED);
        al22.setForeground(Color.RED);
        al23.setForeground(Color.RED);
        al24.setForeground(Color.RED);
        al25.setForeground(Color.RED);
        al26.setForeground(Color.RED);
        al27.setForeground(Color.RED);
        al28.setForeground(Color.RED);
        al29.setForeground(Color.RED);
        al30.setForeground(Color.RED);
        al31.setForeground(Color.RED);
        al32.setForeground(Color.RED);
        al33.setForeground(Color.RED);
        al34.setForeground(Color.RED);
        al35.setForeground(Color.RED);
        al36.setForeground(Color.RED);
        al37.setForeground(Color.RED);
        al38.setForeground(Color.RED);
        al39.setForeground(Color.RED);
        al40.setForeground(Color.RED);
        al41.setForeground(Color.RED);
        al42.setForeground(Color.RED);
    }

    public void changeBackground(JLabel jl, JLabel jl1) {
        Font sized12Font = fontDefault.deriveFont(1, 10f);
        Font sized23Font = fontDefault.deriveFont(1, 23f);
        jl1.setFont(sized12Font);
        jl.setFont(sized23Font);

    }

    private void changeColorDateNow(int day) {

        for (int i = 0; i < jls.size(); i++) {
            if (jls.get(i).getText() != null) {
                int temp = Integer.parseInt(jls.get(i).getText());
                if (temp == day) {
                    changeBackground(jls.get(i), jal.get(i));
                    return;
                }
            }
        }
    }

    private void coverLabel() {
        jls = new ArrayList<JLabel>();
        jls.add(0, day1);
        jls.add(1, day2);
        jls.add(2, day3);
        jls.add(3, day4);
        jls.add(4, day5);
        jls.add(5, day6);
        jls.add(6, day7);
        jls.add(7, day8);
        jls.add(8, day9);
        jls.add(9, day10);
        jls.add(10, day11);
        jls.add(11, day12);
        jls.add(12, day13);
        jls.add(13, day14);
        jls.add(14, day15);
        jls.add(15, day16);
        jls.add(16, day17);
        jls.add(17, day18);
        jls.add(18, day19);
        jls.add(19, day20);
        jls.add(20, day21);
        jls.add(21, day22);
        jls.add(22, day23);
        jls.add(23, day24);
        jls.add(24, day25);
        jls.add(25, day26);
        jls.add(26, day27);
        jls.add(27, day28);
        jls.add(28, day29);
        jls.add(29, day30);
        jls.add(30, day31);
        jls.add(31, day32);
        jls.add(32, day33);
        jls.add(33, day34);
        jls.add(34, day35);
        jls.add(35, day36);
        jls.add(36, day37);
        jls.add(37, day38);
        jls.add(38, day39);
        jls.add(39, day40);
        jls.add(40, day41);
        jls.add(41, day42);

        jal = new ArrayList<JLabel>();
        jal.add(0, al1);
        jal.add(1, al2);
        jal.add(2, al3);
        jal.add(3, al4);
        jal.add(4, al5);
        jal.add(5, al6);
        jal.add(6, al7);
        jal.add(7, al8);
        jal.add(8, al9);
        jal.add(9, al10);
        jal.add(10, al11);
        jal.add(11, al12);
        jal.add(12, al13);
        jal.add(13, al14);
        jal.add(14, al15);
        jal.add(15, al16);
        jal.add(16, al17);
        jal.add(17, al18);
        jal.add(18, al19);
        jal.add(19, al20);
        jal.add(20, al21);
        jal.add(21, al22);
        jal.add(22, al23);
        jal.add(23, al24);
        jal.add(24, al25);
        jal.add(25, al26);
        jal.add(26, al27);
        jal.add(27, al28);
        jal.add(28, al29);
        jal.add(29, al30);
        jal.add(30, al31);
        jal.add(31, al32);
        jal.add(32, al33);
        jal.add(33, al34);
        jal.add(34, al35);
        jal.add(35, al36);
        jal.add(36, al37);
        jal.add(37, al38);
        jal.add(38, al39);
        jal.add(39, al40);
        jal.add(40, al41);
        jal.add(41, al42);

    }

    private ArrayList getValueAllDate() {
        ArrayList getValue = new ArrayList();
        try {
            getValue.add(0, day1.getText());
            getValue.add(1, day2.getText());
            getValue.add(2, day3.getText());
            getValue.add(3, day4.getText());
            getValue.add(4, day5.getText());
            getValue.add(5, day6.getText());
            getValue.add(6, day7.getText());
            getValue.add(7, day8.getText());
            getValue.add(8, day9.getText());
            getValue.add(9, day10.getText());
            getValue.add(10, day11.getText());
            getValue.add(11, day12.getText());
            getValue.add(12, day13.getText());
            getValue.add(13, day14.getText());
            getValue.add(14, day15.getText());
            getValue.add(15, day16.getText());
            getValue.add(16, day17.getText());
            getValue.add(17, day18.getText());
            getValue.add(18, day19.getText());
            getValue.add(19, day20.getText());
            getValue.add(20, day21.getText());
            getValue.add(21, day22.getText());
            getValue.add(22, day23.getText());
            getValue.add(23, day24.getText());
            getValue.add(24, day25.getText());
            getValue.add(25, day26.getText());
            getValue.add(26, day27.getText());
            getValue.add(27, day28.getText());
            getValue.add(28, day29.getText());
            getValue.add(29, day30.getText());
            getValue.add(30, day31.getText());
            getValue.add(31, day32.getText());
            getValue.add(32, day33.getText());
            getValue.add(33, day34.getText());
            getValue.add(34, day35.getText());
            getValue.add(35, day36.getText());
            getValue.add(36, day37.getText());
            getValue.add(37, day38.getText());
            getValue.add(38, day39.getText());
            getValue.add(39, day40.getText());
            getValue.add(40, day41.getText());
            getValue.add(41, day42.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getValue;
    }

    private void setFontDefault() {
        Font sized10Font = fontDefault.deriveFont(11f);
        Font sized20Font = fontDefault.deriveFont(20f);
        Font sized23Font = fontDefault.deriveFont(1, 23f);

        mo.setFont(sized23Font);
        su.setFont(sized23Font);
        tu.setFont(sized23Font);
        we.setFont(sized23Font);
        th.setFont(sized23Font);
        fr.setFont(sized23Font);
        sa.setFont(sized23Font);

        day1.setFont(sized20Font);
        day2.setFont(sized20Font);
        day3.setFont(sized20Font);
        day4.setFont(sized20Font);
        day5.setFont(sized20Font);
        day6.setFont(sized20Font);
        day7.setFont(sized20Font);
        day8.setFont(sized20Font);
        day9.setFont(sized20Font);
        day10.setFont(sized20Font);
        day11.setFont(sized20Font);
        day12.setFont(sized20Font);
        day13.setFont(sized20Font);
        day14.setFont(sized20Font);
        day15.setFont(sized20Font);
        day16.setFont(sized20Font);
        day17.setFont(sized20Font);
        day18.setFont(sized20Font);
        day19.setFont(sized20Font);
        day20.setFont(sized20Font);
        day21.setFont(sized20Font);
        day22.setFont(sized20Font);
        day23.setFont(sized20Font);
        day24.setFont(sized20Font);
        day25.setFont(sized20Font);
        day26.setFont(sized20Font);
        day27.setFont(sized20Font);
        day28.setFont(sized20Font);
        day29.setFont(sized20Font);
        day30.setFont(sized20Font);
        day31.setFont(sized20Font);
        day32.setFont(sized20Font);
        day33.setFont(sized20Font);
        day34.setFont(sized20Font);
        day35.setFont(sized20Font);
        day36.setFont(sized20Font);
        day37.setFont(sized20Font);
        day38.setFont(sized20Font);
        day39.setFont(sized20Font);
        day40.setFont(sized20Font);
        day41.setFont(sized20Font);
        day42.setFont(sized20Font);

        al1.setFont(sized10Font);
        al2.setFont(sized10Font);
        al3.setFont(sized10Font);
        al4.setFont(sized10Font);
        al5.setFont(sized10Font);
        al6.setFont(sized10Font);
        al7.setFont(sized10Font);
        al8.setFont(sized10Font);
        al9.setFont(sized10Font);
        al10.setFont(sized10Font);
        al11.setFont(sized10Font);
        al12.setFont(sized10Font);
        al13.setFont(sized10Font);
        al14.setFont(sized10Font);
        al15.setFont(sized10Font);
        al16.setFont(sized10Font);
        al17.setFont(sized10Font);
        al18.setFont(sized10Font);
        al19.setFont(sized10Font);
        al20.setFont(sized10Font);
        al21.setFont(sized10Font);
        al22.setFont(sized10Font);
        al23.setFont(sized10Font);
        al24.setFont(sized10Font);
        al25.setFont(sized10Font);
        al26.setFont(sized10Font);
        al27.setFont(sized10Font);
        al28.setFont(sized10Font);
        al29.setFont(sized10Font);
        al30.setFont(sized10Font);
        al31.setFont(sized10Font);
        al32.setFont(sized10Font);
        al33.setFont(sized10Font);
        al34.setFont(sized10Font);
        al35.setFont(sized10Font);
        al36.setFont(sized10Font);
        al37.setFont(sized10Font);
        al38.setFont(sized10Font);
        al39.setFont(sized10Font);
        al40.setFont(sized10Font);
        al41.setFont(sized10Font);
        al42.setFont(sized10Font);

    }

    private void fixSize() {
        if (day36.getText() == null) {
             this.setSize(WIDTH_SIZE, HEIGHT_LESS_DATE);
        }
    }

    private String[] getDay() {
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd");
        SimpleDateFormat format1 = new SimpleDateFormat("MM");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy");
        SimpleDateFormat format3 = new SimpleDateFormat("HH");
        SimpleDateFormat format4 = new SimpleDateFormat("mm");
        SimpleDateFormat format5 = new SimpleDateFormat("ss");

        return new String[]{format.format(d.getTime()).toString(), format1.format(d.getTime()), format2.format(d.getTime()), format3.format(d.getTime()), format4.format(d.getTime()), format5.format(d.getTime())};
    }

    public void modellAllCombo() {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        DefaultComboBoxModel dcbm1 = new DefaultComboBoxModel();
        DefaultComboBoxModel dcbm2 = new DefaultComboBoxModel();

        for (int i = 1900; i < 9999; i++) {
            dcbm.addElement("" + i);
        }
        comboYear.setModel(dcbm);
        for (int i = 1; i < 32; i++) {
            dcbm1.addElement("" + i);
        }
        comboDate.setModel(dcbm1);
        for (int i = 1; i < 13; i++) {
            dcbm2.addElement("" + i);
        }
        comboMonth.setModel(dcbm2);
    }

    private void setNullAll() {
        day1.setText(null);
        day2.setText(null);
        day3.setText(null);
        day4.setText(null);
        day5.setText(null);
        day6.setText(null);
        day7.setText(null);
        day8.setText(null);
        day9.setText(null);
        day10.setText(null);
        day11.setText(null);
        day12.setText(null);
        day13.setText(null);
        day14.setText(null);
        day15.setText(null);
        day16.setText(null);
        day17.setText(null);
        day18.setText(null);
        day19.setText(null);
        day20.setText(null);
        day21.setText(null);
        day22.setText(null);
        day23.setText(null);
        day24.setText(null);
        day25.setText(null);
        day26.setText(null);
        day27.setText(null);
        day28.setText(null);
        day29.setText(null);
        day30.setText(null);
        day31.setText(null);
        day32.setText(null);
        day33.setText(null);
        day34.setText(null);
        day35.setText(null);
        day36.setText(null);
        day37.setText(null);
        day38.setText(null);
        day39.setText(null);
        day40.setText(null);
        day41.setText(null);
        day42.setText(null);

        al1.setText(null);
        al2.setText(null);
        al3.setText(null);
        al4.setText(null);
        al5.setText(null);
        al6.setText(null);
        al7.setText(null);
        al8.setText(null);
        al9.setText(null);
        al10.setText(null);
        al11.setText(null);
        al12.setText(null);
        al13.setText(null);
        al14.setText(null);
        al15.setText(null);
        al16.setText(null);
        al17.setText(null);
        al18.setText(null);
        al19.setText(null);
        al20.setText(null);
        al21.setText(null);
        al22.setText(null);
        al23.setText(null);
        al24.setText(null);
        al25.setText(null);
        al26.setText(null);
        al27.setText(null);
        al28.setText(null);
        al29.setText(null);
        al30.setText(null);
        al31.setText(null);
        al32.setText(null);
        al33.setText(null);
        al34.setText(null);
        al35.setText(null);
        al36.setText(null);
        al37.setText(null);
        al38.setText(null);
        al39.setText(null);
        al40.setText(null);
        al41.setText(null);
        al42.setText(null);
        jrand = new ArrayList<JLabel>();
        jrand.add(0, su);
        jrand.add(1, mo);
        jrand.add(2, tu);
        jrand.add(3, we);
        jrand.add(4, th);
        jrand.add(5, fr);
        jrand.add(6, sa);
    }

    private void popupMenu() {

        JPopupMenu menu = new JPopupMenu();
        JMenu jMenu = new JMenu("Loại lịch");
        jMenu.add(thang);
        jMenu.add(ngay);

        trolai.addActionListener(this);
        chuyenngay.addActionListener(this);
        thang.setSelected(true);
        ngay.addActionListener(this);
        thang.addActionListener(this);
        thoat.addActionListener(this);
        about.addActionListener(this);

        menu.add(jMenu);
        menu.add(chuyenngay);
        menu.add(trolai);
        menu.add(about);
        menu.add(thoat);

        for (int i = 0; i < jrand.size(); i++) {
            jrand.get(i).setComponentPopupMenu(menu);
        }
    }

    private void firstDay(int day, int month, int year) {
        String thu = Utils.Thu(day, month, year);
        int point = 0;
        // System.out.println(thu);
        switch (thu) {
            case "Thứ hai":
                day2.setText(day + "");
                point = 2;
                break;
            case "Thứ ba":
                day3.setText(day + "");
                point = 3;
                break;
            case "Thứ tư":
                day4.setText(day + "");
                point = 4;
                break;
            case "Thứ năm":
                day5.setText(day + "");
                point = 5;
                break;
            case "Thứ sáu":
                day6.setText(day + "");
                point = 6;
                break;
            case "Thứ bảy":
                day7.setText(day + "");
                point = 7;
                break;
            case "Chủ nhật":
                day1.setText(day + "");
                point = 1;
                break;
        }
        int songay = 0;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            songay = 31;
        } else if (month == 2 && year % 4 == 0) {
            songay = 29;
        } else if (month == 2 && year % 4 != 0) {
            songay = 28;
        } else {
            songay = 30;
        }

        if (point == 2) {
            setDay(7, songay, point, month, year);
        } else if (point == 3) {
            setDay(6, songay, point, month, year);
        } else if (point == 4) {
            setDay(5, songay, point, month, year);
        } else if (point == 5) {
            setDay(4, songay, point, month, year);
        } else if (point == 6) {
            setDay(3, songay, point, month, year);
        } else if (point == 7) {
            setDay(2, songay, point, month, year);
        } else if (point == 1) {
            setDay(8, songay, point, month, year);
        }

    }

    private String coverGetDayLunar(int day, int month, int year) {
        int a[] = LunarUtils.convertSolar2Lunar(day, month, year, 7);
        return a[0] != 1 ? a[0] + "" : a[0] + "/" + a[1];
    }

    private String getCanChiDay(int day, int month, int year) {
        long z = Utils.DemThangNgay(1, 1, 1900, day, month, year);
        String ngaycc = Utils.CanChiNgay(z);
        return ngaycc;
    }

    private void setDay(int sol, int maxday, int point, int month, int year) {
        int nday = sol;
        int point1 = point;
        int day7str = 1;
        switch (point1) {
            case 2:
                day2.setText(day7str + "");
                al2.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                day3.setText(day7str + "");
                al3.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                day4.setText(day7str + "");
                al4.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                day5.setText(day7str + "");
                al5.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                day6.setText(day7str + "");
                al6.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                day7.setText(day7str + "");
                al7.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                break;
            case 3:
                day3.setText(day7str + "");
                al3.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                day4.setText(day7str + "");
                al4.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                day5.setText(day7str + "");
                al5.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                day6.setText(day7str + "");
                al6.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                day7.setText(day7str + "");
                al7.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                break;
            case 4:
                day4.setText(day7str + "");
                al4.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                day5.setText(day7str + "");
                al5.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                day6.setText(day7str + "");
                al6.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                day7.setText(day7str + "");
                al7.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                break;
            case 5:
                day5.setText(day7str + "");
                al5.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                day6.setText(day7str + "");
                al6.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                day7.setText(day7str + "");
                al7.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));

                day7str++;
                break;
            case 6:
                day6.setText(day7str + "");
                al6.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                day7.setText(day7str + "");
                al7.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                break;
            case 7:
                day7.setText(day7str + "");
                al7.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                break;
            case 1:
                day1.setText(day7str + "");
                al1.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                day2.setText(day7str + "");
                al2.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                day3.setText(day7str + "");
                al3.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                day4.setText(day7str + "");
                al4.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                day5.setText(day7str + "");
                al5.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                day6.setText(day7str + "");
                al6.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                day7.setText(day7str + "");
                al7.setText(coverGetDayLunar(day7str, month, year) + " " + getCanChiDay(day7str, month, year));
                day7str++;
                break;

        }

        day8.setText(nday + "");
        al8.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        day9.setText(nday + "");
        al9.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        day10.setText(nday + "");
        al10.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        day11.setText(nday + "");
        al11.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        day12.setText(nday + "");
        al12.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        day13.setText(nday + "");
        al13.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        day14.setText(nday + "");
        al14.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        day15.setText(nday + "");
        al15.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        day16.setText(nday + "");
        al16.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        day17.setText(nday + "");
        al17.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        day18.setText(nday + "");
        al18.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        day19.setText(nday + "");
        al19.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        day20.setText(nday + "");
        al20.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        day21.setText(nday + "");
        al21.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        day22.setText(nday + "");
        al22.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        day23.setText(nday + "");
        al23.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        day24.setText(nday + "");
        al24.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        day25.setText(nday + "");
        al25.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        day26.setText(nday + "");
        al26.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        day27.setText(nday + "");
        al27.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        day28.setText(nday + "");
        al28.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        if (nday > maxday) {
            return;
        }
        day29.setText(nday + "");
        al29.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        if (nday > maxday) {
            return;
        }
        day30.setText(nday + "");
        al30.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        if (nday > maxday) {
            return;
        }
        day31.setText(nday + "");
        al31.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        if (nday > maxday) {
            return;
        }
        day32.setText(nday + "");
        al32.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        if (nday > maxday) {
            return;
        }
        day33.setText(nday + "");
        al33.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        if (nday > maxday) {
            return;
        }
        day34.setText(nday + "");
        al34.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        if (nday > maxday) {
            return;
        }
        day35.setText(nday + "");
        al35.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        if (nday > maxday) {
            return;
        }
        day36.setText(nday + "");
        al36.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        if (nday > maxday) {
            return;
        }
        day37.setText(nday + "");
        al37.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        if (nday > maxday) {
            return;
        }
        day38.setText(nday + "");
        al38.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        if (nday > maxday) {
            return;
        }
        day39.setText(nday + "");
        al39.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        if (nday > maxday) {
            return;
        }
        day40.setText(nday + "");
        al40.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        if (nday > maxday) {
            return;
        }
        day41.setText(nday + "");
        al41.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        if (nday > maxday) {
            return;
        }
        day42.setText(nday + "");
        al42.setText(coverGetDayLunar(nday, month, year) + " " + getCanChiDay(nday, month, year));
        nday++;
        if (nday > maxday) {
            return;
        }

    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        al23 = new javax.swing.JLabel();
        day23 = new javax.swing.JLabel();
        day2 = new javax.swing.JLabel();
        day30 = new javax.swing.JLabel();
        day37 = new javax.swing.JLabel();
        day9 = new javax.swing.JLabel();
        al37 = new javax.swing.JLabel();
        al9 = new javax.swing.JLabel();
        al2 = new javax.swing.JLabel();
        day16 = new javax.swing.JLabel();
        al16 = new javax.swing.JLabel();
        al30 = new javax.swing.JLabel();
        day29 = new javax.swing.JLabel();
        day36 = new javax.swing.JLabel();
        al36 = new javax.swing.JLabel();
        al29 = new javax.swing.JLabel();
        al22 = new javax.swing.JLabel();
        day22 = new javax.swing.JLabel();
        day15 = new javax.swing.JLabel();
        al15 = new javax.swing.JLabel();
        al8 = new javax.swing.JLabel();
        day8 = new javax.swing.JLabel();
        sa = new javax.swing.JLabel();
        al1 = new javax.swing.JLabel();
        day31 = new javax.swing.JLabel();
        day38 = new javax.swing.JLabel();
        al38 = new javax.swing.JLabel();
        al31 = new javax.swing.JLabel();
        al24 = new javax.swing.JLabel();
        day24 = new javax.swing.JLabel();
        day17 = new javax.swing.JLabel();
        al17 = new javax.swing.JLabel();
        al10 = new javax.swing.JLabel();
        day10 = new javax.swing.JLabel();
        day3 = new javax.swing.JLabel();
        al3 = new javax.swing.JLabel();
        day32 = new javax.swing.JLabel();
        day39 = new javax.swing.JLabel();
        al39 = new javax.swing.JLabel();
        al32 = new javax.swing.JLabel();
        al25 = new javax.swing.JLabel();
        day25 = new javax.swing.JLabel();
        day18 = new javax.swing.JLabel();
        al18 = new javax.swing.JLabel();
        al11 = new javax.swing.JLabel();
        day11 = new javax.swing.JLabel();
        day4 = new javax.swing.JLabel();
        al4 = new javax.swing.JLabel();
        day33 = new javax.swing.JLabel();
        day40 = new javax.swing.JLabel();
        al40 = new javax.swing.JLabel();
        al33 = new javax.swing.JLabel();
        al26 = new javax.swing.JLabel();
        day26 = new javax.swing.JLabel();
        day19 = new javax.swing.JLabel();
        al19 = new javax.swing.JLabel();
        al12 = new javax.swing.JLabel();
        day12 = new javax.swing.JLabel();
        day5 = new javax.swing.JLabel();
        al5 = new javax.swing.JLabel();
        day34 = new javax.swing.JLabel();
        day41 = new javax.swing.JLabel();
        al41 = new javax.swing.JLabel();
        al34 = new javax.swing.JLabel();
        al27 = new javax.swing.JLabel();
        day27 = new javax.swing.JLabel();
        day20 = new javax.swing.JLabel();
        al20 = new javax.swing.JLabel();
        al13 = new javax.swing.JLabel();
        day13 = new javax.swing.JLabel();
        day6 = new javax.swing.JLabel();
        al6 = new javax.swing.JLabel();
        day35 = new javax.swing.JLabel();
        day42 = new javax.swing.JLabel();
        al42 = new javax.swing.JLabel();
        al35 = new javax.swing.JLabel();
        al28 = new javax.swing.JLabel();
        day28 = new javax.swing.JLabel();
        day21 = new javax.swing.JLabel();
        al21 = new javax.swing.JLabel();
        al14 = new javax.swing.JLabel();
        day14 = new javax.swing.JLabel();
        day7 = new javax.swing.JLabel();
        al7 = new javax.swing.JLabel();
        comboDate = new javax.swing.JComboBox();
        comboMonth = new javax.swing.JComboBox();
        comboYear = new javax.swing.JComboBox();
        day1 = new javax.swing.JLabel();
        su = new javax.swing.JLabel();
        mo = new javax.swing.JLabel();
        tu = new javax.swing.JLabel();
        we = new javax.swing.JLabel();
        th = new javax.swing.JLabel();
        fr = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });
        getContentPane().setLayout(null);

        al23.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al23.setText("2");
        getContentPane().add(al23);
        al23.setBounds(90, 200, 90, 13);

        day23.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day23.setText("12");
        getContentPane().add(day23);
        day23.setBounds(90, 180, 90, 19);

        day2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day2.setText("4");
        getContentPane().add(day2);
        day2.setBounds(90, 30, 90, 19);

        day30.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day30.setText("18");
        getContentPane().add(day30);
        day30.setBounds(90, 230, 90, 19);

        day37.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day37.setText("23");
        getContentPane().add(day37);
        day37.setBounds(90, 280, 90, 19);

        day9.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day9.setText("5");
        day9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        day9.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        getContentPane().add(day9);
        day9.setBounds(90, 80, 90, 19);

        al37.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al37.setText("2");
        getContentPane().add(al37);
        al37.setBounds(90, 300, 90, 13);

        al9.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al9.setText("2");
        getContentPane().add(al9);
        al9.setBounds(90, 100, 90, 13);

        al2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al2.setText("2");
        getContentPane().add(al2);
        al2.setBounds(90, 50, 90, 13);

        day16.setBackground(new java.awt.Color(0, 0, 204));
        day16.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day16.setText("2");
        getContentPane().add(day16);
        day16.setBounds(90, 130, 90, 19);

        al16.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al16.setText("2");
        getContentPane().add(al16);
        al16.setBounds(90, 150, 90, 13);

        al30.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al30.setText("1");
        getContentPane().add(al30);
        al30.setBounds(90, 250, 90, 13);

        day29.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day29.setText("18");
        getContentPane().add(day29);
        day29.setBounds(0, 230, 90, 19);

        day36.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day36.setText("23");
        getContentPane().add(day36);
        day36.setBounds(0, 280, 90, 19);

        al36.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al36.setText("2");
        getContentPane().add(al36);
        al36.setBounds(0, 300, 90, 13);

        al29.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al29.setText("1");
        getContentPane().add(al29);
        al29.setBounds(0, 250, 90, 13);

        al22.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al22.setText("2");
        getContentPane().add(al22);
        al22.setBounds(0, 200, 90, 13);

        day22.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day22.setText("12");
        getContentPane().add(day22);
        day22.setBounds(0, 180, 90, 19);

        day15.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day15.setText("2");
        getContentPane().add(day15);
        day15.setBounds(0, 130, 90, 19);

        al15.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al15.setText("2");
        getContentPane().add(al15);
        al15.setBounds(0, 150, 90, 13);

        al8.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al8.setText("2");
        getContentPane().add(al8);
        al8.setBounds(0, 100, 90, 13);

        day8.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day8.setText("5");
        getContentPane().add(day8);
        day8.setBounds(0, 80, 90, 19);

        sa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        sa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sa.setText("Sat");
        sa.setBorder(new javax.swing.border.MatteBorder(null));
        getContentPane().add(sa);
        sa.setBounds(540, 0, 90, 24);

        al1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al1.setText("2");
        getContentPane().add(al1);
        al1.setBounds(0, 50, 90, 13);

        day31.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day31.setText("18");
        getContentPane().add(day31);
        day31.setBounds(180, 230, 90, 19);

        day38.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day38.setText("23");
        getContentPane().add(day38);
        day38.setBounds(180, 280, 90, 19);

        al38.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al38.setText("2");
        getContentPane().add(al38);
        al38.setBounds(180, 300, 90, 13);

        al31.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al31.setText("1");
        getContentPane().add(al31);
        al31.setBounds(180, 250, 90, 13);

        al24.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al24.setText("2");
        getContentPane().add(al24);
        al24.setBounds(180, 200, 90, 13);

        day24.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day24.setText("12");
        getContentPane().add(day24);
        day24.setBounds(180, 180, 90, 19);

        day17.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day17.setText("2");
        getContentPane().add(day17);
        day17.setBounds(180, 130, 90, 19);

        al17.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al17.setText("2");
        getContentPane().add(al17);
        al17.setBounds(180, 150, 90, 13);

        al10.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al10.setText("2");
        getContentPane().add(al10);
        al10.setBounds(180, 100, 90, 13);

        day10.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day10.setText("5");
        getContentPane().add(day10);
        day10.setBounds(180, 80, 90, 19);

        day3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day3.setText("4");
        getContentPane().add(day3);
        day3.setBounds(180, 30, 90, 19);

        al3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al3.setText("2");
        getContentPane().add(al3);
        al3.setBounds(180, 50, 90, 13);

        day32.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day32.setText("18");
        getContentPane().add(day32);
        day32.setBounds(270, 230, 90, 19);

        day39.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day39.setText("23");
        getContentPane().add(day39);
        day39.setBounds(270, 280, 90, 19);

        al39.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al39.setText("2");
        getContentPane().add(al39);
        al39.setBounds(270, 300, 90, 13);

        al32.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al32.setText("1");
        getContentPane().add(al32);
        al32.setBounds(270, 250, 90, 13);

        al25.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al25.setText("2");
        getContentPane().add(al25);
        al25.setBounds(270, 200, 90, 13);

        day25.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day25.setText("12");
        getContentPane().add(day25);
        day25.setBounds(270, 180, 90, 19);

        day18.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day18.setText("2");
        getContentPane().add(day18);
        day18.setBounds(270, 130, 90, 19);

        al18.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al18.setText("2");
        getContentPane().add(al18);
        al18.setBounds(270, 150, 90, 13);

        al11.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al11.setText("2");
        getContentPane().add(al11);
        al11.setBounds(270, 100, 90, 13);

        day11.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day11.setText("5");
        getContentPane().add(day11);
        day11.setBounds(270, 80, 90, 19);

        day4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day4.setText("4");
        getContentPane().add(day4);
        day4.setBounds(270, 30, 90, 19);

        al4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al4.setText("2");
        getContentPane().add(al4);
        al4.setBounds(270, 50, 90, 13);

        day33.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day33.setText("18");
        getContentPane().add(day33);
        day33.setBounds(360, 230, 90, 19);

        day40.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day40.setText("23");
        getContentPane().add(day40);
        day40.setBounds(360, 280, 90, 19);

        al40.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al40.setText("2");
        getContentPane().add(al40);
        al40.setBounds(360, 300, 90, 13);

        al33.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al33.setText("1");
        getContentPane().add(al33);
        al33.setBounds(360, 250, 90, 13);

        al26.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al26.setText("2");
        getContentPane().add(al26);
        al26.setBounds(360, 200, 90, 13);

        day26.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day26.setText("12");
        getContentPane().add(day26);
        day26.setBounds(360, 180, 90, 19);

        day19.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day19.setText("2");
        getContentPane().add(day19);
        day19.setBounds(360, 130, 90, 19);

        al19.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al19.setText("2");
        getContentPane().add(al19);
        al19.setBounds(360, 150, 90, 13);

        al12.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al12.setText("2");
        getContentPane().add(al12);
        al12.setBounds(360, 100, 90, 13);

        day12.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day12.setText("5");
        getContentPane().add(day12);
        day12.setBounds(360, 80, 90, 19);

        day5.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day5.setText("4");
        getContentPane().add(day5);
        day5.setBounds(360, 30, 90, 19);

        al5.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al5.setText("2");
        getContentPane().add(al5);
        al5.setBounds(360, 50, 90, 13);

        day34.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day34.setText("18");
        getContentPane().add(day34);
        day34.setBounds(450, 230, 90, 19);

        day41.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day41.setText("23");
        getContentPane().add(day41);
        day41.setBounds(450, 280, 90, 19);

        al41.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al41.setText("2");
        getContentPane().add(al41);
        al41.setBounds(450, 300, 90, 13);

        al34.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al34.setText("1");
        getContentPane().add(al34);
        al34.setBounds(450, 250, 90, 13);

        al27.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al27.setText("2");
        getContentPane().add(al27);
        al27.setBounds(450, 200, 90, 13);

        day27.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day27.setText("12");
        getContentPane().add(day27);
        day27.setBounds(450, 180, 90, 19);

        day20.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day20.setText("2");
        getContentPane().add(day20);
        day20.setBounds(450, 130, 90, 19);

        al20.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al20.setText("2");
        getContentPane().add(al20);
        al20.setBounds(450, 150, 90, 13);

        al13.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al13.setText("2");
        getContentPane().add(al13);
        al13.setBounds(450, 100, 90, 13);

        day13.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day13.setText("5");
        getContentPane().add(day13);
        day13.setBounds(450, 80, 90, 19);

        day6.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day6.setText("4");
        getContentPane().add(day6);
        day6.setBounds(450, 30, 90, 19);

        al6.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al6.setText("2");
        getContentPane().add(al6);
        al6.setBounds(450, 50, 90, 13);

        day35.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day35.setText("18");
        getContentPane().add(day35);
        day35.setBounds(540, 230, 90, 19);

        day42.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day42.setText("23");
        getContentPane().add(day42);
        day42.setBounds(540, 280, 90, 19);

        al42.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al42.setText("2");
        getContentPane().add(al42);
        al42.setBounds(540, 300, 90, 13);

        al35.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al35.setText("1");
        getContentPane().add(al35);
        al35.setBounds(540, 250, 90, 13);

        al28.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al28.setText("2");
        getContentPane().add(al28);
        al28.setBounds(540, 200, 90, 13);

        day28.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day28.setText("12");
        getContentPane().add(day28);
        day28.setBounds(540, 180, 90, 19);

        day21.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day21.setText("2");
        getContentPane().add(day21);
        day21.setBounds(540, 130, 90, 19);

        al21.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al21.setText("2");
        getContentPane().add(al21);
        al21.setBounds(540, 150, 90, 13);

        al14.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al14.setText("2");
        getContentPane().add(al14);
        al14.setBounds(540, 100, 90, 13);

        day14.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day14.setText("5");
        getContentPane().add(day14);
        day14.setBounds(540, 80, 90, 19);

        day7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        day7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day7.setText("4");
        getContentPane().add(day7);
        day7.setBounds(540, 30, 90, 19);

        al7.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        al7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        al7.setText("2");
        getContentPane().add(al7);
        al7.setBounds(540, 50, 90, 13);

        comboDate.setBackground(new java.awt.Color(255, 255, 255));
        comboDate.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboDate.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboDateItemStateChanged(evt);
            }
        });
        comboDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDateActionPerformed(evt);
            }
        });
        getContentPane().add(comboDate);
        comboDate.setBounds(210, 320, 90, 35);

        comboMonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboMonth.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboMonthItemStateChanged(evt);
            }
        });
        comboMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMonthActionPerformed(evt);
            }
        });
        getContentPane().add(comboMonth);
        comboMonth.setBounds(300, 320, 90, 35);

        comboYear.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboYear.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboYearItemStateChanged(evt);
            }
        });
        getContentPane().add(comboYear);
        comboYear.setBounds(390, 320, 90, 35);

        day1.setBackground(new java.awt.Color(51, 51, 255));
        day1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        day1.setForeground(new java.awt.Color(51, 0, 204));
        day1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        day1.setText("4");
        getContentPane().add(day1);
        day1.setBounds(0, 30, 90, 19);

        su.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        su.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        su.setText("Sun");
        su.setBorder(new javax.swing.border.MatteBorder(null));
        getContentPane().add(su);
        su.setBounds(0, 0, 90, 24);

        mo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        mo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mo.setText("Mon");
        mo.setBorder(new javax.swing.border.MatteBorder(null));
        getContentPane().add(mo);
        mo.setBounds(90, 0, 90, 24);

        tu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tu.setText("Tue");
        tu.setBorder(new javax.swing.border.MatteBorder(null));
        getContentPane().add(tu);
        tu.setBounds(180, 0, 90, 24);

        we.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        we.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        we.setText("Wed");
        we.setToolTipText("");
        we.setBorder(new javax.swing.border.MatteBorder(null));
        getContentPane().add(we);
        we.setBounds(270, 0, 90, 24);

        th.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        th.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        th.setText("Thu");
        th.setBorder(new javax.swing.border.MatteBorder(null));
        getContentPane().add(th);
        th.setBounds(360, 0, 90, 24);

        fr.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        fr.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fr.setText("Fri");
        fr.setBorder(new javax.swing.border.MatteBorder(null));
        getContentPane().add(fr);
        fr.setBounds(450, 0, 90, 24);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 21)); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 370, 490, 40);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void comboDateItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboDateItemStateChanged
        setChange();
    }//GEN-LAST:event_comboDateItemStateChanged

    private void comboMonthItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboMonthItemStateChanged
        setChange();
    }//GEN-LAST:event_comboMonthItemStateChanged

    private void comboYearItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboYearItemStateChanged
        setChange();
    }//GEN-LAST:event_comboYearItemStateChanged

    private void comboMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMonthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboMonthActionPerformed

    private void comboDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboDateActionPerformed

    private void setChange() {
        int day = Integer.parseInt((String) comboDate.getSelectedItem());
        int month = Integer.parseInt((String) comboMonth.getSelectedItem());
        int year = Integer.parseInt((String) comboYear.getSelectedItem());
        int check = 0;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                if (day > 31) {
                    check = 1;
                }
                break;
            case 2:
                if (year % 4 == 0) {
                    if (day > 29) {
                        check = 1;
                    }
                } else {
                    if (day > 28) {
                        check++;
                    }
                }
            default:
                if (day > 30) {
                    check++;
                }
        }

//        System.out.println(day + "--" + month + "--" + year);
        if (check == 0) {
            setNullAll();
            firstDay(1, month, year);
            setFontDefault();
            setColor();
            changeColorDateNow(day);
        } else {
            JOptionPane.showMessageDialog(null, "Xin lỗi tháng " + month + " năm " + year + " không có ngày " + day, null, JOptionPane.OK_CANCEL_OPTION);
        }
    }

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                CalendarMonth dialog = new CalendarMonth(new java.awt.Frame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel al1;
    private javax.swing.JLabel al10;
    private javax.swing.JLabel al11;
    private javax.swing.JLabel al12;
    private javax.swing.JLabel al13;
    private javax.swing.JLabel al14;
    private javax.swing.JLabel al15;
    private javax.swing.JLabel al16;
    private javax.swing.JLabel al17;
    private javax.swing.JLabel al18;
    private javax.swing.JLabel al19;
    private javax.swing.JLabel al2;
    private javax.swing.JLabel al20;
    private javax.swing.JLabel al21;
    private javax.swing.JLabel al22;
    private javax.swing.JLabel al23;
    private javax.swing.JLabel al24;
    private javax.swing.JLabel al25;
    private javax.swing.JLabel al26;
    private javax.swing.JLabel al27;
    private javax.swing.JLabel al28;
    private javax.swing.JLabel al29;
    private javax.swing.JLabel al3;
    private javax.swing.JLabel al30;
    private javax.swing.JLabel al31;
    private javax.swing.JLabel al32;
    private javax.swing.JLabel al33;
    private javax.swing.JLabel al34;
    private javax.swing.JLabel al35;
    private javax.swing.JLabel al36;
    private javax.swing.JLabel al37;
    private javax.swing.JLabel al38;
    private javax.swing.JLabel al39;
    private javax.swing.JLabel al4;
    private javax.swing.JLabel al40;
    private javax.swing.JLabel al41;
    private javax.swing.JLabel al42;
    private javax.swing.JLabel al5;
    private javax.swing.JLabel al6;
    private javax.swing.JLabel al7;
    private javax.swing.JLabel al8;
    private javax.swing.JLabel al9;
    private javax.swing.JComboBox comboDate;
    private javax.swing.JComboBox comboMonth;
    private javax.swing.JComboBox comboYear;
    private javax.swing.JLabel day1;
    private javax.swing.JLabel day10;
    private javax.swing.JLabel day11;
    private javax.swing.JLabel day12;
    private javax.swing.JLabel day13;
    private javax.swing.JLabel day14;
    private javax.swing.JLabel day15;
    private javax.swing.JLabel day16;
    private javax.swing.JLabel day17;
    private javax.swing.JLabel day18;
    private javax.swing.JLabel day19;
    private javax.swing.JLabel day2;
    private javax.swing.JLabel day20;
    private javax.swing.JLabel day21;
    private javax.swing.JLabel day22;
    private javax.swing.JLabel day23;
    private javax.swing.JLabel day24;
    private javax.swing.JLabel day25;
    private javax.swing.JLabel day26;
    private javax.swing.JLabel day27;
    private javax.swing.JLabel day28;
    private javax.swing.JLabel day29;
    private javax.swing.JLabel day3;
    private javax.swing.JLabel day30;
    private javax.swing.JLabel day31;
    private javax.swing.JLabel day32;
    private javax.swing.JLabel day33;
    private javax.swing.JLabel day34;
    private javax.swing.JLabel day35;
    private javax.swing.JLabel day36;
    private javax.swing.JLabel day37;
    private javax.swing.JLabel day38;
    private javax.swing.JLabel day39;
    private javax.swing.JLabel day4;
    private javax.swing.JLabel day40;
    private javax.swing.JLabel day41;
    private javax.swing.JLabel day42;
    private javax.swing.JLabel day5;
    private javax.swing.JLabel day6;
    private javax.swing.JLabel day7;
    private javax.swing.JLabel day8;
    private javax.swing.JLabel day9;
    private javax.swing.JLabel fr;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel mo;
    private javax.swing.JLabel sa;
    private javax.swing.JLabel su;
    private javax.swing.JLabel th;
    private javax.swing.JLabel tu;
    private javax.swing.JLabel we;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem jtm = (JMenuItem) e.getSource();
        if (jtm == thoat) {
            System.exit(0);
        } else if (jtm == thang) {
            thang.setSelected(true);
        } else if (jtm == ngay) {
            this.setVisible(false);
            Utils.writeOutput("day");
            wgcalendar = new CalendarWiget(null, true);
            wgcalendar.show(true);
        } else if (jtm == chuyenngay) {
            comboDate.setVisible(true);
            comboMonth.setVisible(true);
            comboYear.setVisible(true);

            chuyenngay.setVisible(false);
            trolai.setVisible(true);

            //timer.stop();
            this.setSize(WIDTH_SIZE, HEIGHT_FIND_DATE);
        } else if (jtm == trolai) {
            comboDate.setVisible(true);
            comboMonth.setVisible(true);
            comboYear.setVisible(true);

            chuyenngay.setVisible(true);
            trolai.setVisible(false);

            this.setSize(WIDTH_SIZE, HEIGHT_SIZE);
            fixSize();
            //changeDate();
        } else if (jtm == about) {
            JOptionPane.showOptionDialog(this, Utils.about(), "Thông tin", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
        }
    }
}
