package com.lethaqlch.qlch5application.mClass;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileInternal {
    private Context context;
    public FileInternal(Context context) {
        this.context = context;

    }
    public void writeData(String strInput,String fileName,boolean isAppend){

        int mode = Context.MODE_APPEND;
        if (!isAppend) mode = Context.MODE_PRIVATE;
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName,mode);
            fileOutputStream.write(strInput.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean isFileExist(String fileName){
        File file = new File(context.getFilesDir().getAbsolutePath(),fileName);

        return  file.exists();
    }

    public boolean fileDelete(String fileName){
        File file = new File(context.getFilesDir().getAbsolutePath(),fileName);
        return file.delete();
    }

    public String readData(String fileName){
        String line = null;
        StringBuffer buffer = new StringBuffer();

        try {



            FileInputStream fileInputStream = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            while ((line = reader.readLine())!=null){
                buffer.append(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public ArrayList<String> listLineReadFile (String fileName ){
        String line = null;
        ArrayList<String> stringArrayList = null;
        FileInputStream fileInputStream = null ;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        try {

            stringArrayList = new ArrayList<>();

            fileInputStream = context.openFileInput(fileName);
             inputStreamReader = new InputStreamReader(fileInputStream);
             reader = new BufferedReader(inputStreamReader);

            while ((line = reader.readLine())!=null){
                stringArrayList.add(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringArrayList;
    }
}
