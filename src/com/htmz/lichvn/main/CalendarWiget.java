/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htmz.lichvn.main;

import com.htmz.lichvn.formload.constants.IConstanst;
import com.htmz.lichvn.utils.CopyFile;
import com.htmz.lichvn.utils.Sc;
import com.htmz.lichvn.model.DateInfo;
import com.htmz.lichvn.utils.LunarUtils;
import com.htmz.lichvn.utils.SystemTrayUtils;
import com.htmz.lichvn.utils.Utils;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author HTMZ
 */
public class CalendarWiget extends javax.swing.JDialog implements ActionListener {

    private String thu, ngaycc, thangcc, namcc, gio, tiet;
    int ngay, thang, nam, ngal, thal, thnhu, namal, kt = 0, navigate = 0, alwayTop = 0;
    double width, height;
    private JPopupMenu popupMenu;
    private JMenuItem startup = new JMenuItem("Khởi động cùng Windows"), duongsangam = new JMenuItem("Ngày dương"), amsangduong = new JMenuItem("Ngày âm"), about = new JMenuItem("Thông tin"), ovacity = new JMenuItem("Trong suốt"), rezable = new JMenuItem("Di chuyển"), exit = new JMenuItem("Thoát"), changebackg = new JMenuItem("Đổi nền");
    private JMenu chuyenden = new JMenu("Chuyển đến"), lich = new JMenu("Loại lịch");
    private JCheckBoxMenuItem day = new JCheckBoxMenuItem("Ngày"), month = new JCheckBoxMenuItem("Tháng"), dieuhuong = new JCheckBoxMenuItem("Điều hướng");
    private CalendarMonth month_calendar;
    //private Thread ths;
    private Font fontDefault;

