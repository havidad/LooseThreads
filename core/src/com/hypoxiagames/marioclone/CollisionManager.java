package com.hypoxiagames.marioclone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.hypoxiagames.marioclone.entities.Player;
import com.hypoxiagames.marioclone.screens.GameScreen;

public class CollisionManager {
	GameScreen screen;

	// Handle the width and height of the tile.
	float tileHeight, tileWidth;
	static float unitScale = GameScreen.UNITSCALE;
	TiledMap map;

	public CollisionManager(GameScreen screen, TiledMap map) {
		this.screen = screen;
		this.map = map;
		tileHeight = screen.getLayer().getTileHeight();
		tileWidth = screen.getLayer().getTileWidth();

	}

	public void checkPlayerCollision(float x, float y) {
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
		int oldX = (int) x, oldY = (int) y;
		try {
			if (collisionLayer.getCell(oldX, oldY).getTile().getProperties().containsKey("isWall"))
					System.out.println("Hit A Wall");
			else System.out.println("Not A Wall"); 
		} catch (Exception e) {
			
		}

	}

}
