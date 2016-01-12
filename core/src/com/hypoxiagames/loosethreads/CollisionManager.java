package com.hypoxiagames.loosethreads;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.hypoxiagames.loosethreads.entities.Monster;
import com.hypoxiagames.loosethreads.entities.Player;
import com.hypoxiagames.loosethreads.screens.GameScreen;

public class CollisionManager {
	Player player;
	Array<Monster> monsters;
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

	public CollisionManager(TiledMap map, Array<Monster> monsters, GameScreen screen) {
		this.map = map;
		this.monsters = monsters;
		this.screen = screen;
	}

	public boolean wallBelow(Vector2 points) {
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
		try {
			if (collisionLayer.getCell((int) (points.x), (int) (points.y - 0.05f)).getTile().getProperties()
					.containsKey("isWall"))
				return false;
			else
				return true;
		} catch (Exception e) {

		}
		return false;
	}

	public boolean wallAbove(Vector2 points) {
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
		try {
			if (collisionLayer.getCell((int) (points.x), (int) (points.y + 0.05f)).getTile().getProperties()
					.containsKey("isWall"))
				return false;
			else
				return true;
		} catch (Exception e) {

		}
		return false;
	}

	public boolean wallLeft(Vector2 points) {
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
		try {
			if (collisionLayer.getCell((int) (points.x - 0.05f), (int) (points.y)).getTile().getProperties()
					.containsKey("isWall"))
				return false;
			else
				return true;
		} catch (Exception e) {

		}
		return false;
	}
	
	public boolean wallRight(Vector2 points) {
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
		try {
			if (collisionLayer.getCell((int) (points.x + 0.05f), (int) (points.y)).getTile().getProperties()
					.containsKey("isWall"))
				return false;
			else
				return true;
		} catch (Exception e) {

		}
		return false;
	}

	public void checkWallCollision(Array<Vector2> collisionPoints) {
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
		int i = 0;

		// Check if any wall collision points are in a wall. If so, pushes them
		// back a little bit.
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
			if (player.getLocation().x > 12)
				screen.moveCamera(13, 0);

			i++;
		}

	}

}
