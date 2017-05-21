package com.implemica;

import com.implemica.second.Main;

import java.io.File;


public class SecondTask {

    public static void main(String[] args){
        File fileIn = new File("./data/input-task.txt");
        File fileOut = new File("./data/output-task.txt");
        Main task = new Main(fileIn, fileOut);
        task.main(task,fileOut);
    }
}
