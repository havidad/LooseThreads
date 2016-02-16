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
	private static final Vector2 INHOMETP1 = new Vector2(25, 59.45f); // Upstairs															// point
	private static final Vector2 INHOMETP2 = new Vector2(26, 36.45f); // Downstairs
	private static final Vector2 INHOMEDOOR = new Vector2(36.8f, 36.8f); // Indoor Home Front Door

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

	public void wallBelow(Vector2 points) {
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
		try {
			if (collisionLayer.getCell((int) (points.x), (int) (points.y - 0.2f)).getTile().getProperties()
					.containsKey("isWall")) {
				player.canMoveDown = false;
				System.out.println(" Can't move down");
			} else
				player.canMoveDown = true;
		} catch (Exception e) {

		}
	}

	public void wallAbove(Vector2 points) {
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
		try {
			if (collisionLayer.getCell((int) (points.x), (int) (points.y - .1f)).getTile().getProperties()
					.containsKey("isWall")) {
				player.canMoveUp = false;
				System.out.println("Can't move up");
			} else
				player.canMoveUp = true;
		} catch (Exception e) {

		}
	}

	public void wallLeft(Vector2 points) {
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
		try {
			if (player.aHeld)
				player.canMoveLeft = true;
			if (collisionLayer.getCell((int) (points.x + 0.4f), (int) (points.y)).getTile().getProperties()
					.containsKey("isWall")) {
				player.canMoveLeft = false;
				System.out.println(" Can't move left");
			} else
				player.canMoveLeft = true;
		} catch (Exception e) {

		}
	}

	public void wallRight(Vector2 points) {
		TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
		try {
			if (collisionLayer.getCell((int) (points.x + 0.05f), (int) (points.y)).getTile().getProperties()
					.containsKey("isWall")) {
				player.canMoveRight = false;
				System.out.println(" Can't move right");
			} else {
				player.canMoveRight = true;

			}
		} catch (Exception e) {

		}
	}

	public void checkTeleportingZones(float posX, float posY) {
		System.out.println(
				"Position: " + "(" + posX + ", " + posY + ")" + ", Level Loaded: " + String.valueOf(screen.getLevel()));
		if (screen.getLevel() == 0) {
			if (((posX >= INHOMETP1.x) && (posX <= INHOMETP1.x + 0.5f)) && (((posY >= INHOMETP1.y - 0.3f) && (posY < 61)))) {
				player.disableMovement();
				player.getSprite().setPosition(27, 37);
			} else if (((posX >= INHOMETP2.x) && (posX <= INHOMETP2.x + 0.5f))
					&& (((posY >= INHOMETP2.y - 0.5f) && (posY < 38)))) {
				player.disableMovement();
				player.getSprite().setPosition(24.5f, 60f);
			}
			if ((int) posX >= INHOMEDOOR.x && (int) posY >= INHOMEDOOR.y) {
				screen.switchMaps(1);
				player.getSprite().setPosition(21, 29.5f);
			}
			// With the way the walls are drawn with the walls not the same with the
			// two rooms before it, we need
			// this check to make sure that the collision is correct based on which
			// wall there is.
			if (player.getSprite().getY() < 32)
				player.setInBedroom(false);
			else
				player.setInBedroom(true);

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
			} catch (Exception e) {
				// System.out.println("Not A Wall");
			}
			i++;
		}
	}

	public void switchMap(TiledMap map) {
		this.map = map;
	}
}
