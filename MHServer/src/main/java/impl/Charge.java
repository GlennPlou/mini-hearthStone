package impl;

import inter.Target;
import inter.CardAction;
import abstracts.Minion;

/**
 * Class representing the special action "charge".
 * @author Raphaël Pagé & Henri Bouvet & Alexandre Melo & Glenn Plouhinec
 * @version 0.1
 */
public class Charge implements CardAction {

    private Minion myMinion;

    public Charge(Minion myMinion) {
        this.myMinion = myMinion;
    }

    public void effect(Target myTarget) {
        myMinion.setCanAttack(true);
    }
}