package com.nyrds.pixeldungeon.ai;

import com.nyrds.pixeldungeon.ml.R;
import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.mobs.Mob;
import com.watabou.pixeldungeon.utils.Utils;

public class Hunting extends MobAi implements AiState {

    public Hunting() { }

    @Override
    public void act(Mob me) {

        if(returnToOwnerIfTooFar(me, 4)) {
            return;
        }

        if(me.getEnemy() == Char.DUMMY) {
            me.setEnemy(chooseEnemy(me));
        }

        if(me.friendly(me.getEnemy())) {
            me.setState(getStateByClass(Wandering.class));
            return;
        }

        me.enemySeen = me.isEnemyInFov();

        if (me.enemySeen && me.canAttack(me.getEnemy())) {
            me.doAttack(me.getEnemy());
        } else {
            if (me.enemySeen) {
                me.target = me.getEnemy().getPos();
            }

            if(!me.doStepTo(me.target)) {
                me.target = Dungeon.level.randomDestination();
                me.setState(getStateByClass(Wandering.class));
            }
        }
    }

    @Override
    public String status(Mob me) {
        if (me.getEnemy().valid()) {
            return Utils.format(Game.getVar(R.string.Mob_StaHuntingStatus2),
                    me.getName(), me.getEnemy().getName_objective());
        }
        return Utils.format(Game.getVar(R.string.Mob_StaHuntingStatus),
                me.getName());
    }

    @Override
    public void gotDamage(Mob me, Object src, int dmg) {
        if(!me.isEnemyInFov()) {
            seekRevenge(me,src);
        }
    }

}
