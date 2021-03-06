package impl.behaviour.generic.notTargetedEffect;

import abstracts.NotTargetedEffect;
import impl.ConcreteMinion;
import impl.ConcreteSpell;
import impl.Player;
import inter.Target;

import java.util.ArrayList;

/**
 * Class representing the special action "DamageAllMinions" used for the "Whirlwind" (fr:Tourbillon)
 * spell. Deal 1 damage to ALL minions (including yours).
 *
 * @author Raphaël Pagé & Henri Bouvet & Alexandre Melo & Glenn Plouhinec
 * @version 0.1
 */
public class DamageAllMinions extends NotTargetedEffect {

  private ConcreteSpell mySpell;
  private int damage;

  public DamageAllMinions(ConcreteSpell mySpell, int damage) {
    this.mySpell = mySpell;
    this.damage = damage;
  }

  @Override
  public void effect() {

    Player myPlayer = mySpell.getPlayer();
    Player myOpponent = myPlayer.getOpponent();
    ArrayList<ConcreteMinion> hisMinions = myOpponent.getMyMinions();
    ArrayList<ConcreteMinion> myMinions = myPlayer.getMyMinions();

    ArrayList<Target> myAdversaries = new ArrayList<Target>(hisMinions);
    myAdversaries.addAll(myMinions);

    for (Target target : myAdversaries) {

      target.takeDamage(damage);
    }
  }
}
