package impl.behaviour.minion;

import abstracts.Minion;
import inter.NotTargetedEffect;

/**
 * Class representing the special action "Taunt" used for the "Public Defender" (fr:Avocat commis d'office) & "Goldshire Footman" (fr:Soldat du comté-de-l'or) minions.
 * @author Raphaël Pagé & Henri Bouvet & Alexandre Melo & Glenn Plouhinec
 * @version 0.1
 */
public class Taunt extends NotTargetedEffect {

    private Minion myMinion;

    public Taunt(Minion myMinion) {
        this.myMinion = myMinion;
        myMinion.setHasTaunt(true);
    }

    @Override
    public void effect() {
    }

    public Minion getMyMinion() {
        return myMinion;
    }
}
