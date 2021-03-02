package com.lapi.lottoapi.lottoguess;

import com.google.gson.Gson;

import java.io.*;

public class FileHandler {
    boolean createFile(String fileName) {
        Boolean fileCreated = false;
        try {
            File myObj = new File(fileName);
            fileCreated = myObj.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return fileCreated;
    }

    void writeToFile(String fileName, String content) {
        if (!createFile(fileName)) {
            try {
                FileWriter myWriter = new FileWriter(fileName);
                myWriter.write(content);
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    Object readFileContent(String fileName) {
        Object content = null;
        Gson gson = new Gson();

        try (Reader reader = new FileReader(fileName)) {
            content = gson.fromJson(reader,Object.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

}