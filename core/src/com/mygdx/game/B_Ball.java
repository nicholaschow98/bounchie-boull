package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


public class B_Ball extends Game {

	int cameraWidth;
	int cameraHeight;
	SpriteBatch batch;
	Texture img;
	float screenWidth, screenHeight;
	public Vector3 touchPos = new Vector3();

	@Override
	public void create () {
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		cameraWidth = 800;
		cameraHeight  = (int)(800/ screenWidth * screenHeight);

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		MainScreen mainmenu = new MainScreen(this);
		this.setScreen(mainmenu);
	}

	@Override
	public void render () {
		batch.enableBlending();
		super.render();
	}

	@Override
	public void dispose () {

	}
}
