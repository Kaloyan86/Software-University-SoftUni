package B02_Command.inplementations;

import B02_Command.abstracts.AbstractHero;
import B02_Command.interfaces.Target;
import B02_Command.abstracts.Logger;

public class Warrior extends AbstractHero {

    private static final String ATTACK_MESSAGE = "%s damages %s for %s";

    public Warrior(String id, int dmg) {
        super(id, dmg);
    }


    @Override
    protected void executeClassSpecificAttack(Target target, int dmg) {
        System.out.println(String.format(ATTACK_MESSAGE, this, target, dmg));
        target.receiveDamage(dmg);
    }
}
