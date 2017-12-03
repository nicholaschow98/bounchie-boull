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

	Texture T_backButton;
	Texture T_shopIcon;
	Texture T_ogBall;
	Texture T_skinIcon;


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
		this.initializeTextures();
	}

	@Override
	public void render () {
		batch.enableBlending();
		super.render();
	}

	@Override
	public void dispose () {

	}

	private void initializeTextures(){

		T_backButton = new Texture("back_icon_1.png");
		T_ogBall = new  Texture("ogball_icon.png");
		T_shopIcon = new Texture("shop_icon.png");
		T_skinIcon = new Texture("skin_icon.png");
	}
}
