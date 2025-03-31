package org.example.BotLogic;


import org.example.Main;
import org.example.Structures.Module;

import java.io.*;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StateParser implements Module {
    private BufferedReader scan;
    private Random rand = new Random();
    private String bufer;

    public String GetArticle(int number){
        try {
            scan = new BufferedReader(new InputStreamReader(
                    Objects.requireNonNull(Main.class.getResourceAsStream("/UgolovnyyKodeks"))));
        }catch (Exception e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }

        try {
            if ((number > 0) && (number < 362)) {
                String article = "";
                Pattern Spliter = Pattern.compile("Статья \\d+");
                boolean StartFlag = false;
                while (true) {
                    try {
                        String s = scan.readLine();
                        if (s.equals("Stop")) {
                            return "Возникла ошибка поиска";
                        }
                        Matcher matcher = Spliter.matcher(s);
                        if (matcher.find()) {
                            Pattern Number = Pattern.compile("\\d+");
                            Matcher NumberMatch = Number.matcher(matcher.group());
                            NumberMatch.find();
                            if (Integer.parseInt(NumberMatch.group()) == number) {
                                System.out.println("Обнаружена нужная статья: ");
                                System.out.println(NumberMatch.group());
                                StartFlag = true;
                                article = article + s + "\n";
                            } else {
                                if (StartFlag) {
                                    break;
                                }
                            }
                        } else {
                            if (StartFlag) {
                                article = article + s + "\n";
                            }
                        }
                    } catch (IOException e) {

                    }
                }
                scan.close();
                return article;
            } else {
                return "Неверный номер статьи";
            }
        }catch (Exception e){
            return "Ошибка чтения файла";
        }
    }

    @Override
    public String TakeResult() {
        return bufer;
    }

    @Override
    public void Input(String inp, Long Who) {
        if (inp.equals("/article")){
            int i = rand.nextInt(1,362);
            System.out.println("Поиск статьи: ");
            System.out.println(i);
            bufer = GetArticle(i);
        }
        else if (inp.substring(0,9).contains("/article")){
            System.out.println("Da");
            try {
                bufer = GetArticle(Integer.parseInt(inp.substring(9)));
            }catch (Exception e){
                bufer = "Указана неверная статья";
            }
        }
        else{
            bufer = "Указана неверная статья";
        }
    }
}
