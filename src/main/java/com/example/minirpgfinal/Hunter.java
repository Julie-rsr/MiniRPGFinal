package com.example.minirpgfinal;

public class Hunter extends Hero {

    // Attributes
    private int damage; // damage per arrow


    // Constructor of Hunter
    public Hunter(float strengthFactor) {
        // Call Hero constructor function with tuned initial values
        super(strengthFactor, 50, 50, 1, 1, 0, 0, 3, 3, 0,0);
        this.damage = 15;
    }

    // Members functions
    // This function is used during the fight so that Console or GUI parsers will know what action to be proposed to the human playing the game
    public boolean HasThisCapacity(GameParameters.HeroActionKind actionKind) {
        boolean ret=false;

        switch(actionKind){
            case EAT:
                ret = true;
                break;
            case DRINK:
                ret = false;
                break;
            case ATTACK:
                ret =  true;
                break;
            case DEFEND:
                ret = false;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + actionKind);
        }
        return ret;
    }

}