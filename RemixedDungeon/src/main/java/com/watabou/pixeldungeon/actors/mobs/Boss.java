package com.watabou.pixeldungeon.actors.mobs;

import com.nyrds.pixeldungeon.ai.AiState;
import com.nyrds.pixeldungeon.ai.Hunting;
import com.watabou.noosa.audio.Music;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.watabou.pixeldungeon.items.weapon.enchantments.Death;
import com.watabou.pixeldungeon.scenes.GameScene;

import androidx.annotation.Nullable;

abstract public class Boss extends Mob {

	private static final String BATTLE_MUSIC = "battleMusic";

	@Nullable
	private String battleMusic;

	public Boss() {
		RESISTANCES.add(Death.class);
		RESISTANCES.add(ScrollOfPsionicBlast.class);
		maxLvl = 50;
	}

	@Override
	public boolean canBePet() {
		return false;
	}

	@Override
	public void setState(AiState state) {
		if (state instanceof Hunting) {
			if (battleMusic != null) {
				Music.INSTANCE.play(battleMusic, true);
			}
		}
		super.setState(state);
	}

	@Override
	public void die(Object cause) {
		GameScene.playLevelMusic();
		GameScene.bossSlain();
		super.die(cause);
	}

	@Override
	protected void readCharData() {
		super.readCharData();
		battleMusic = getClassDef().optString(BATTLE_MUSIC, null);
	}
}
