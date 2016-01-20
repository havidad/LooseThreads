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
			if (collisionLayer.getCell((int) (points.x), (int) (points.y - 0.02f)).getTile().getProperties()
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
			if (collisionLayer.getCell((int) (points.x), (int) (points.y + 0.03f)).getTile().getProperties()
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
			if (collisionLayer.getCell((int) (points.x - 0.02f), (int) (points.y)).getTile().getProperties()
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
			if (collisionLayer.getCell((int) (points.x + 0.02f), (int) (points.y)).getTile().getProperties()
					.containsKey("isWall"))
				return false;
			else
				return true;
		} catch (Exception e) {

		}
		return false;
	}

	public void checkTeleportingZones(float posX, float posY) {
		Vector2 teleport1 = new Vector2(25, 60);
		Vector2 teleport2 = new Vector2(26, 37);
		System.out.println("(" + posX + ", " + posY + ")");
		if (((posX >= teleport1.x) && (posX <= 25.5)) && (((posY >= teleport1.y) && (posY < 60.2)))) {
			player.disableMovement();
			player.getSprite().setPosition(27, 37);
		} else if (((posX >= teleport2.x) && (posX < 26.3)) && (((posY >= teleport2.y) && (posY < 37.2)))) {
			player.disableMovement();
			player.getSprite().setPosition(24.5f, 60f);
		}
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
						player.getSprite().setY(player.getLocation().y + 0.1f);

						break;
					case 1:
						System.out.println("Head hit wall");
						player.getSprite().setY(player.getLocation().y - 0.1f);
						break;
					case 2:
						System.out.println("Left Side Hit wall");
						player.getSprite().setX(player.getLocation().x + 0.1f);
						break;
					case 3:
						System.out.println("Right Side Hit Wall");
						player.getSprite().setX(player.getLocation().x - 0.1f);
						break;
					}
				if (player.getSprite().getX() == 24 && player.getSprite().getY() == 60) {
					System.out.println("Should be teleporting");
					player.moveToPoint(26, 38);
				}
			} catch (Exception e) {
				// System.out.println("Not A Wall");
			}
			i++;
		}

	}

}
