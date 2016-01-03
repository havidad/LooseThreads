package com.hypoxiagames.marioclone.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.hypoxiagames.marioclone.screens.GameScreen;

public class FPSCounter {
	public static boolean isShown;
	private static float unitScale = GameScreen.UNITSCALE;

	public FPSCounter(boolean shouldBeShown) {
		isShown = shouldBeShown;
	}

	public static void ShowCounter(BitmapFont font, Batch batch, GlyphLayout layout) {
		if (isShown) {
			font.setColor(0, 0, 255, 1);
			;
			font.getData().setScale(0.5f * unitScale);
			long fps = Gdx.graphics.getFramesPerSecond();
			layout.setText(font, String.valueOf(fps));
			font.draw(batch, layout, 100 * unitScale, 100 * unitScale);
		}

	}
}
