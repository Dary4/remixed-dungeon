package com.watabou.pixeldungeon.items.scrolls;

import com.watabou.noosa.audio.Sample;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.buffs.Invisibility;
import com.watabou.pixeldungeon.actors.mobs.Boss;
import com.watabou.pixeldungeon.actors.mobs.Mob;
import com.watabou.pixeldungeon.actors.mobs.npcs.NPC;
import com.watabou.pixeldungeon.effects.Flare;
import com.watabou.pixeldungeon.effects.SpellSprite;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ScrollOfDomination extends Scroll {

	@Override
	protected void doRead() {

		SpellSprite.show( getUser(), SpellSprite.DOMINATION );
		Sample.INSTANCE.play( Assets.SND_DOMINANCE );
		Invisibility.dispel(getUser());
		
		ArrayList<Mob> mobsInSight = new ArrayList<>();
		
		for (Mob mob : Dungeon.level.getCopyOfMobsArray()) {
			if (Dungeon.level.fieldOfView[mob.getPos()] && !(mob instanceof Boss) && !mob.isPet() && !(mob instanceof NPC)) {
				mobsInSight.add(mob);
			}
		}
		
		while(!mobsInSight.isEmpty()) {
			Mob pet = Random.element(mobsInSight);

			if(pet.canBePet()) {
				Mob.makePet(pet, getUser());
				new Flare(3, 32).show(pet.getSprite(), 2f);
				break;
			}
			mobsInSight.remove(pet);
		}
		
		Dungeon.observe();
		
		setKnown();
		
		getUser().spendAndNext( TIME_TO_READ );
	}

	@Override
	public int price() {
		return isKnown() ? 80 * quantity() : super.price();
	}
}
