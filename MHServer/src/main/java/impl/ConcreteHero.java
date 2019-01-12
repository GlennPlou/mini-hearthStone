package impl;

import abstracts.CardType;
import impl.behaviour.generic.notTargetedEffect.ModifyArmor;
import impl.behaviour.generic.notTargetedEffect.Summon;
import impl.behaviour.generic.targetedEffect.DamageTarget;
import inter.Effect;
import inter.NotTargetedEffect;
import inter.Target;
import org.springframework.data.annotation.Id;

import java.util.Map;

public class ConcreteHero implements Target {

    /**
     * The ID of this ConcreteHero.
     */
    @Id
    private String id;

    /**
     * the url of the heros picture
     */
    private String imgurl;

    /**
     * The type of the hero (mage, warrior or paladin)
     */
    private CardType heroType;

    /**
     * The name of the hero
     */
    private String heroName;

    /**
     * Indicates the maximum number of health points that this Hero has.
     */
    private int maxHealthPoints;

    /**
     * Indicates the current number of health points that this Hero has.
     */
    private int currentHealthPoints;

    /**
     * Indicates the number of armor points that this Hero has.
     */
    private int armorPoints;

    /**
     * Reference the list of actions or behaviors of this card.
     */
    private Effect myEffect;

    private Player myPlayer;
    private Map<String,String> abilityKeyWord;
    private String powerImgName;
    private String powerImgText;
    private String powerImgUrl;
    private boolean canUseHeroAbility;

    public ConcreteHero() {}


    public ConcreteHero(CardType heroType, int maxHealthPoints, int armorPoints, Map<String,String> abilityKeyWord, String heroName, String imgurl) {
        super();

        this.heroType = heroType;
        this.armorPoints = armorPoints;
        this.maxHealthPoints = maxHealthPoints;
        this.currentHealthPoints = maxHealthPoints;
        this.abilityKeyWord = abilityKeyWord;
        this.heroName = heroName;
        this.imgurl = imgurl;
        this.canUseHeroAbility = true;

        generateEffect(abilityKeyWord);

    }


    public void setId(String id) {
        this.id = id;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }


    public CardType getHeroType() {
        return heroType;
    }

    public void setHeroType(CardType heroType) {
        this.heroType = heroType;
    }

    public String getId() {
        return id;
    }

    public int getCurrentHealthPoints() {
        return currentHealthPoints;
    }

    public void setCurrentHealthPoints(int hp) {
        this.currentHealthPoints = hp;
    }

    public int getArmorPoints() {
        return armorPoints;
    }

