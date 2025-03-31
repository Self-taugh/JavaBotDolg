package org.example.BotLogic;
import java.util.*;

import org.example.Bot.MainBotLogic;
import org.example.Structures.Module;

public class Congratulator extends Thread implements Module {
    ArrayList<Date> dates = new ArrayList<Date>();
    ArrayList<String> Messages = new ArrayList<String>();
    ArrayList<Long> Id = new ArrayList<Long>();
    private MainBotLogic mbl;
    String bufer;
    @Override
    public void run(){
        while (true){
            for (int i = 0;i < dates.size(); i++){
                if ((dates.get(i)).before(new Date())){
                    System.out.println("Da. Send");
                    mbl.SendNOW(Id.get(i), Messages.get(i));
                    dates.remove(i); Messages.remove(i); Id.remove(i);
                }
            }
            try{
                sleep(1000);
            }catch (Exception e ){System.out.println("Ошибка сна потока");}
        }
    }

    public Congratulator(MainBotLogic mbt){
        this.mbl = mbt;

    }

    @Override
    public String TakeResult() {
        return bufer;
    }

    @Override
    public void Input(String input, Long who) {
        parseComand(input, who);
    }

    private void parseComand(String str, Long who){
        if (str.contains("Запиши")){
            String[] cl = str.split(" ");
            if (cl.length < 3){ bufer = "Неверный формат ввода"; return;}
            int day, month,year, hour, minuts;
            Date newDate;
            try{
                String[] date = cl[1].split("\\.");
                day = Integer.parseInt(date[0]);
                month = Integer.parseInt(date[1]);
                year = Integer.parseInt(date[2]);
                hour = Integer.parseInt(cl[2].split(":")[0]);
                minuts = Integer.parseInt(cl[2].split(":")[1]);
                newDate = new Date(year - 1900, month-1, day,hour,minuts);
                System.out.println("New Date: ");
                System.out.println(newDate.toString());

                String info = "";
                for (int i = 3;i< cl.length; i++){
                    info = info + cl[i] + " ";
                }

                Id.add(who);
                dates.add(newDate);
                Messages.add(info);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                bufer = "Ошибка ввода данных";
            }
        }
        else {
            bufer = "Как вводить команду: \nЗапиши дд.мм.гггг чч:мм  ваш текст";
        }
    }
}