    public CalendarWiget(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        try {
            InputStream is = this.getClass().getResourceAsStream("/fonts/Quicksand-Medium.ttf");
            this.fontDefault = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String type = Utils.readUTF8(Utils.getPathConfig(IConstanst.NAME_OF_CONFIG_CALENDAR_TYPE));
        System.out.println("Type of canlendar initial: " + type);
        if (type.equals("day")) {
            this.getContentPane().setBackground(Color.WHITE);
            this.getRootPane().setBorder(BorderFactory.createLineBorder(Color.RED));
            this.setBackground(Color.WHITE);
            this.setUndecorated(true);
            initComponents();
            String path = null;
            try {

                try {
                    path = Utils.readUTF8(this.getClass().getResourceAsStream("/data/config/config.txt"));
                } catch (Exception e) {
                    path = null;
                }
                BufferedImage bfi;
                try {
                    bfi = ImageIO.read(new File(path));
                    ImageIcon imc = new ImageIcon(bfi.getScaledInstance(335, 400, bfi.SCALE_SMOOTH));
                    jLabel18.setIcon(imc);
                } catch (IOException ex) {
                    bfi = ImageIO.read(this.getClass().getResourceAsStream("/images/background/root.jpg"));
                    ImageIcon imc = new ImageIcon(bfi.getScaledInstance(335, 400, bfi.SCALE_SMOOTH));
                    jLabel18.setIcon(imc); // NOI18N
                }

                this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                getValueDate();
                getWigetCalendar();
                timerTng();
                setPropeties();
                popMenu();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi : " + e);
            }
        } else if (type.equals("month")) {
            month_calendar = new CalendarMonth(null, rootPaneCheckingEnabled);
            month_calendar.show();
        }
        SystemTrayUtils.initialSystemTray(Utils.trayIconDefault(), this);
        //jLabel8.setBorder(BorderFactory.createTitledBorder("Multi-Line HTML"));

    }

    public int alwayTop() {
        return this.alwayTop;
    }

    public void changeAlwaysTop() {
        if (alwayTop == 0) {
            alwayTop = 1;
        } else {
            alwayTop = 0;
        }
    }

    public void setPropeties() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(this.getClass().getResourceAsStream("/images/other/1.png"));
        } catch (IOException ex) {
            Logger.getLogger(CalendarWiget.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setIconImage(image);
        getWigetCalendar();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.getWidth();
        height = screenSize.getHeight();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(430, 430);
        this.setLocation((int) (width - this.getWidth() - 0.9), 0);
        jLabel20.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jLabel19.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        final String monthStatus = Utils.lunarTotalDayOfMonth(ngay, thang, nam, 7.0) == 30 ? "Đ" : "T";

        if (navigate == 0) {
            jLabel20.setVisible(false);
            jLabel19.setVisible(false);
        } else {
            jLabel20.setVisible(true);
            jLabel19.setVisible(true);
        }

        jLabel8.setText(null);
        jLabel9.setText(null);
        jLabel1.setText(String.valueOf(ngay));
        jLabel2.setText(Utils.toUpperCase("Tháng " + Utils.convertNumberToString(thang, true)));
        jLabel6.setText(Utils.toUpperCase(engM(thang)));
        jLabel7.setText(String.valueOf(nam));
        jLabel3.setText(Utils.toUpperCase(thu));
        jLabel4.setText(Utils.toUpperCase(engTh(thu)));
        jLabel5.setText("Giờ " + gio);
        jLabel10.setText("Ngày " + ngaycc);
        jLabel11.setText("Tháng " + thangcc);
        jLabel12.setText("Năm " + namcc);
        jLabel13.setText(Utils.toUpperCase("Tháng " + Utils.convertNumberToString(thal, false)) + " (" + monthStatus + ")");
        jLabel14.setText("Tiết " + tiet);
        if (thnhu != 0) {
            jLabel17.setText("Nhuần Tháng " + thnhu);
            jLabel17.setVisible(true);
        } else {
            jLabel17.setVisible(false);
        }
        jLabel15.setText(String.valueOf(ngal));

        Font sized120Font = fontDefault.deriveFont(120f);
        Font sized12Font = fontDefault.deriveFont(12f);
        Font sized19Font = fontDefault.deriveFont(19f);
        Font sized15Font = fontDefault.deriveFont(15f);
        Font sized50Font = fontDefault.deriveFont(50f);
        Font sized14Font = fontDefault.deriveFont(14f);
        Font sized16Font = fontDefault.deriveFont(16f);

        jLabel1.setFont(sized120Font);

        jLabel2.setFont(sized14Font);
        jLabel6.setFont(sized14Font);
        jLabel7.setFont(sized16Font);

        jLabel15.setFont(sized50Font);
        jLabel3.setFont(sized19Font);
        jLabel4.setFont(sized19Font);
        jLabel9.setFont(sized15Font);
        jLabel10.setFont(sized12Font);
        jLabel11.setFont(sized12Font);
        jLabel12.setFont(sized12Font);
        jLabel13.setFont(sized12Font);
        jLabel14.setFont(sized12Font);
        jLabel5.setFont(sized12Font);
        jLabel17.setFont(sized12Font);
        jLabel8.setFont(sized12Font);
        jLabel21.setFont(sized12Font);

        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        setColor();
        xuLyHinh(gio, ngaycc, thangcc, namcc);
        docFileAL(ngal, thal);
        docFileDL(ngay, thang);
        defaultFile(ngauNhien(0, 100));
        if (ngal == 1 & thal == 1) {
            //nhapNhay();
        } else if ((ngal != 1 && ngal != 2 && ngal != 3) && thal == 1) {
            try {
                //ths.stop();
            } catch (Exception e) {
                System.out.println("Chưa start");
            }
        }
        timeUpdate();
    }

    public void getValueDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormatd = new SimpleDateFormat("dd");
        SimpleDateFormat simpleDateFormatm = new SimpleDateFormat("MM");
        SimpleDateFormat simpleDateFormaty = new SimpleDateFormat("yyyy");
        ngay = Integer.parseInt(simpleDateFormatd.format(date.getTime()));
        thang = Integer.parseInt(simpleDateFormatm.format(date.getTime()));
        nam = Integer.parseInt(simpleDateFormaty.format(date.getTime()));
    }

