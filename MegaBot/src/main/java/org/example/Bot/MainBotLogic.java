package org.example.Bot;

import org.example.APIWork.APIObject;
import org.example.BotLogic.CoreModule;
import org.example.BotLogic.StateParser;
import org.example.BotLogic.TextBasedRPG;
import org.example.Structures.Module;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainBotLogic {
    public APIObject API;

    private HashMap<Long,Integer> ClientsStates;
    private Module[] SomeModule = new Module[5];
    private String bufer;

    public MainBotLogic(APIObject api){
        API=api;
        ClientsStates = new HashMap<Long,Integer>();
        SomeModule[0] = new CoreModule();
        SomeModule[1] = new StateParser();
        SomeModule[2] = new TextBasedRPG();
    }

    public void Think(long Who, String What){
        if (ClientsStates.containsKey(Who)){
            System.out.println(Who);
            System.out.println(What);
            RecogniseComand(Who, What);
            ModuleWorks(Who, What);
            Send(Who,bufer);
        }
        else{
            ClientsStates.put(Who,0);
            Send(Who, "Здравствуй");
        }
    }

    private void RecogniseComand(long Who, String str){
        if(str.contains("/article")){
            ClientsStates.put(Who,1);
        }
        else if (str.contains("/game")){
            ClientsStates.put(Who,2);
        }
        else if (str.contains("/")){
            ClientsStates.put(Who,0);
        }
    }

    private void ModuleWorks(long Who, String inp){
        SomeModule[ClientsStates.get(Who)].Input(inp);
        bufer = SomeModule[ClientsStates.get(Who)].TakeResult();
    }

    private void Send(long Who, String What){
        API.sendText(Who, What);
    }
}
