package com.example.minirpgfinal;

import javafx.scene.control.*;
import javafx.stage.StageStyle;

import java.util.List;
import java.util.Optional;

//Class that implement the GUI of the Interface Input/OutputParser
public class GUIParser implements InputOutputParser{

    Label mainGUILabel; //this mumber stores the label control of the javaFx main window in wich we will write "force en présence"
    public void setMainGUILabel(Label value){mainGUILabel = value;}
    public Label getLabelField(){return mainGUILabel;}

    public GUIParser(){
    }

    // output interface members
    public boolean DisplayCurrentOpponents(List<Hero> heroes, List<Enemy>enemies, int roundNb){
        String str=""; //str will store that force en présence
        str += "roundNb :"+ roundNb+ "\n\r";
        str += "-----------------------------------"+ "\n\r";
        str += "Heroes"+ "\n\r";
        str += "-----------------------------------"+ "\n\r";

        for(int h=0; h<heroes.size(); h++){
            Hero theHero = heroes.get(h);
            str +=  (h + 1) + " : ";
            str +=  theHero.getClass().getSimpleName()+ " / ";
            str +=  theHero.getHeroAttributesString()+ "\n\r";
        }
        str += ""+ "\n\r";

        str += "-----------------------------------"+ "\n\r";
        str += "Enemies"+ "\n\r";
        str += "-----------------------------------"+ "\n\r";
        for(int e=0; e<enemies.size(); e++){
            Enemy theEnemy = enemies.get(e);
            str +=  (e + 1) + " : ";
            str +=  theEnemy.getClass().getSimpleName()+ " / ";
            str +=  theEnemy.getHeroAttributesString()+ "\n\r";
        }
        str += ""+ "\n\r";


        mainGUILabel.setText(str); //change text of label (with constructed String)

        return true;

    }
    //simple dialog box to display info and wait for user click
    public String AKindOfConfirmDialogBox(String title, String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("");
        alert.setContentText(text);
        alert.showAndWait();
        return "";
    }

    //dialog box to display title and question to the user and return his answer
    public String AKindOfDialogBox(String title, String text){

        Optional<String> result;
        String answer="";
        result = showTheDialog(title, text, "");
        if (result.isPresent()) {
            System.out.println("The user's answer is: " + result.get());
            answer = result.get();
        } else
        {
            System.out.println("The user cancelled the dialog.");
            answer="";
        }
        return answer;
    }


    // input interface members
    //dialog box to request the action the user wants for hero
    public GameParameters.HeroActionKind AskAction(Hero hero){
        GameParameters.HeroActionKind ret = GameParameters.HeroActionKind.NONE;
        String text = "Choose the Action for "+hero.getClass().getSimpleName()+" :\n\r\n\r";
        String title = "Choose the Action";
        if (hero.HasThisCapacity(GameParameters.HeroActionKind.EAT) && hero.getNbFood()>0){ // hero must be able to eat but needs food as well
            text += "'Eat', ";
        }
        if (hero.HasThisCapacity(GameParameters.HeroActionKind.ATTACK) && (hero.canAttack()) ){ // if hero is able to attack upon its class
            text += "'Attack', ";
        }
        if (hero.HasThisCapacity(GameParameters.HeroActionKind.DRINK) && hero.getNbPotion()>0){ // hero must be able to drink and have potions
            text += "'Drink', ";
        }
        if (hero.HasThisCapacity(GameParameters.HeroActionKind.DEFEND) && (hero.getMana()>=5)){ // hero must be able to defend and have mana
            text += "'Defend', ";
        }
        String userInput = AKindOfDialogBox(title, text).toLowerCase().trim(); // remove leading ending spaces of lower case user input
        switch (userInput){
            case "eat":
                ret = GameParameters.HeroActionKind.EAT;
                break;
        }
        switch (userInput){
            case "attack":
                ret = GameParameters.HeroActionKind.ATTACK;
                break;
        }
        switch (userInput){
            case "drink":
                ret = GameParameters.HeroActionKind.DRINK;
                break;
        }
        switch (userInput){
            case "defend":
                ret = GameParameters.HeroActionKind.DEFEND;
                break;
        }

        return ret;


    }

    public Optional<String> showTheDialog(String dlgTitle, String dlgQuestion, String dlgDefaultAnswer){
        TextInputDialog dialog = new TextInputDialog(dlgDefaultAnswer);
        dialog.setTitle(dlgTitle);
        dialog.initStyle(StageStyle.DECORATED);
        dialog.setHeaderText(dlgQuestion);
        dialog.setContentText("Answer:");

        // Show the dialog and get the user's response
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("The user's answer is: " + result.get());
        } else {
            System.out.println("The user cancelled the dialog.");
        }
        return result;
    }
}
