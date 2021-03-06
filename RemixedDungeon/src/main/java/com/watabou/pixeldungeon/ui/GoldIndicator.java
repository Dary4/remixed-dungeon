/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.watabou.pixeldungeon.ui;

import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Game;
import com.watabou.noosa.ui.Component;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.RemixedDungeon;
import com.watabou.pixeldungeon.scenes.PixelScene;
import com.watabou.pixeldungeon.windows.elements.Tool;

public class GoldIndicator extends Component {

	private static final float TIME	= 2f;
	
	private int lastValue = 0;

	private BitmapText tf;
	
	private float time;

	@Override
	protected void createChildren() {
		tf = new BitmapText( PixelScene.font1x );
		tf.hardlight( 0xFFFF00 );
		add( tf );
		
		setVisible(false);
	}
	
	@Override
	protected void layout() {
		tf.x = x + (width - tf.width()) / 2;
		tf.y = bottom() - tf.height();
	}
	
	@Override
	public void update() {
		super.update();
		
		if (getVisible()) {
			
			time -= Game.elapsed;
			if (time > 0) {
				tf.alpha( time > TIME / 2 ? 1f : time * 2 / TIME );
			} else {
				setVisible(false);
			}
			
		}
		
		if (Dungeon.hero.gold() != lastValue && !RemixedDungeon.toolStyle().equals(Tool.Size.Tiny.name())) {
			
			lastValue = Dungeon.hero.gold();
			
			tf.text( Integer.toString( lastValue ) );

			setVisible(true);
			time = TIME;
			
			layout();
		}
	}
}
