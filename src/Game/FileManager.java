package Game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {

    int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void writePatient(String name , String fileName ){
        String person = name+",0\n";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.write(person);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void change(String name ,int lvl){
        String nameInFile;
        int levelInFile;
        File file = new File("data.txt");
        List<String> list = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("[,\n]");
            while (scanner.hasNext()){
                nameInFile = scanner.next();
                levelInFile = Integer.parseInt(scanner.next());
                if (name.equals(nameInFile)){
                    if(levelInFile>lvl){
                        list.add(name+","+levelInFile);
                        continue;
                    }
                    else list.add(name+","+lvl);
                    continue;
                }
                list.add(nameInFile+","+levelInFile);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();

        int i = 0;
        while (i < list.size()) {
            sb.append(list.get(i)+"\n");
            i++;
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int readLevel(String name){
        String nameInFile;
        int levelInFile;
        File file = new File("data.txt");
        List<String> list = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("[,\n]");
            while (scanner.hasNext()){
                nameInFile = scanner.next();
                levelInFile = Integer.parseInt(scanner.next());
                if (name.equals(nameInFile)){
                    return levelInFile;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public  boolean checkRegister(String name, String fileName){
        File file = new File(fileName);
        boolean flag = false;
        if(!file.exists()) return flag ;
        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("[,\n]");
            while (scanner.hasNext()) {
                if(scanner.next().equals(name)) {
                    setLevel(Integer.parseInt(scanner.next()));
                    flag = true;
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
