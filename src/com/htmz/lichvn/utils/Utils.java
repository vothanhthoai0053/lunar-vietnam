/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htmz.lichvn.utils;

import static com.htmz.lichvn.formload.constants.IConstanst.NAME_OF_CONFIG_CALENDAR_TYPE;
import com.htmz.lichvn.main.CalendarWiget;
import com.htmz.lichvn.model.DateInfo;
import static com.htmz.lichvn.utils.LunarUtils.INT;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author vtt0053
 */
public class Utils {

    public static String about() {
        return "<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Thông Tin Về Lịch</b>"
                + "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Để hoàn thành được phiên bản này Mình đã sử dụng Code ngày,thang của TS.HỒ NGỌC ĐỨC<br>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Tử vi được Mình sử dụng lấy từ trang Web <a href='http://xem-tuvi.com'>http://xem-tuvi.com</a><br>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Phiên bản còn rất nhiều hạn chế mong các Bạn góp ý địa chỉ để hoàn thiện hơn <a href='http://google.com'>htmz0053@gmail.com</a>"
                + "<br><br><i>18/12/2013</i><br><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Võ Thanh Thoại-12DTH03<br>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Đại Học Công Nghệ TP.HCM</b><html>";
    }

    public static BufferedImage trayIconDefault() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(SystemTrayUtils.class.getResourceAsStream("/images/other/tray24.png"));
        } catch (IOException ex) {
            Logger.getLogger(CalendarWiget.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }

    public static String CanChiNgay(long u) {
        String kq = null;
        int nd = (int) (u % 60);
        switch (nd) {
            case 1:
                kq = "Giáp Tuất";
                break;
            case 2:
                kq = "Ất Hợi";
                break;
            case 3:
                kq = "Bính Tý";
                break;
            case 4:
                kq = "Đinh Sửu";
                break;
            case 5:
                kq = "Mậu Dần";
                break;
            case 6:
                kq = "Kỷ Mão";
                break;
            case 7:
                kq = "Canh Thìn";
                break;
            case 8:
                kq = "Tân Tỵ";
                break;
            case 9:
                kq = "Nhâm Ngọ";
                break;
            case 10:
                kq = "Qúy Mùi";
                break;
            case 11:
                kq = "Giáp Thân";
                break;
            case 12:
                kq = "Ất Dậu";
                break;
            case 13:
                kq = "Bính Tuất";
                break;
            case 14:
                kq = "Đinh Hợi";
                break;
            case 15:
                kq = "Mậu Tý";
                break;
            case 16:
                kq = "Kỷ Sửu";
                break;
            case 17:
                kq = "Canh Dần";
                break;
            case 18:
                kq = "Tân Mão";
                break;
            case 19:
                kq = "Nhâm Thìn";
                break;
            case 20:
                kq = "Qúy Tỵ";
                break;
            case 21:
                kq = "Giáp Ngọ";
                break;
            case 22:
                kq = "Ất Mùi";
                break;
            case 23:
                kq = "Bính Thân";
                break;
            case 24:
                kq = "Đinh Dậu";
                break;
            case 25:
                kq = "Mậu Tuất";
                break;
            case 26:
                kq = "Kỷ Hợi";
                break;
            case 27:
                kq = "Canh Tý";
                break;
            case 28:
                kq = "Tân Sửu";
                break;
            case 29:
                kq = "Nhâm Dần";
                break;
            case 30:
                kq = "Qúy Mão";
                break;
            case 31:
                kq = "Giáp Thìn";
                break;
            case 32:
                kq = "Ất Tỵ";
                break;
            case 33:
                kq = "Bính Ngọ";
                break;
            case 34:
                kq = "Đinh Mùi";
                break;
            case 35:
                kq = "Mậu Thân";
                break;
            case 36:
                kq = "Kỷ Dậu";
                break;
            case 37:
                kq = "Canh Tuất";
                break;
            case 38:
                kq = "Tân Hợi";
                break;
            case 39:
                kq = "Nhâm Tý";
                break;
            case 40:
                kq = "Qúy Sửu";
                break;
            case 41:
                kq = "Giáp Dần";
                break;
            case 42:
                kq = "Ất Mão";
                break;
            case 43:
                kq = "Bính Thìn";
                break;
            case 44:
                kq = "Đinh Tỵ";
                break;
            case 45:
                kq = "Mậu Ngọ";
                break;
            case 46:
                kq = "Kỷ Mùi";
                break;
            case 47:
                kq = "Canh Thân";
                break;
            case 48:
                kq = "Tân Dậu";
                break;
            case 49:
                kq = "Nhâm Tuất";
                break;
            case 50:
                kq = "Qúy Hợi";
                break;
            case 51:
                kq = "Giáp Tý";
                break;
            case 52:
                kq = "Ất Sửu";
                break;
            case 53:
                kq = "Bính Dần";
                break;
            case 54:
                kq = "Đinh Mão";
                break;
            case 55:
                kq = "Mậu Thìn";
                break;
            case 56:
                kq = "Kỷ Tỵ";
                break;
            case 57:
                kq = "Canh Ngọ";
                break;
            case 58:
                kq = "Tân Mùi";
                break;
            case 59:
                kq = "Nhâm Thân";
                break;
            case 0:
                kq = "Qúy Dậu";
                break;
        }

        return kq;
    }

    public static long DemThangNgay(int dd, int mm, int yy, int d, int m, int y) {

        int ls = 0;
        int nd;

        while (yy != y || mm != m) {
            if ((yy % 4 == 0 && yy % 100 != 0) | (yy % 100 == 0 && yy % 400 == 0)) {
                nd = 0;
            } else {
                nd = 1;
            }
            switch (mm) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12: {
                    if (dd == 31) {
                        mm++;
                        dd = 1;
                        ls++;
                    } else {
                        dd++;
                        ls++;
                    }
                }
                break;
                case 4:
                case 6:
                case 9:
                case 11: {
                    if (dd == 30) {
                        mm++;
                        dd = 1;
                        ls++;
                    } else {
                        dd++;
                        ls++;
                    }
                }
                break;
                case 2: {
                    switch (nd) {
                        case 0: {
                            if (dd == 29) {
                                mm++;
                                dd = 1;
                                ls++;
                            } else {
                                dd++;
                                ls++;
                            }
                        }
                        break;
                        default: {
                            if (dd == 28) {
                                mm++;
                                dd = 1;
                                ls++;
                            } else {
                                dd++;
                                ls++;
                            }
                        }
                        break;
                    }
                }
                break;
                default: {
                    yy++;
                    mm = 1;
                    dd = 1;
                }
            }
        }
        return ls + d;
    }

    public static String headOfAnimal(String nameSource) {
        String urlHr = null;
        switch (nameSource.trim().toLowerCase()) {
            case "tý":
                urlHr = "mouse.png";
                break;
            case "sửu":
                urlHr = "buffalo.png";
                break;
            case "dần":
                urlHr = "tiger.png";
                break;
            case "mão":
                urlHr = "cat.png";
                break;
            case "thìn":
                urlHr = "dragon.png";
                break;
            case "tỵ":
                urlHr = "snake.png";
                break;
            case "ngọ":
                urlHr = "horse.png";
                break;
            case "mùi":
                urlHr = "goat.png";
                break;
            case "thân":
                urlHr = "monkey.png";
                break;
            case "dậu":
                urlHr = "chicken.png";
                break;
            case "tuất":
                urlHr = "dog.png";
                break;
            case "hợi":
                urlHr = "pig.png";
                break;
        }
        return urlHr;
    }

    public static int lunarTotalDayOfMonth(int dd, int mm, int yy, double timeZone) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(yy, mm - 1, dd);
        final int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int k = getKOfMoonDay(maxDay, mm, yy);
        calendar.add(Calendar.MONTH, 1);
        final int maxDayNext = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int kNext = getKOfMoonDay(maxDayNext, calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
        return LunarUtils.getNewMoonDay(kNext, 7.0) - LunarUtils.getNewMoonDay(k, 7.0);
    }

    public static int getKOfMoonDay(int dd, int mm, int yy) {
        double off = LunarUtils.jdFromDate(dd, mm, yy) - 2415021.076998695;
        int k = INT(off / 29.530588853);
        return k;
    }

    public static String coverZeroSemantic(int source) {
        StringBuilder sb = new StringBuilder();
        if (source < 10) {
            sb.append(0);
        }
        sb.append(source);
        return sb.toString();
    }

    public static DateInfo getDateInfo(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);

        DateInfo dateInfo = new DateInfo();
        dateInfo.setDayOfYear(dayOfYear);
        dateInfo.setWeekOfYear(weekOfYear);

        return dateInfo;
    }

    public static String toUpperCase(String soure) {
        if (soure == null) {
            return null;
        }
        return soure.toUpperCase();
    }

    public static String convertNumberToString(int number, boolean isFormal) {
        switch (number) {
            case 0:
                return "Không";
            case 1:
                if (isFormal) {
                    return "Một";
                } else {
                    return "Giêng";
                }
            case 2:
                return "Hai";
            case 3:
                return "Ba";
            case 4:
                return "Bốn";
            case 5:
                return "Năm";
            case 6:
                return "Sáu";
            case 7:
                return "Bảy";
            case 8:
                return "Tám";
            case 9:
                return "Chín";
            case 10:
                return "Mười";
            case 11:
                return "Mười Một";
            case 12:
                return "Mười Hai";
            default:
                return "Unknown";
        }
    }

    public static String readUTF8(InputStream fis) {
        String kq = "";
        try {
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String h;
            while ((h = br.readLine()) != null) {
                kq += h;
            }
            br.close();
            isr.close();
            fis.close();
        } catch (Exception ex) {
        }
        return kq;
    }

    public static String readUTF8(String urlPath) {
        String kq = "";
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(new File(urlPath)), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String h;
            while ((h = br.readLine()) != null) {
                kq += h;
            }
            br.close();
            isr.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "".equalsIgnoreCase(kq) ? "day" : kq;
    }

    public static int writeOutput(String str) {
        try {
            String fullPath = Utils.getPathConfig(NAME_OF_CONFIG_CALENDAR_TYPE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fullPath), "UTF8"));
            out.write(str);
            out.close();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
    }

    public static String getPathConfig(String namePath) {
        // user dir
        String userDir = System.getProperty("user.dir");
        String fullPath = userDir + "/" + namePath;
        // tmp file
        // String tmpdir = System.getProperty("java.io.tmpdir");
        // String fullPath = tmpdir + "/" + namePath;
        return fullPath;
    }

    public static String Thu(int d, int m, int y) {
        long ls = LunarUtils.jdFromDate(d, m, y);
        int nx = (int) ls % 7;
        String kq = null;
        switch (nx) {
            case 0:
                kq = "Thứ hai";
                break;
            case 1:
                kq = "Thứ ba";
                break;
            case 2:
                kq = "Thứ tư";
                break;
            case 3:
                kq = "Thứ năm";
                break;
            case 4:
                kq = "Thứ sáu";
                break;
            case 5:
                kq = "Thứ bảy";
                break;
            case 6:
                kq = "Chủ nhật";
        }
        return kq;
    }

    public static String SetCanT(int y, int m) {
        int th = 0;
        String kqct = null;
        th = ((y * 12 + m + 3) % 10);
        switch (th) {
            case 0:
                kqct = "Giáp";
                break;
            case 1:
                kqct = "Ất";
                break;
            case 2:
                kqct = "Bính";
                break;
            case 3:
                kqct = "Đinh";
                break;
            case 4:
                kqct = "Mậu";
                break;
            case 5:
                kqct = "Kỷ";
                break;
            case 6:
                kqct = "Canh";
                break;
            case 7:
                kqct = "Tân";
                break;
            case 8:
                kqct = "Nhâm";
                break;
            case 9:
                kqct = "Qúy";
                break;
        }
        return kqct;
    }

    public static String SetChiT(int m) {
        String kqcg = null;
        switch (m) {
            case 1:
                kqcg = "Dần";
                break;
            case 2:
                kqcg = "Mão";
                break;
            case 3:
                kqcg = "Thìn";
                break;
            case 4:
                kqcg = "Tỵ";
                break;
            case 5:
                kqcg = "Ngọ";
                break;
            case 6:
                kqcg = "Mùi";
                break;
            case 7:
                kqcg = "Thân";
                break;
            case 8:
                kqcg = "Dậu";
                break;
            case 9:
                kqcg = "Tuất";
                break;
            case 10:
                kqcg = "Hợi";
                break;
            case 11:
                kqcg = "Tý";
                break;
            case 12:
                kqcg = "Sửu";
                break;
        }
        return kqcg;
    }

    public static String SetCanG(int d, int m, int y) {
        long h = LunarUtils.jdFromDate(d, m, y);
        String ks = null;
        int can = 0;
        String c = CanChiNgay(h);
        StringTokenizer stk = new StringTokenizer(c, " ");
        Vector cs = new Vector();
        while (stk.hasMoreElements()) {
            cs.addElement(stk.nextToken());
        }
        for (int i = 0; i < cs.size(); i++) {
            if (i < 1) {
                ks = (String) cs.elementAt(i);
            }
        }
        switch (ks.trim()) {
            case "Giáp":
                can = 1;
                break;
            case "Ất":
                can = 2;
                break;
            case "Bính":
                can = 3;
                break;
            case "Đinh":
                can = 4;
                break;
            case "Mậu":
                can = 5;
                break;
            case "Kỷ":
                can = 6;
                break;
            case "Canh":
                can = 7;
                break;
            case "Tân":
                can = 8;
                break;
            case "Nhâm":
                can = 9;
                break;
            case "Qúy":
                can = 10;
                break;
        }
        int ka = (((can * 2) - 1) % 10);
        String kqci = null;
        switch (ka) {
            case 1:
                kqci = "Giáp";
                break;
            case 3:
                kqci = "Bính";
                break;
            case 5:
                kqci = "Mậu";
                break;
            case 7:
                kqci = "Canh";
                break;
            case 9:
                kqci = "Nhâm";
                break;
        }
        return kqci;
    }

    public static String SetChiG(int h) {
        String chig = null;
        switch (h) {
            case 23:
            case 00:
                chig = "Tý";
                break;
            case 1:
            case 2:
                chig = "Sửu";
                break;
            case 3:
            case 4:
                chig = "Dần";
                break;
            case 5:
            case 6:
                chig = "Mão";
                break;
            case 7:
            case 8:
                chig = "Thìn";
                break;
            case 9:
            case 10:
                chig = "Tỵ";
                break;
            case 11:
            case 12:
                chig = "Ngọ";
                break;
            case 13:
            case 14:
                chig = "Mùi";
                break;
            case 15:
            case 16:
                chig = "Thân";
                break;
            case 17:
            case 18:
                chig = "Dậu";
                break;
            case 19:
            case 20:
                chig = "Tuất";
                break;
            case 21:
            case 22:
                chig = "Hợi";
                break;
        }
        return chig;
    }

    public static String SetNhuanAmLich(int y) {
        String kq = null;
        int an = y % 19;
        switch (an) {
            case 0:
            case 3:
            case 6:
            case 9:
            case 11:
            case 14:
            case 17:
                kq = "Nhuần";
                break;
            default:
                kq = "Không Nhuần";
                break;
        }
        return kq;
    }

    public static String SetNhuanDuongLich(int y) {
        String kq = null;
        if (y % 4 == 0) {
            kq = "Nhuần";
        } else {
            kq = "Không Nhuần";
        }
        return kq;
    }

    public static String SetCanY(int y) {
        int can;
        String kqca = null;
        can = y % 10;
        switch (can) {
            case 0:
                kqca = "Canh";
                break;
            case 1:
                kqca = "Tân";
                break;
            case 2:
                kqca = "Nhâm";
                break;
            case 3:
                kqca = "Qúy";
                break;
            case 4:
                kqca = "Giáp";
                break;
            case 5:
                kqca = "Ất";
                break;
            case 6:
                kqca = "Bính";
                break;
            case 7:
                kqca = "Đinh";
                break;
            case 8:
                kqca = "Mậu";
                break;
            case 9:
                kqca = "Kỷ";
                break;
        }
        return kqca;
    }

    public static String SetChiY(int y) {
        String kqch = null;
        if (y > 0) {
            int chi = y % 12;
            switch (chi) {
                case 0:
                    kqch = "Thân";
                    break;
                case 1:
                    kqch = "Dậu";
                    break;
                case 2:
                    kqch = "Tuất";
                    break;
                case 3:
                    kqch = "Hợi";
                    break;
                case 4:
                    kqch = "Tý";
                    break;
                case 5:
                    kqch = "Sửu";
                    break;
                case 6:
                    kqch = "Dần";
                    break;
                case 7:
                    kqch = "Mão";
                    break;
                case 8:
                    kqch = "Thìn";
                    break;
                case 9:
                    kqch = "Tỵ";
                    break;
                case 10:
                    kqch = "Ngọ";
                    break;
                case 11:
                    kqch = "Mùi";
                    break;
            }
        }
        return kqch;
    }

    public static int setThangNhuanAl(int namam) {
        int z, tn, result;
        if (SetNhuanAmLich(namam).equals("Nhuần")) {
            z = LunarUtils.getLunarMonth11(namam - 1, 7);
            tn = LunarUtils.getLeapMonthOffset(z, 7);
            result = tn - 2;
            return result;
        }
        return 0;
    }

    public static String Settiet(int d, int m) {
        String kq = null;
        switch (m) {
            case 2: {
                if (d >= 1 && d < 4) {
                    kq = "Đại hàn";
                } else if (d >= 4 && d < 19) {
                    kq = "Lập xuân";
                } else {
                    kq = "Vũ thủy";
                }
            }
            break;
            case 3: {
                if (d >= 1 && d < 5) {
                    kq = "Vũ thủy";
                } else if (d >= 5 && d < 21) {
                    kq = "Kinh trập";
                } else {
                    kq = "Xuân phân";
                }
            }
            break;
            case 4: {
                if (d >= 1 && d < 5) {
                    kq = "Xuân phân";
                } else if (d >= 5 && d < 20) {
                    kq = "Thanh minh";
                } else {
                    kq = "Cốc vũ";
                }
            }
            break;
            case 5: {
                if (d >= 1 && d < 6) {
                    kq = "Cốc vũ";
                } else if (d >= 6 && d < 21) {
                    kq = "Lập hạ";
                } else {
                    kq = "Tiểu mãn";
                }
            }
            break;
            case 6: {
                if (d >= 1 && d < 6) {
                    kq = "Tiểu mãn";
                } else if (d >= 6 && d < 21) {
                    kq = "Mang chủng";
                } else {
                    kq = "Hạ chí";
                }
            }
            break;
            case 7: {
                if (d >= 1 && d < 7) {
                    kq = "Hạ chí";
                } else if (d >= 7 && d < 23) {
                    kq = "Tiểu thử";
                } else {
                    kq = "Đại thử";
                }
            }
            break;
            case 8: {
                if (d >= 1 && d < 7) {
                    kq = "Đại thử";
                } else if (d >= 7 && d < 23) {
                    kq = "Lập thu";
                } else {
                    kq = "Xử thử";
                }
            }
            break;
            case 9: {
                if (d >= 1 && d < 8) {
                    kq = "Xử thử";
                } else if (d >= 8 && d < 23) {
                    kq = "Bạch lộ";
                } else {
                    kq = "Thu phân";
                }
            }
            break;
            case 10: {
                if (d >= 1 && d < 8) {
                    kq = "Thu phân";
                } else if (d >= 8 && d < 23) {
                    kq = "Hàn lộ";
                } else {
                    kq = "Sương giáng";
                }
            }
            break;
            case 11: {
                if (d >= 1 && d < 7) {
                    kq = "Sương giáng";
                } else if (d >= 7 && d < 22) {
                    kq = "Lập đông";
                } else {
                    kq = "Tiểu tuyết";
                }
            }
            break;
            case 12: {
                if (d >= 1 && d < 7) {
                    kq = "Tiểu tuyết";
                } else if (d >= 7 && d < 22) {
                    kq = "Đại tuyết";
                } else {
                    kq = "Đông chí";
                }
            }
            break;
            case 1: {
                if (d >= 1 && d < 6) {
                    kq = "Đông chí";
                } else if (d >= 6 && d < 21) {
                    kq = "Tiểu hàn";
                } else {
                    kq = "Đại hàn";
                }
            }
            break;
        }
        return kq;
    }
}
