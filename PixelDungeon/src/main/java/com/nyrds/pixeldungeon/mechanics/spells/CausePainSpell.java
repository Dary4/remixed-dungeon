package com.nyrds.pixeldungeon.mechanics.spells;

import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.blobs.Blob;
import com.watabou.pixeldungeon.actors.blobs.Fire;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.mechanics.Ballistica;
import com.watabou.pixeldungeon.scenes.GameScene;

public class CausePainSpell extends Spell{

	CausePainSpell() {
		targetingType = SpellHelper.TARGET_CELL;
		magicAffinity = SpellHelper.AFFINITY_NECROMANCY;

		level = 3;
		imageIndex = 1;
		spellCost = 5;
	}

	@Override
	public boolean cast(Char chr, int cell){
		if(Dungeon.level.cellValid(cell)) {
			if(Ballistica.cast(chr.getPos(), cell, false, true) == cell) {
				//GameScene.add( Blob.seed( cell, 5, Fire.class ) );
				if(chr instanceof Hero) {
					Hero hero = (Hero) chr;
					castCallback(hero);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public String texture(){
		return "spellsIcons/necromancy.png";
	}
}