    public void getWigetCalendar() {
        thu = Utils.Thu(ngay, thang, nam);
        long z = Utils.DemThangNgay(1, 1, 1900, ngay, thang, nam);
        ngaycc = Utils.CanChiNgay(z);
        int[] a = LunarUtils.convertSolar2Lunar(ngay, thang, nam, 7);
        ngal = a[0];
        thal = a[1];
        namal = a[2];
        thangcc = Utils.SetCanT(namal, thal) + " " + Utils.SetChiT(thal);
        namcc = Utils.SetCanY(namal) + " " + Utils.SetChiY(namal);
        gio = Utils.SetCanG(ngay, thang, nam) + " " + Utils.SetChiG(0);
        tiet = Utils.Settiet(ngay, thang);
        thnhu = Utils.setThangNhuanAl(namal);

        DateInfo dateInfo = Utils.getDateInfo(new Date(nam, thang - 1, ngay));
        jLabel21.setText("Tuần " + Utils.coverZeroSemantic(dateInfo.getWeekOfYear()) + " | " + Utils.coverZeroSemantic(dateInfo.getDayOfYear()) + " Ngày");
    }

    public void setOvacityFrame(float s) {
        this.setOpacity(s);
    }

    public void setDatePrev() {
        if (ngay == 1) {
            if (thang == 2 || thang == 4 || thang == 6 || thang == 9 || thang == 11) {
                System.out.println("thang 2");
                thang--;
                ngay = 31;
                return;
            }
            if (thang == 5 || thang == 7 || thang == 8 || thang == 10 || thang == 12) {
                thang--;
                ngay = 30;
                return;
            } else if (thang == 3) {
                thang--;
                if (nam % 4 == 0) {
                    ngay = 29;
                    return;
                } else {
                    ngay = 28;
                    return;
                }
            } else if (thang == 1) {
                nam--;
                thang = 12;
                ngay = 31;
            }
        } else {
            ngay--;
        }
        System.out.println(ngay + "--" + thang + "--" + nam);
    }

    public void setDateNext() {
        if (thang == 4 || thang == 6 || thang == 9 || thang == 11) {
            if (thang <= 12 && thang > 1) {
            } else {
                thang = 1;
            }
            if (ngay < 30) {
                ngay++;
            } else {
                thang++;
                ngay = 1;
            }
        } else if (thang == 2 && nam % 4 == 0) {
            if (thang <= 12 && thang > 1) {
            } else {
                thang = 1;
            }
            if (ngay < 29) {
                ngay++;
            } else {
                thang++;
                ngay = 1;
            }
        } else if (thang == 2 && nam % 4 != 0) {
            if (thang <= 12 && thang > 1) {
            } else {
                thang = 1;
            }
            if (ngay < 28) {
                ngay++;
            } else {
                thang++;
                ngay = 1;
            }

        } else if (thang == 12) {
            if (ngay < 31) {
                ngay++;
            } else {
                nam++;
                thang = 1;
                ngay = 1;
            }
        } else {

            if (ngay < 31) {
                ngay++;
            } else {
                thang++;
                ngay = 1;
            }
            if (thang <= 12 && thang > 1) {
            } else {
                thang = 1;
            }
        }
        System.out.println(ngay + "--" + thang + "--" + nam);
    }

