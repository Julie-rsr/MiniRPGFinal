package com.example.minirpgfinal;

import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    // Attributes

    final int maxRounds = 4; // max round before boss
    private int curRound = 1; // current round

    private int nbMaxHeroes; // max playable heroes
    private int firstNotDeadHero; // 1st not dead hero updated during combat
    private List<Hero> playerHeroes = new ArrayList<>(); // define list of hero instances of the player
    private int nbPlayerHeroes; // nb heroes

    private List<Enemy> Enemies = new ArrayList<>(); // define list of enemy instances
    private int nbEnemies; // nb enemies
    private int firstNotDeadEnemy; // 1st not dead Enemy updated during combat

    private DispatcherParser theDispatcherParser;
    public Label lbl=null;

    // Constructor function
    public Game(){
        this.theDispatcherParser = new DispatcherParser();
    }

    public boolean isItAGraphicGame(){
        return this.theDispatcherParser.getIsGUI();
    }

    // Public Members
    public boolean playGame() { // play the whole game
        boolean ret = false;
        this.theDispatcherParser.setLabelField(lbl);
        this.nbMaxHeroes = 4;

        // ask the user how many heroes he wants (specifying the max)
        String answer = this.theDispatcherParser.AKindOfDialogBox("Nombre de héros ", "Veuillez saisir le nombre de héros souhaité (max:"+this.nbMaxHeroes+").");
        // convert answer String to a number between 0 and max allowed
        try {
            nbPlayerHeroes = Math.max(Math.min(Integer.parseInt(answer), this.nbMaxHeroes), 1); //take the min between entered number and nb max hero and the max between 1 and previous number
        }
        catch(Exception e) {
            System.out.println("Pb vous n'avez pas saisi un nombre entier ...!");
            System.out.println(e.getMessage());
            System.exit(999);
        }
        this.initGame();
        this.initHeroes();
        while (this.curRound<=this.maxRounds && this.playerHeroes.size()>0){
            this.nbEnemies = this.nbPlayerHeroes;
            this.initEnemies();
            Collections.shuffle(this.playerHeroes); // shuffle the order of heroes
            Collections.shuffle(this.Enemies); // shuffle the order of enemies
            this.theDispatcherParser.DisplayCurrentOpponents(this.playerHeroes, this.Enemies, this.curRound);
            this.computeNotDeads();
            int currentHero = this.firstNotDeadHero;
            int currentEnemy = this.firstNotDeadEnemy;
            while (currentHero>-1 && currentEnemy>-1) {
                // currentHero attacks 1st not dead enemy (kill him or not)
                this.heroAttackEnemy(currentHero, currentEnemy);
                // is attacked enemy dead ? If yes remove it from list
                if(this.Enemies.get(currentEnemy).getLifePoints()<=0){
                    this.Enemies.remove(currentEnemy);
                    currentEnemy = this.GetNextEnemyAliveIndex(currentEnemy);
                }
                this.theDispatcherParser.DisplayCurrentOpponents(this.playerHeroes, this.Enemies, this.curRound);

                if (currentEnemy>-1) {
                    // current enemy attacks 1st not dead hero (kill him or not)
                    this.playerHeroes.get(currentHero).heroAttacked(this.Enemies.get(currentEnemy).getDamage());
                    if(this.playerHeroes.get(currentHero).getLifePoints()<=0){
                        this.playerHeroes.remove(currentHero);
                        currentHero = this.GetNextHeroAliveIndex(currentHero);
                    }
                    // currentEnemy ++ modulo nbEnemy ;
                    currentEnemy = this.GetNextEnemyAliveIndex(currentEnemy);
                    // currentHero++ modulo nbHero ;
                    currentHero = this.GetNextHeroAliveIndex(currentHero);
                }
                this.theDispatcherParser.DisplayCurrentOpponents(this.playerHeroes, this.Enemies, this.curRound);

            }
            this.ImproveHeroes();
            this.theDispatcherParser.DisplayCurrentOpponents(this.playerHeroes, this.Enemies, this.curRound);
            this.curRound++;
        }
        // Either all heros are dead or we passed final round
        if(this.playerHeroes.size()>0){
            this.theDispatcherParser.AKindOfConfirmDialogBox("End of game", "You won and passed the last round");
        }
        else {
            this.theDispatcherParser.AKindOfConfirmDialogBox("End of game", "You loose : no more heroes are alive ... Try again !");
        }
        return ret;
    }

    // Private Members

    private boolean ImproveHeroes(){
        boolean ret=false;
        for (int h=0;h<this.playerHeroes.size();h++) {
            String title = "Increase skills";
            String text = "";
            Hero thisHero = this.playerHeroes.get(h);
            switch(thisHero.getClass().getSimpleName()) {
                case "Warrior":
                    text="Do you want to increase [Damage] or [Lifepoints] of your Warrior?";
                    break;
                case "Mage":
                    text="Do you want to increase [Damage], [Lifepoints] or [Mana] of your Mage?";
                    break;
                case "Hunter":
                    text="Do you want to increase number of [Arrows], [Damage] or [Lifepoints] of your Hunter?";
                    break;
                case "Healer":
                    text="Do you want to increase [Mana] or [Lifepoints] of your Healer?";
                    break;
            }
            String answer = this.theDispatcherParser.AKindOfDialogBox(title,text).toLowerCase().trim();
            switch(answer) {
                case "damage":
                    thisHero.setDamage(thisHero.getDamage() * 2);
                    break;
                case "lifepoints":
                    thisHero.setLifePoints(thisHero.getLifePoints() * 2);
                    thisHero.setMaxLifePoints(thisHero.getMaxLifePoints() * 2);
                    break;
                case "mana":
                    thisHero.setMana(thisHero.getMana() * 2);
                    thisHero.setMaxMana(thisHero.getMaxMana() * 2);
                    break;
                case "arrows":
                    thisHero.setNbArrows(thisHero.getNbArrows() * 2);
                    thisHero.setNbMaxArrows(thisHero.getNbMaxArrows() * 2);
                    break;
            }
        }
        return ret;
    }

    private int GetNextHeroAliveIndex(int currentHero){
        int ret=-1;
        int currentToTest=currentHero;

        if (this.playerHeroes.size()>0) {
            while (ret == -1) {
                currentToTest = (currentToTest + 1) % this.playerHeroes.size();
                if (this.playerHeroes.get(currentToTest).getIsAlive()) {
                    ret = currentToTest;
                    break;
                }
                else if (currentToTest == currentHero)
                    break;
            }
        }
        return ret;
    }

    private int GetNextEnemyAliveIndex(int currentEnemy){
        int ret=-1;
        int currentToTest=currentEnemy;

        if (this.Enemies.size()>0){
            while (ret == -1){
                currentToTest = (currentToTest + 1) % this.Enemies.size();
                if (this.Enemies.get(currentToTest).getIsAlive()) {
                    ret = currentToTest;
                    break;
                } else if (currentToTest == currentEnemy)
                    break;
            }
        }
        return ret;
    }

    private boolean initGame() { // initialise Game
        return true;
    }
    private boolean heroAttackEnemy(int h, int e) { // this is the fight of hero h to enemy e
        boolean ret = false;
        Hero thisHero = this.playerHeroes.get(h); //thisHero h th hero
        Enemy thisEnemy = this.Enemies.get(e);
        GameParameters.HeroActionKind action = this.theDispatcherParser.AskAction(thisHero); //ask to the user which action depending on the hero
        switch(action){
            case EAT:
                thisHero.setNbFood(thisHero.getNbMaxFood()-1);
                thisHero.setLifePoints(Math.min(thisHero.getLifePoints()+10, thisHero.getMaxLifePoints()));
                ret = true;
                break;
            case DRINK:
                thisHero.setNbPotion(thisHero.getNbMaxPotion()-1);
                thisHero.setMana(Math.min(thisHero.getMana()+10, thisHero.getMaxMana()));
                ret = false;
                break;
            case ATTACK:
                switch (this.getClass().getSimpleName()){
                    case "Hunter":
                        thisHero.setNbArrows(thisHero.getNbArrows()-1);
                        break;
                    case "Mage":
                        thisHero.setMana(thisHero.getMana()-5);
                        break;
                }
                thisEnemy.heroAttacked(thisHero.getDamage());
                ret =  true;
                break;
            case DEFEND:
                thisHero.setMana(thisHero.getMana()-5);
                for (h=0; h<this.playerHeroes.size();h++){
                    Hero defendedHero = this.playerHeroes.get(h);
                    if (defendedHero.getLifePoints()>0){
                        defendedHero.setLifePoints(Math.min(defendedHero.getMaxLifePoints(), defendedHero.getLifePoints()+10));
                    }
                }
                ret = true;
                break;
            default:
                throw new IllegalStateException("Unexpected value of fight chosen by user: " + action);
        }

        return true;
    }
    private boolean computeNotDeads() { // find first not dead hero and enemy
        this.firstNotDeadEnemy = -1;
        this.firstNotDeadHero = -1;
        for (int i=0; i<this.playerHeroes.size();i++){ // compare length of list Hero
            Hero h=this.playerHeroes.get(i); //get the i th hero from the list
            if (h.getLifePoints() > 0) {
                this.firstNotDeadHero = i;
                break;
            }
        }
        for (int i=0; i<this.Enemies.size();i++){ // compare length of list Enemies
            Hero h=this.Enemies.get(i); //get the i th enemy from the list
            if (h.getLifePoints() > 0) {
                this.firstNotDeadEnemy = i;
                break;
            }
        }
        return true;
    }
    private boolean initHeroes() { // initialise list of instances of heroes
        for (int h = 0; h < this.nbPlayerHeroes; h++){
            String answer = this.theDispatcherParser.AKindOfDialogBox("Choix d'un héros", "Veuillez saisir le type de héros souhaité : Mage, Warrior, Healer, Hunter");
            System.out.println(answer);
            answer = answer.toLowerCase().trim(); // force the string in lower case because user could do enter upper case / lowercase mix/ trim removes leading or ending spaces
            switch(answer) {
                case "mage":
                    Mage theMage = new Mage(1.0f); //the hero chosen is added to playerHero. 1.0f <=> force type float of the number
                    this.playerHeroes.add(theMage);
                    break;
                case "warrior":
                    Warrior theWarrior = new Warrior(1.0f);
                    this.playerHeroes.add(theWarrior);
                    break;
                case "hunter":
                    Hunter theHunter = new Hunter(1.0f);
                    this.playerHeroes.add(theHunter);
                    break;
                case "healer":
                    Healer theHealer = new Healer(1.0f);
                    this.playerHeroes.add(theHealer); //add to the list HERO
                    break;
                default:
                    System.out.println("Ce type de héros n'existe pas !");
                    h--; // in this case we added nothing to the list so we have to do one more loop
                    break;
            }
        }
        this.theDispatcherParser.DisplayCurrentOpponents(this.playerHeroes, this.Enemies,0);
        return true;

    }
    private boolean initEnemies() { // initialise list of instances of heroes
        for (int h = 0; h < this.nbPlayerHeroes; h++){
            Enemy theEnemy = new Enemy(0.30f* (float)this.curRound);
            this.Enemies.add(theEnemy);
        }
        this.theDispatcherParser.DisplayCurrentOpponents(this.playerHeroes, this.Enemies,0);
        return true;
    }

    //private GameParameters.HeroActionKind RequestPlayerAction (Hero fighter) { // Function used during the fight to assess possible hero actions, and request user action to be done
    //    return true;
    //}

    private boolean playRound() { // heroes fight enemies
        return true;
    }
    private boolean playFight() { // detail of one hero one enemy fight
        return true;
    }
    private boolean quitGame() { // leave the game
        return true;
    }

    private boolean dumpGame() { // dump heroes and enemies status in console windows for testing debug purpose
        return true;
    }

}
