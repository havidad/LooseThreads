package com.hypoxiagames.marioclone.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.hypoxiagames.marioclone.input.PlayerInput;
import com.hypoxiagames.marioclone.screens.GameScreen;

public class Player extends Sprite implements InputProcessor {

	// Player movement velocity 
	public Vector2 velocity = new Vector2(0,0);
	
	// Change these values to change different parameters for the characters movement in the world
	private float speed = 45 * 2, gravity = 85 * 1.0f;
	
	// Used to see if a player can land on this a secific tile
	private TiledMapTileLayer collisionLayer;
	
	PlayerInput input;
	
	
	public boolean collidedGround, collidedWall;

	public Player(Sprite sprite, TiledMapTileLayer collisionLayer){
		super(sprite);
		this.collisionLayer = collisionLayer;
	}
	@Override
	public void draw(Batch spriteBatch){
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}
	
	public void update(float delta){
		// Applying gravity
		velocity.y -= gravity * delta;
		
		// Limits the player to only going too fast.
		if(velocity.y > speed)
			velocity.y = speed;
		else if(velocity.y < -speed)
			velocity.y = -speed;
		
		if(velocity.x > speed)
			velocity.x = speed;
		else if(velocity.x < -speed)
			velocity.x = -speed;
			
			
		
		// Save old position
		float oldX = getX(), oldY = getY(); 
		float tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
		
		//Move on X Axis
		setX(getX() + velocity.x * delta);
		// Stop moving for xcollision
		if(collidedWall){
			setX(oldX);
			velocity.x = 0;
		}
		
		//Move on Y Axis
		setY(getY() + velocity.y * delta);
		// Stop moving Y collision
		if(collidedGround){
			setY(oldY);
			velocity.y = 0;
		}
		
	}

	
	public Vector2 getVelocity() {
		return velocity;
	}
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public float getGravity() {
		return gravity;
	}
	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}
	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode){
		case Keys.UP:
			break;
		case Keys.DOWN:
			break;
		case Keys.LEFT:
			if(!collidedWall)
				velocity.x -= speed;
			break;
		case Keys.RIGHT:
			if(!collidedWall)
				velocity.x += speed;
			break;
		}
		return true;
	}
	@Override
	public boolean keyUp(int keycode) {
		switch(keycode){
		case Keys.UP:
			break;
		case Keys.DOWN:
			break;
		case Keys.LEFT:
		case Keys.RIGHT:
			velocity.x = 0;
			break;
		}
		return true;
	}
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
