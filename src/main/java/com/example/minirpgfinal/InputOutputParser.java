package com.example.minirpgfinal;

import java.util.List;

public interface InputOutputParser {

    // output interface members
    public boolean DisplayCurrentOpponents(List<Hero>heroes,List<Enemy>enemies, int roundNb);     // used to display opponents
    public String AKindOfDialogBox(String title, String text);                      // used either Console or GUI to ask a value
    public String AKindOfConfirmDialogBox(String title, String text);               // used either Console or GUI to ask a value

    // input interface members
    public GameParameters.HeroActionKind AskAction(Hero hero);
}
