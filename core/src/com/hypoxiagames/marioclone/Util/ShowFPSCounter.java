package com.hypoxiagames.marioclone.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class ShowFPSCounter {
	public static boolean isShown;
	public ShowFPSCounter(boolean shouldBeShown){
		isShown = shouldBeShown;
	}
	public static void ShowCounter(BitmapFont font, Batch batch, GlyphLayout layout){
		if(isShown){
			font.setColor(0, 0, 255, 1);;
			font.getData().setScale(0.5f);
			long fps = Gdx.graphics.getFramesPerSecond();
			layout.setText(font, String.valueOf(fps));
			font.draw(batch, layout, 5, 15);
		}
		
	}
}