    public void popMenu() {
        popupMenu = new JPopupMenu();
        chuyenden.add(duongsangam);
        chuyenden.add(amsangduong);
        lich.add(day);
        lich.add(month);
        day.setSelected(true);
        dieuhuong.setSelected(false);

        ovacity.addActionListener(this);
        rezable.addActionListener(this);
        exit.addActionListener(this);
        changebackg.addActionListener(this);
        duongsangam.addActionListener(this);
        amsangduong.addActionListener(this);
        startup.addActionListener(this);
        month.addActionListener(this);
        day.addActionListener(this);
        dieuhuong.addActionListener(this);
        about.addActionListener(this);

        popupMenu.add(lich);
        popupMenu.add(ovacity);
        popupMenu.add(rezable);
        popupMenu.add(dieuhuong);
        popupMenu.add(changebackg);
        popupMenu.add(chuyenden);
        popupMenu.add(startup);
        popupMenu.add(about);
        popupMenu.add(exit);
        jLabel1.setComponentPopupMenu(popupMenu);
    }

    private int ngauNhien(int s1, int s2) {
        Random rds = new Random();
        int rd = s1 + rds.nextInt(s2 - s1);
        System.out.println("Line: " + rd);
        return rd;
    }

    private void setColor() {
        jLabel1.setForeground(Color.BLUE);
        jLabel15.setForeground(new Color(0, 0, 205));
        jLabel9.setForeground(new Color(255, 51, 255));
        jLabel8.setForeground(new Color(139, 0, 255));
        jLabel3.setForeground(Color.GREEN);
        jLabel4.setForeground(Color.GREEN);
        jLabel5.setForeground(Color.RED);
        jLabel10.setForeground(Color.RED);
        jLabel11.setForeground(Color.RED);
        jLabel12.setForeground(Color.RED);
        jLabel13.setForeground(Color.RED);
        jLabel14.setForeground(Color.RED);
        jLabel16.setForeground(Color.RED);
        jLabel17.setForeground(Color.RED);
        jLabel21.setForeground(Color.RED);
        jLabel2.setForeground(new Color(139, 0, 139));
        jLabel6.setForeground(new Color(139, 0, 139));
        jLabel7.setForeground(new Color(198, 0, 0));
    }

    private void defaultFile(int so) {
        if (jLabel8.getText() == null) {
            try {
                InputStreamReader isr = new InputStreamReader(this.getClass().getResourceAsStream("/data/files/3.txt"), "utf-8");
                BufferedReader bfr = new BufferedReader(isr);
                String h = null;
                int line = 0;
                while ((h = bfr.readLine()) != null) {
                    line++;
                    if (line == so) {
                        String arLine[] = h.split(":");
                        System.out.println(arLine[0] + " --- > " + arLine[1]);
                        labelDescriptionSet(arLine[0], arLine[1]);
                    }
                }
            } catch (Exception ex) {

            }
        }
    }

    private void labelDescriptionSet(String source, String author) {
        if ((source.trim().length()) > 60) {
            // JOptionPane.showMessageDialog(null,"Lon hon 54");
            String text = source;
            int chec = 0;
            for (int i = 60; i < text.length(); i++) {
                if (text.charAt(i) == 32) {
                    chec = i;
                    break;
                }
            }
            //System.out.println("Check : "+chec);
            String lengnew = text.substring(chec, text.length());
            System.out.println(text.substring(0, chec) + "---" + lengnew);
            jLabel8.setText("<html><p align='center'>" + text.substring(0, chec) + "<br/>" + lengnew + "</p><p  align='center'><b>(" + author + ")</b></p></html>");
        } else {
            jLabel8.setText("<html><p align='center'>" + source + "</p><p  align='center'><b>(" + author + ")</b></p></html>");
        }
    }

