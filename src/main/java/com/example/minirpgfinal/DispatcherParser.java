package com.example.minirpgfinal;

import javafx.scene.control.Label;

import java.util.List;

//Class used to choose between 2 IHM : GUI or Console and to dispatch all calls to inputOutputParser Interface accordingly
public class DispatcherParser implements InputOutputParser{

    private boolean isGUI=false; // will store if GUI was chosen
    private ConsoleParser theConsoleParser = new ConsoleParser(); // will store instance of a consoleParser
    private GUIParser theGUIParser = new GUIParser(); // will store instance of a GUIParser

    public boolean getIsGUI(){return isGUI;}

    public DispatcherParser(){
        var ret = theConsoleParser.AKindOfDialogBox("Mode d'IHM", "Voulez vous une interface graphique [G]UI ou [C]onsole ?");
        if (ret.equals("G"))
            isGUI = true;
        else
            isGUI=false;
        System.out.println(isGUI);
    }

    // output interface members
    //when this functions are called it check isGUI Boolean and dispatch function call to proper class (ConsoleParser, GUIParser)
    public boolean DisplayCurrentOpponents(List<Hero> heroes, List<Enemy>enemies, int roundNb){

        boolean ret=false;
        if (isGUI)
            ret = theGUIParser.DisplayCurrentOpponents(heroes, enemies, roundNb);
        else
            ret = theConsoleParser.DisplayCurrentOpponents(heroes, enemies, roundNb);
        return true;
    }

    public String AKindOfDialogBox(String title, String text){

        String answer="";
        if (isGUI)
            answer = theGUIParser.AKindOfDialogBox(title, text);
        else
            answer = theConsoleParser.AKindOfDialogBox(title, text);


        return answer;
    }

    public String AKindOfConfirmDialogBox(String title, String text){

        String answer="";
        if (isGUI)
            answer = theGUIParser.AKindOfConfirmDialogBox(title, text);
        else
            answer = theConsoleParser.AKindOfConfirmDialogBox(title, text);


        return answer;
    }

    public void setLabelField(Label value){
        Label ret=null;
        if (isGUI)
            theGUIParser.setMainGUILabel(value);
    }

    // input interface members
    public GameParameters.HeroActionKind AskAction(Hero hero){
        GameParameters.HeroActionKind ret= GameParameters.HeroActionKind.NONE;
        if (isGUI)
            ret = theGUIParser.AskAction(hero);
        else
            ret = theConsoleParser.AskAction(hero);
        return ret;
    }


}
