package com.hypoxiagames.loosethreads.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.hypoxiagames.loosethreads.screens.GameScreen;

public class Entity {
	float unitScale = GameScreen.UNITSCALE;
	float speed = 255 * unitScale;

	float stateTime;
	float animationSpeed;
	TextureAtlas animationTexture;

	// Defines a default amount of health for the average Entity
	// This value will change based on what level the character/Monster is.
	float currentHealth = 50;
	float maxHealth = 50;

	// Default attack value for hands. Getting punched will deal 2 damage,
	// depending on other stats this will go up.
	float attack = 2;

	// Holds location information for Entity
	protected Vector2 location;

	private static xDir xDirection;
	private static yDir yDirection;

	public Entity(String type) {
		animationSpeed = 1 / 12f;
	}

	public enum xDir {
		left, right, none
	}

	public enum yDir {
		up, down, none
	}

	// Player animation updates are done here.
	public void updateAnimation(Player player, float delta) {
		stateTime += delta;
		player.setAnimation(new Animation(1 / 4f, player.getRegion("Down")));
		if (player.getyDirection() == yDir.down || player.issHeld())
			player.setAnimation(new Animation(animationSpeed, player.getRegion("Down")));
		if (xDirection == xDir.right || player.isdHeld())
			player.setAnimation(new Animation(animationSpeed, player.getRegion("Right")));
		if (xDirection == xDir.left || player.isaHeld())
			player.setAnimation(new Animation(animationSpeed, player.getRegion("Left")));
		if (yDirection == yDir.up || player.iswHeld())
			player.setAnimation(new Animation(animationSpeed, player.getRegion("Up")));

		player.setCurrentFrame(player.getAnimation().getKeyFrame(stateTime, true));
	}

	public void updateAnimation(Monster monster, float delta) {

	}

	public TextureAtlas getAnimationTexture() {
		return animationTexture;
	}

	public void setAnimationTexture(TextureAtlas animationTexture) {
		this.animationTexture = animationTexture;
	}

	public Vector2 getLocation() {
		return location;
	}

	public void setLocation(Vector2 location) {
		this.location = location;
	}

	public float getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(float currentHealth) {
		this.currentHealth = currentHealth;
	}

	public float getMaxHealth() {
		return maxHealth;
	}
}
