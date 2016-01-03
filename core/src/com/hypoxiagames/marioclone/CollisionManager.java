package com.hypoxiagames.marioclone;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.hypoxiagames.marioclone.screens.GameScreen;

public class CollisionManager {
	GameScreen screen;
	
	// Handle the width and height of the tile.
	float tileHeight, tileWidth;
	
	public CollisionManager(GameScreen screen){
		this.screen = screen;
		tileHeight = screen.getLayer().getTileHeight();
		tileWidth = screen.getLayer().getTileWidth();
		
	}

}