    public void setArmorPoints(int armor) {
        this.armorPoints = armor;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public Effect getMyEffect() {
        return myEffect;
    }

    private void setMyEffect(Effect myEffect) {
        this.myEffect = myEffect;
    }

    public Player getMyPlayer() {
        return myPlayer;
    }

    public void setMyPlayer(Player myPlayer) {
        this.myPlayer = myPlayer;
    }

    public void setCanUseHeroAbility(boolean canUseHeroAbility) {
        this.canUseHeroAbility = canUseHeroAbility;
    }

    public String getPowerImgUrl() {
        return powerImgUrl;
    }

    public void setPowerImgUrl(String powerImgUrl) {
        this.powerImgUrl = powerImgUrl;
    }

    public String getPowerImgName() {
        return powerImgName;
    }

    public void setPowerImgName(String powerImgName) {
        this.powerImgName = powerImgName;
    }

    public String getPowerImgText() {
        return powerImgText;
    }

    public void setPowerImgText(String powerImgText) {
        this.powerImgText = powerImgText;
    }

    public boolean isCanUseHeroAbility() {
        return canUseHeroAbility;
    }

    /**
     * allows to generate the effect of a hero
     * the abilities of the heroes are stored using a Map in the database in the form <key:value> where key is the
     * ability keyword and value is its modifier
     * @param abilityKeyWord a map containing the keywords of the abilities of the hero
     */
    public void generateEffect(Map<String,String> abilityKeyWord){

        for (Map.Entry<String, String> entry : abilityKeyWord.entrySet()) {

            switch(entry.getKey()) {

                case "damageTarget":
                    DamageTarget abilityDamage = new DamageTarget(Integer.parseInt(entry.getValue()));
                    this.setMyEffect(abilityDamage);
                    break;
                case "modifyArmor" :
                    ModifyArmor abilityArmor = new ModifyArmor(this, Integer.parseInt(entry.getValue()));
                    this.setMyEffect(abilityArmor);
                    break;
                case "summon":
                    Summon abilitySummon = new Summon(entry.getValue());
                    this.setMyEffect(abilitySummon);
                    break;
                default:
                    break;
            }
        }
    }

    public Map<String,String> getAbilityKeyWord() {
        return abilityKeyWord;
    }

    public void setAbilityKeyWord(Map<String,String> abilityKeyWord) {
        this.abilityKeyWord = abilityKeyWord;
    }

    @Override
    public void setMaxHealthPoints(int healthPoints) {
        this.maxHealthPoints = healthPoints;
    }

    /**
     * Allows a hero to activate its hero power
     */
    public void activateEffect(Target target) {
        if (canUseHeroAbility()) {
            if (this.getMyEffect() instanceof NotTargetedEffect) {
                this.getMyEffect().effect();
            } else {
                this.getMyEffect().effect(target);
            }
            setCanUseHeroAbility(false);
        }
    }

    @Override
    public void addDamagePoints(int bonusDamage) {}

    /**
     * add the value in parameter to the maxHealthPoints of the hero
     * and to the currentHealthPoints of the hero
     * @param bonusHealthPoints the healthpoints that are added to the heros maximum healthpoints
     */
    @Override
    public void addMaxHealthPoints(int bonusHealthPoints) {
        this.maxHealthPoints += bonusHealthPoints;
        this.currentHealthPoints += bonusHealthPoints;
    }

    /**
     * get the max health point of the hero
     * @return the maximum health points the hero can have
     */
    @Override
    public int getMaxHealthPoints() {
        return this.maxHealthPoints;
    }

    /**
     * {@inheritDoc}
     */
    public int takeDamage(int damageTaken) {
        if (damageTaken >= 0) {
            if (damageTaken < this.armorPoints) {
                this.armorPoints -= damageTaken;
                damageTaken = 0;
            } else {
                this.currentHealthPoints = this.currentHealthPoints + this.armorPoints - damageTaken;
                damageTaken = damageTaken - this.armorPoints;
                this.armorPoints = 0;
            }

            if (this.isDead())
                this.dies();

            return damageTaken;
        } else {
            return 0;
        }
    }

    /**
     * Allows to give a few health points back to a living hero.
     *
     * @param healingPoints the number of health points to be returned.
     */
    @Override
    public void heal(int healingPoints) {
        if (healingPoints >= 0) {
            long sum = (long)this.currentHealthPoints + (long)healingPoints;
            int hp;
            if (sum >= Integer.MAX_VALUE) {
                hp = Math.min(Integer.MAX_VALUE, this.maxHealthPoints);
            } else {
                hp  = (int)Math.min(sum, (long) this.maxHealthPoints);
            }
            this.currentHealthPoints = hp;
        }
    }

    public void addArmor(int armor) {
        this.armorPoints += armor;
    }

    /**
     * check if the hero is dead
     *
     * @return true if the hero is dead
     */
    public boolean isDead() {

        return currentHealthPoints <= 0;

    }

    /**
     * what happens when the hero dies
     */
    public void dies() {

        myPlayer.lost();


    }

    /**
     * return a boolean which said if the hero ability can be used
     *
     * @return true if the player can use its hero power
     */
    public boolean canUseHeroAbility() {
        if (myPlayer.getMyMana() >= 2) {
            return canUseHeroAbility;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format(
                "Hero[id=%s, heroName='%s', maxHealthPoints='%s', armorPoints='%s']",
                id, heroName, maxHealthPoints, armorPoints);
    }

}
