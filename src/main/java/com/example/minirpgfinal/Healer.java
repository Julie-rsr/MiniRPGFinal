package com.example.minirpgfinal;

public class Healer extends Hero {
    // Attributes


    // Constructor of Healer
    public Healer(float strengthFactor) {
        // Call Hero constructor function with tuned initial values
        super(strengthFactor, 50, 50, 0, 0, 1, 1, 0, 0, 15,15);
    }



    // Members functions
    // This function is used during the fight so that Console or GUI parsers will know what action to be proposed to the human playing the game
    public boolean HasThisCapacity(GameParameters.HeroActionKind actionKind) {
        boolean ret=false;

        switch(actionKind){
            case EAT:
                ret = false;
                break;
            case DRINK:
                ret = true;
                break;
            case ATTACK:
                ret =  false;
                break;
            case DEFEND:
                ret = true;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + actionKind);
        }
        return ret;
    }


}
