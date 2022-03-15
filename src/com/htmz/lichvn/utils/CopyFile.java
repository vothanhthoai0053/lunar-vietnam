package com.htmz.lichvn.utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HTMZ
 */
public class CopyFile {

    public void FileCopy(String url, String urlcopy) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(new File(url));
            os = new FileOutputStream(new File(urlcopy));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
            }
            try {
                os.close();
            } catch (IOException ex) {
            }
        }
    }

}
