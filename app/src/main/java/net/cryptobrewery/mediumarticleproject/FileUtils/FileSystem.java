package net.cryptobrewery.mediumarticleproject.FileUtils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileSystem {




    public String readFile(File fileToBeRead){
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileToBeRead));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
        }
        return text.toString();
    }


    public boolean saveFile(String fileContents,String fileName , String fullPath){
        try {
            File a = new File(fileName, fullPath);
            FileWriter out = new FileWriter(a);
            out.write(fileContents);
            out.close();
            return true;
        } catch (IOException e) {
            Log.d("asdasd",e.getLocalizedMessage());
            return false;
        }
    }

    public boolean createFile(String fileName , String fullPath){
        try {
            File a = new File(fullPath, fileName);
            if(a.exists()){
                return false;
            }
            FileWriter out = new FileWriter(a);
            out.write("");
            out.close();
            return true;
        } catch (IOException e) {
        }
        return false;
    }
    public boolean createDir(String filePathWithFileName){
        //perform necessary checks...
        File dir = new File(filePathWithFileName);
        if(!dir.exists()){
            dir.mkdirs();
            return true;
        }
        return false;
    }

    public boolean deleteFile(String filePathWithFileName){
        File dir = new File(filePathWithFileName);
        return dir.delete();
    }
}