    private void docFileAL(int n, int ts) {
        try {
            InputStreamReader isr = new InputStreamReader(this.getClass().getResourceAsStream("/data/files/1.txt"), "utf-8");
            BufferedReader bfr = new BufferedReader(isr);
            String h = null;
            String ngt = n + "/" + ts;
            while ((h = bfr.readLine()) != null) {
                StringTokenizer skz = new StringTokenizer(h, ":");
                Vector c = new Vector();
                while (skz.hasMoreTokens()) {
                    c.add(skz.nextToken().trim());
                }
                for (int i = 0; i < c.size(); i++) {
                    String kq = (String) c.get(i);
                    if (kq.equals(ngt)) {
                        String kq1 = (String) c.get(i + 1);
                        jLabel8.setText(kq1);
                    }
                }
            }
        } catch (Exception ex) {

        }

    }

    private void docFileDL(int nd, int tsd) {
        if (jLabel8.getText() == null) {

            try {
                InputStreamReader isr = new InputStreamReader(this.getClass().getResourceAsStream("/data/files/2.txt"), "utf-8");
                BufferedReader bfr = new BufferedReader(isr);
                String h = null;
                String ngt = nd + "/" + tsd;
                while ((h = bfr.readLine()) != null) {
                    StringTokenizer skz = new StringTokenizer(h, ":");
                    Vector c = new Vector();
                    while (skz.hasMoreTokens()) {
                        c.add(skz.nextToken().trim());
                    }
                    for (int i = 0; i < c.size(); i++) {
                        String kq = (String) c.get(i);
                        if (kq.equals(ngt)) {
                            String kq1 = (String) c.get(i + 1);
                            jLabel8.setText(kq1);
                        }
                    }
                }
            } catch (Exception ex) {

            }
        }

    }

