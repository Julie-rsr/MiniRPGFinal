package com.example.minirpgfinal;

public class Hero {
    // Attributes
    private int maxLifePoints ; // define max lifePoints for warrior at 50 points
    private int lifePoints; // number of lifePoints whatever the kind of hero, when it reaches 0 the hero is dead
    private int nbMaxFood;
    private int nbFood;

    private int nbMaxPotion;
    private int nbPotion;

    private int nbMaxArrows;
    private int nbArrows;

    private int maxMana;
    private int mana;

    private int damage;



    // Members functions
    // Getters and Setters
    public int getDamage(){return this.damage;}
    public void setDamage(int value) { this.damage=value; }

    public int getMaxMana() {
        return this.maxMana;
    }
    public void setMaxMana(int value) { this.maxMana=value; }

    public int getMana() {
        return this.mana;
    }
    public void setMana(int value) {
        this.mana=value;
    }

    public int getNbMaxArrows() {
        return this.nbMaxArrows;
    }
    public void setNbMaxArrows(int value) {
        this.nbMaxArrows=value;
    }

    public int getNbArrows() {
        return this.nbArrows;
    }
    public void setNbArrows(int value) {
        this.nbArrows=value;
    }

    public int getNbPotion() {
        return this.nbPotion;
    }
    public void setNbPotion(int value) {
        this.nbPotion=value;
    }

    public int getNbMaxPotion() {
        return this.nbMaxPotion;
    }
    public void setNbMaxPotion(int value) {
        this.nbMaxPotion=value;
    }

    public int getNbFood() {
        return this.nbFood;
    }
    public void setNbFood(int value) {
        this.nbFood=value;
    }

    public int getNbMaxFood() {
        return this.nbMaxFood;
    }
    public void setNbMaxFood(int value) {
        this.nbMaxFood=value;
    }

    public int getMaxLifePoints() { // function to get lifePoints of hero
        return this.maxLifePoints;
    }
    public void setMaxLifePoints(int value) { // function to get lifePoints of hero
        this.maxLifePoints=value;
    }

    public int getLifePoints() { // function to get lifePoints of hero
        return this.lifePoints;
    }
    public void setLifePoints(int value) {
        this.lifePoints=value;
    }

    //funcion used during the fight to know if heroes/enemies are still alived
    public boolean getIsAlive() { // function to know if hero is Alive
        return (this.lifePoints > 0); // alive if lifePoints > 0
    }

    public int heroAttacked(int damage) { // function to calculate new lifePoints when damage occurs
        this.lifePoints -= damage;
        return lifePoints; // new lifePoints (potentially dead)
    }
    public int heroRestored(int food) { // function to calculate new lifePoints when food taken
        this.lifePoints += food;
        return lifePoints; // new lifePoints
    }
    //Constructor : stores all attributes of all heroes (depending on its kind), strengthFactor is used to make enemies stronger over rounds
    public Hero(float strengthFactor, int maxLifePoints, int lifePoints, int nbMaxFood, int nbFood, int nbMaxPotion, int nbPotion, int nbMaxArrows, int nbArrows, int maxMana, int mana ) {
        // strengthFactor is used to differentiate relative strengths of fighters. For Heroes 100% for Enemies relative strength is set at 30% * round number for increasing difficulty
        this.maxLifePoints=(int)(strengthFactor * (float)maxLifePoints ) ; // define max lifePoints for warrior at 50 points
        this.lifePoints=(int)(strengthFactor * (float)lifePoints ); // number of lifePoints whatever the kind of hero, when it reaches 0 the hero is dead
        this.nbMaxFood=(int)(strengthFactor * (float)nbMaxFood );
        this.nbFood=(int)(strengthFactor * (float)nbFood );

        this.nbMaxPotion=(int)(strengthFactor * (float)nbMaxPotion );
        this.nbPotion=(int)(strengthFactor * (float)nbPotion );

        this.nbMaxArrows=(int)(strengthFactor * (float)nbMaxArrows );
        this.nbArrows=(int)(strengthFactor * (float)nbArrows );

        this.maxMana=(int)(strengthFactor * (float)maxMana );
        this.mana=(int)(strengthFactor * (float)mana );

        this.damage = (int)(strengthFactor * 15.0f );
    }
    //Retrieve String with attributes of hero
    public String getHeroAttributesString(){
        String ret = "";
        ret = ret + "MaxLifePoints = " + this.maxLifePoints + "\n\r";
        ret = ret + "LifePoints = " + this.lifePoints + "\n\r";
        return ret;
    }

    //return boolean to signify if the hero has a certain capacity depending on the kind of hero
    public boolean HasThisCapacity(GameParameters.HeroActionKind actionKind) {
        boolean ret = false;

        String test=this.getClass().getSimpleName();

        switch(this.getClass().getSimpleName()){

            case "Warrior":
                ret = ((Warrior)(this)).HasThisCapacity(actionKind);
                break;
            case "Hunter":
                ret = ((Hunter)(this)).HasThisCapacity(actionKind);
            break;
            case "Healer":
                ret = ((Healer)(this)).HasThisCapacity(actionKind);
            break;
            case "Mage":
                ret = ((Mage)(this)).HasThisCapacity(actionKind);
            break;
        }
        return ret;
    }

    public boolean canAttack(){
        boolean ret = false;

        switch(this.getClass().getSimpleName()){

            case "Warrior":
                ret = true;
                break;
            case "Hunter":
                ret = (((Hunter)this).getNbArrows() >= 1);
                break;
            case "Healer":
                ret = false;
                break;
            case "Mage":
                ret = (((Mage)this).getMana() >= 5);
                break;
        }
        return ret;

    }

}
