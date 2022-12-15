package com.example.minirpgfinal;

public class GameParameters {
    // different possible actions of hero (depending it is a warrior, healer ...
    // each subclasses of heroes will use this enum to mention its capacities to game (fight engine)
    enum HeroActionKind {
        EAT,
        DEFEND,
        ATTACK,
        DRINK,
        NONE
    }
}
