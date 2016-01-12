package com.hypoxiagames.loosethreads;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.hypoxiagames.loosethreads.entities.Player;
import com.hypoxiagames.loosethreads.screens.GameScreen;

public class CollisionManager {
	Player player;
	GameScreen screen;

	// Handle the width and height of the tile.
	float tileHeight, tileWidth;
	static float unitScale = GameScreen.UNITSCALE;
	TiledMap map;

	public CollisionManager(TiledMap map, Player player, GameScreen screen) {
		this.map = map;
		this.player = player;
		this.screen = screen;

	}

	public void checkWallCollision(Array<Vector2> collisionPoints) {
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
		int i = 0;
		for (Vector2 points : collisionPoints) {
			int pointX = (int) points.x;
			int pointY = (int) points.y;
			try {
				if (collisionLayer.getCell(pointX, pointY).getTile().getProperties().containsKey("isWall"))
					switch (i) {
					case 0:
						System.out.println("Feet hit wall");
						player.setY(player.getLocation().y + 0.1f);
						
						break;
					case 1:
						System.out.println("Head hit wall");
						player.setY(player.getLocation().y - 0.1f);
						break;
					case 2:
						System.out.println("Left Side Hit wall");
						player.setX(player.getLocation().x + 0.1f);
						break;
					case 3:
						System.out.println("Right Side Hit Wall");
						player.setX(player.getLocation().x - 0.1f);
						break;
					}
			} catch (Exception e) {
				// System.out.println("Not A Wall");
			}
			if(player.getLocation().x > 12)
				screen.moveCamera(5, 0);
				
			i++;
		}

	}

}