    private void timerTng() {

        Timer tm = new Timer(60000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                timeUpdate();
            }
        });
        tm.start();
    }

    private void timeUpdate() {
        int times = 0, timem = 0, timeh = 0;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm aa");
        SimpleDateFormat sdfS = new SimpleDateFormat("ss");
        SimpleDateFormat sdfM = new SimpleDateFormat("mm");
        SimpleDateFormat sdfH = new SimpleDateFormat("HH");

        jLabel9.setText(sdf.format(date.getTime()));

        times = Integer.parseInt(sdfS.format(date.getTime()));
        timem = Integer.parseInt(sdfM.format(date.getTime()));
        timeh = Integer.parseInt(sdfH.format(date.getTime()));

        if (timem == 0 && timeh == 0 && times == 1) {
            getValueDate();
            getWigetCalendar();
            setPropeties();
        }
    }

    private String engM(int thas) {
        String kq = null;
        switch (thas) {
            case 1:
                kq = "January";
                break;
            case 2:
                kq = "February";
                break;
            case 3:
                kq = "March";
                break;
            case 4:
                kq = "April";
                break;
            case 5:
                kq = "May";
                break;
            case 6:
                kq = "June";
                break;
            case 7:
                kq = "July";
                break;
            case 8:
                kq = "August";
                break;
            case 9:
                kq = "September";
                break;

            case 10:
                kq = "October";
                break;
            case 11:
                kq = "November";
                break;
            case 12:
                kq = "December";
                break;
        }
        return kq;
    }

    private String engTh(String ths) {
        String kq = null;
        switch (ths.trim().toLowerCase()) {
            case "thứ hai":
                kq = "Monday";
                break;
            case "thứ ba":
                kq = "Tuesday";
                break;
            case "thứ tư":
                kq = "Wednesday";
                break;
            case "thứ năm":
                kq = "Thursday";
                break;
            case "thứ sáu":
                kq = "Friday";
                break;
            case "thứ bảy":
                kq = "Saturday";
                break;
            case "chủ nhật":
                kq = "Sunday";
                break;
        }

        return kq;
    }

    public void xuLyHinh(String gioAm, String ngaas, String thangAm, String namAm) {
        StringTokenizer stk = new StringTokenizer(ngaas, " ");
        String ngal = ngaas.split(" ")[1];
        String nameAl = namAm.split(" ")[1];
        String thangAl = thangAm.split(" ")[1];
        String gioAl = gioAm.split(" ")[1];

        System.out.println("hours: " + gioAl + " - day: " + ngal + " - month:" + thangAl + " - year:" + nameAl);

        BufferedImage bfi = null, bfiYY = null, bfiMM = null, bfiDD = null, bfiHr = null;
        ImageIcon im = null, imYY = null, imMM = null, imDD = null, imHr = null;
        String url = null, urlYY = null, urlMM = null, urlDD = null, urlHr = null;

        switch (nameAl.trim().toLowerCase()) {
            case "tý":
                url = "1.png";
                break;
            case "sửu":
                url = "2.png";
                break;
            case "dần":
                url = "3.png";
                break;
            case "mão":
                url = "4.png";
                break;
            case "thìn":
                url = "5.png";
                break;
            case "tỵ":
                url = "6.png";
                break;
            case "ngọ":
                url = "7.png";
                break;
            case "mùi":
                url = "8.png";
                break;
            case "thân":
                url = "9.png";
                break;
            case "dậu":
                url = "10.png";
                break;
            case "tuất":
                url = "11.png";
                break;
            case "hợi":
                url = "12.png";
                break;
        }

        urlMM = Utils.headOfAnimal(thangAl);
        urlYY = Utils.headOfAnimal(nameAl);
        urlDD = Utils.headOfAnimal(ngal);
        urlHr = Utils.headOfAnimal(gioAl);

        try {
            bfi = ImageIO.read(this.getClass().getResourceAsStream("/images/animals/" + url));
            im = new ImageIcon(bfi.getScaledInstance(76, 48, bfi.SCALE_SMOOTH));

            bfiYY = ImageIO.read(this.getClass().getResourceAsStream("/images/animals/heads/" + urlYY));
            imYY = new ImageIcon(bfiYY.getScaledInstance(24, 24, bfi.SCALE_SMOOTH));

            bfiMM = ImageIO.read(this.getClass().getResourceAsStream("/images/animals/heads/" + urlMM));
            imMM = new ImageIcon(bfiMM.getScaledInstance(24, 24, bfi.SCALE_SMOOTH));

            bfiDD = ImageIO.read(this.getClass().getResourceAsStream("/images/animals/heads/" + urlDD));
            imDD = new ImageIcon(bfiDD.getScaledInstance(24, 24, bfi.SCALE_SMOOTH));

            bfiHr = ImageIO.read(this.getClass().getResourceAsStream("/images/animals/heads/" + urlHr));
            imHr = new ImageIcon(bfiHr.getScaledInstance(24, 24, bfi.SCALE_SMOOTH));

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Không tìm được ", "Lỗi 3 : Không tìm thấy File !", 0);
        }
        jLabel23.setSize(24, 24);
        jLabel24.setSize(24, 24);
        jLabel25.setSize(24, 24);
        jLabel22.setSize(24, 24);

        jLabel16.setIcon(im);
        jLabel23.setIcon(imYY);
        jLabel24.setIcon(imMM);
        jLabel25.setIcon(imDD);
        jLabel22.setIcon(imHr);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel18 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();

        jLabel18.setBackground(new java.awt.Color(0, 255, 204));
        jLabel18.setForeground(new java.awt.Color(51, 255, 255));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(null);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("jLabel13");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(0, 260, 430, 15);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("jLabel14");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(290, 320, 140, 15);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("vi_month");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(30, 10, 130, 20);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("year");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(0, 10, 430, 20);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("en_month");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(310, 10, 90, 20);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("main_day");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 40, 430, 100);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("jLabel3");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(40, 250, 110, 30);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("jLabel5");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(50, 380, 140, 20);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("jLabel10");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(50, 350, 130, 15);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("jLabel11");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(50, 320, 130, 15);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("jLabel12");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(50, 290, 130, 15);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("jLabel17");
        getContentPane().add(jLabel17);
        jLabel17.setBounds(290, 350, 140, 15);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("jLabel15");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(160, 290, 110, 90);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("jLabel9");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(290, 380, 120, 15);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("description");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(10, 143, 410, 60);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("jLabel4");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(290, 250, 140, 30);
        getContentPane().add(jLabel16);
        jLabel16.setBounds(180, 210, 100, 48);

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/other/right-arrow.png"))); // NOI18N
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel19);
        jLabel19.setBounds(400, 220, 30, 30);

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/other/left-arrow.png"))); // NOI18N
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel20);
        jLabel20.setBounds(0, 220, 40, 30);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText("jLabel21");
        getContentPane().add(jLabel21);
        jLabel21.setBounds(290, 290, 140, 15);

        jLabel22.setText("jLabel22");
        jLabel22.setPreferredSize(new java.awt.Dimension(32, 32));
        getContentPane().add(jLabel22);
        jLabel22.setBounds(20, 380, 20, 15);

        jLabel23.setText("jLabel22");
        jLabel23.setPreferredSize(new java.awt.Dimension(32, 32));
        getContentPane().add(jLabel23);
        jLabel23.setBounds(20, 290, 20, 15);

        jLabel24.setText("jLabel22");
        jLabel24.setPreferredSize(new java.awt.Dimension(32, 32));
        getContentPane().add(jLabel24);
        jLabel24.setBounds(20, 320, 20, 15);

        jLabel25.setText("jLabel22");
        jLabel25.setPreferredSize(new java.awt.Dimension(32, 32));
        getContentPane().add(jLabel25);
        jLabel25.setBounds(20, 350, 20, 15);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        setDatePrev();
        getWigetCalendar();
        timerTng();
        setPropeties();
        popMenu();
    }//GEN-LAST:event_jLabel20MouseClicked

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        setDateNext();
        getWigetCalendar();
        timerTng();
        setPropeties();
        popMenu();
    }//GEN-LAST:event_jLabel19MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
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
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CalendarWiget dialog = new CalendarWiget(new javax.swing.JFrame(), true);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem jt = (JMenuItem) e.getSource();
        if (jt == ovacity) {
            int ovacity = 100;
            String string = null;
            do {
                try {
                    string = JOptionPane.showInputDialog(null, "Nhập giá trị trong suốt >=5 n <=100", "Độ trong suốt", JOptionPane.WARNING_MESSAGE);
                    ovacity = Integer.parseInt(string);
                } catch (Exception ea) {
                    try {
                        if (!string.trim().equals("")) {
                            JOptionPane.showMessageDialog(null, "Bạn vui lòng nhập số !", "Sai !", JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (Exception s) {
                        return;
                    }
                }
            } while (ovacity <= 0 || ovacity > 100);
            setOvacityFrame(((float) ovacity / 100));
        } else if (jt == rezable) {
            if (kt % 2 == 0) {
                setOvacityFrame(1);
                this.dispose();
                this.setUndecorated(false);
                ovacity.setEnabled(false);
                this.setResizable(false);
                this.setSize(430, 430);
                kt++;
                rezable = new JMenuItem("Cố định");
                popMenu();
                this.setVisible(true);

            } else {
                setOvacityFrame(((float) 1));
                this.dispose();
                this.setUndecorated(true);
                ovacity.setEnabled(true);
                this.setSize(430, 430);
                kt++;
                rezable = new JMenuItem("Di chuyển");
                popMenu();
                this.setVisible(true);

            }
        } else if (jt == exit) {
            System.exit(0);
        } else if (jt == changebackg) {
            File file;
            try {
                JOptionPane.showMessageDialog(null, "Bạn chọn file phải có kích cở >400x410 để không vỡ ảnh file JPG");
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileFilter() {

                    @Override
                    public boolean accept(File f) {
                        return f.getName().toLowerCase().endsWith(".jpg") || f.isDirectory();
                    }

                    @Override
                    public String getDescription() {
                        return "*.JPG";
                    }
                });
                chooser.showOpenDialog(this);
                file = chooser.getSelectedFile();
                BufferedImage bfi = ImageIO.read(file);
                ImageIcon imc = new ImageIcon(bfi.getScaledInstance(400, 410, bfi.SCALE_SMOOTH));
                jLabel18.setIcon(imc);
            } catch (Exception ez) {
                JOptionPane.showMessageDialog(null, "Không chọn được file hoặc kích cỡ,tên file không phù hợp !", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                Utils.writeOutput(file.getAbsolutePath());
            } catch (Exception ek) {
                ek.printStackTrace();
            }

        } else if (jt == amsangduong) {
            String ms = JOptionPane.showInputDialog(null, "Nhập ngày âm (dd/mm/yyyy)", "Chú ý nhập", JOptionPane.WARNING_MESSAGE);
            try {
                StringTokenizer tokenizer = new StringTokenizer(ms, "/");
                ArrayList arrayList = new ArrayList();
                while (tokenizer.hasMoreElements()) {
                    arrayList.add(tokenizer.nextToken());
                }
                ngal = Integer.parseInt(arrayList.get(0).toString());
                thal = Integer.parseInt(arrayList.get(1).toString());
                namal = Integer.parseInt(arrayList.get(2).toString());
                int a[] = LunarUtils.convertLunar2Solar(ngal, thal, namal, 0, 7);
                ngay = a[0];
                thang = a[1];
                nam = a[2];
                getWigetCalendar();
                setPropeties();
                popMenu();
            } catch (Exception ze) {

            }
        } else if (jt == duongsangam) {
            String ms = JOptionPane.showInputDialog(null, "Nhập ngày âm (dd/mm/yyyy)", "Chú ý nhập", JOptionPane.WARNING_MESSAGE);
            try {
                StringTokenizer tokenizer = new StringTokenizer(ms, "/");
                ArrayList arrayList = new ArrayList();
                while (tokenizer.hasMoreElements()) {
                    arrayList.add(tokenizer.nextToken());
                }
                ngay = Integer.parseInt(arrayList.get(0).toString());
                thang = Integer.parseInt(arrayList.get(1).toString());
                nam = Integer.parseInt(arrayList.get(2).toString());
                int a[] = LunarUtils.convertSolar2Lunar(ngay, thang, nam, 7);
                ngal = a[0];
                thal = a[1];
                namal = a[2];
                getWigetCalendar();
                setPropeties();
                popMenu();
            } catch (Exception ze) {
            }
        } else if (jt == startup) {

            Sc sc = new Sc();
            sc.createShortcut();

            CopyFile copyFile = new CopyFile();
            try {
                copyFile.FileCopy("src/widget.lnk", "C:/Users/All Users/Microsoft/Windows/Start Menu/Programs/Startup/widget.lnk");
            } catch (Exception ez) {
                try {
                    copyFile.FileCopy("src/widget.lnk", "C:/Documents and Settings/All Users/Start Menu/Programs/Startup/widget.lnk");
                } catch (Exception ce) {
                    JOptionPane.showMessageDialog(null, "Xin lỗi ! Bạn phải chạy chương trình bằng quyền Administrator");
                }
            }

        } else if (jt == dieuhuong) {
            final boolean state = (dieuhuong.getState());
            if (state) {
                jLabel20.setVisible(true);
                jLabel19.setVisible(true);
                navigate = 1;
            } else {
                jLabel20.setVisible(false);
                jLabel19.setVisible(false);
                navigate = 0;
            }
        } else if (jt == month) {
            Utils.writeOutput("month");
            this.setVisible(false);
            month_calendar = new CalendarMonth(null, rootPaneCheckingEnabled);
            month_calendar.show();
        } else if (jt == day) {
            day.setSelected(true);
        } else if (jt == about) {
            JOptionPane.showOptionDialog(this, Utils.about(), "Thông tin", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
        }
    }
}
