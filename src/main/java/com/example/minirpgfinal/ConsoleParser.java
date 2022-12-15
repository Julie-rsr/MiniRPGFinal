package com.example.minirpgfinal;

import java.util.List;
import java.util.Scanner;

//Class responsible for what is showed in the Console, must implement all functions of Interface InputOutputParser
public class ConsoleParser implements InputOutputParser {

    public ConsoleParser(){
    }
    // output interface members
    public boolean DisplayCurrentOpponents(List<Hero> heroes, List<Enemy>enemies, int roundNb) //used to display force en présence
    {
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("roundNb :"+ roundNb);
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Heroes");
        System.out.println("------------------------------------------------------------------------------------------------------------------");

        for(int h=0; h<heroes.size(); h++){
            Hero theHero = heroes.get(h);
            System.out.println( h + 1);
            System.out.println( theHero.getClass().getSimpleName()); //Retrieve class name (ex : Mage,...)
            System.out.println( theHero.getHeroAttributesString()); //Retrieve String of hero attributes (ex : lifePoints,...)
            System.out.println("");
        }
        System.out.println("");

        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println("Enemies");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        for(int e=0; e<enemies.size(); e++){
            Enemy theEnemy = enemies.get(e);
            System.out.println( e + 1 );
            System.out.println( theEnemy.getClass().getSimpleName());
            System.out.println( theEnemy.getHeroAttributesString());
            System.out.println("");
        }
        System.out.println("");

        return true;
    }

    public String AKindOfConfirmDialogBox(String title, String text)
    {
        return AKindOfDialogBox(title, text);
    }

    public String AKindOfDialogBox(String title, String text){ //function to display title and text and return user keyboard entry
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println(title);
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.println(text);
        System.out.println("");
        Scanner sc = new Scanner(System.in);
        System.out.println("");
        System.out.println("");
        String answer = sc.nextLine();
        return answer;
    }


    // input interface members
    public GameParameters.HeroActionKind AskAction(Hero hero){ //function to ask next hero action in fight (ex : eat, attack,...) depending on the heroKind and inventory
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


}
