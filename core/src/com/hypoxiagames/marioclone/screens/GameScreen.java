package com.hypoxiagames.marioclone.screens;

import com.hypoxiagames.marioclone.*;
import com.hypoxiagames.marioclone.entities.Player;
import com.hypoxiagames.marioclone.input.PlayerInput;
import com.hypoxiagames.marioclone.input.TitleInput;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen implements com.badlogic.gdx.Screen {
	final MainGame game;
	Assets assetManager;
	SpriteBatch batch;
	GlyphLayout glyphLayout;
	
	private Player player;
	
	private TiledMap testMap;
	private OrthogonalTiledMapRenderer renderer;
	private StaticTiledMapTile staticTile;
	private MapObjects groundObjects, wallObjects;
	private OrthographicCamera camera;
	
	final float GAME_WORLD_WIDTH = 480, GAME_WORLD_HEIGHT = 360;
	final float ASPECT_RATIO;
	
	public GameScreen(final MainGame gam){
		game = gam;
		glyphLayout = new GlyphLayout();
		
		ASPECT_RATIO = GAME_WORLD_HEIGHT * GAME_WORLD_WIDTH;
	
		testMap = new TmxMapLoader().load("Maps/TestMap.tmx");
		renderer =  new OrthogonalTiledMapRenderer(testMap);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GAME_WORLD_WIDTH * ASPECT_RATIO, GAME_WORLD_HEIGHT);
		
		player = new Player(new Sprite(new Texture("Sprites/Finn.png")), 
				(TiledMapTileLayer)testMap.getLayers().get("Player"));
		player.setPosition(2, 71);
		
		//Set collision points for player
		setCollisionMap();
		
		Gdx.input.setInputProcessor(player);
		
	}

	public void setCollisionMap(){
		try{
			groundObjects = testMap.getLayers().get(2).getObjects();
			wallObjects = testMap.getLayers().get(3).getObjects();
		}catch (ExceptionInInitializerError e){
			System.out.println("We can't load the collision Layer");
			game.exit();
		}
	}
	
	public boolean checkCollision(MapObjects objects){
		boolean collided = false;
		for(RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)){
			if(collided)
				return true;
			Rectangle rect = rectangleObject.getRectangle();
			if(Intersector.overlaps(rect, new Rectangle(player.getX(),player.getY()
					,player.getWidth(), player.getHeight())))
					collided = true;
			else
				collided = false;
		}
		return false;
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		player.update(delta);
		if(checkCollision(groundObjects))
			player.collidedGround = true;
		else
			player.collidedGround = false;
		if(checkCollision(wallObjects))
			player.collidedWall = true;
		else 
			player.collidedWall = false;
		renderer.setView(camera);
		renderer.render();
		renderer.getBatch().begin();
		player.draw(renderer.getBatch());
		renderer.getBatch().end();

	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
		camera.update();
		
	}

	@Override
	public void pause() {
				
	}

	@Override
	public void resume() {
		
		
	}

	@Override
	public void hide() {
		dispose();
		
	}

	@Override
	public void dispose() {
		testMap.dispose();
		renderer.dispose();
		
	}

	public TiledMap getTestMap() {
		return testMap;
	}

	public void setTestMap(TiledMap testMap) {
		this.testMap = testMap;
	}

	public MapObjects getObjects(MapObjects objects) {
		return objects;
	}

}
