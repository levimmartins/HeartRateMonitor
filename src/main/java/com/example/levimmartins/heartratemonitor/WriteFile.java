package com.example.levimmartins.heartratemonitor;

/**
 * Created by levimmartins on 13/05/17.
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WriteFile {

    private String FILENAME;

    BufferedWriter bw = null;
    FileWriter fw = null;

    public WriteFile(String file) {
        this.FILENAME = file;
        createFile();
    }


    public void createFile(){
        try {
            fw = new FileWriter(FILENAME);
        } catch (IOException ex) {
            Logger.getLogger(SsensorHealthActivity.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void WriteWord(String content){


        bw = new BufferedWriter(fw);

        try {
            bw.write(content);
        } catch (IOException ex) {
            Logger.getLogger(SsensorHealthActivity.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Done");
    }



    public void CloseFile(){
        try{
            bw.close();
            fw.close();
        }catch (IOException ex) {
            ex.printStackTrace();

        }
    }

}
