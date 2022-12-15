package com.example.minirpgfinal;

//Class that define the attributes of enemies (specific hero)
public class Enemy extends Hero {


    private int damage;

    // Constructor of Enemy
    public Enemy(float strengthFactor) {
        super(strengthFactor, 50, 50, 1, 1, 0, 0, 0, 0, 0, 0);
        this.damage = 15;
    }
}
