package com.mygdx.gameV2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


public class B_Ball extends Game {



	Preferences data;

	int cameraWidth;
	int cameraHeight;
	int ballSize = 100;
	SpriteBatch batch;
	Texture img;

	Texture T_backButton, T_shopIcon, T_ogBall, T_skinIcon, Menulogo, T_wallTexture, T_wallTexture2, T_wallTexture3, T_coin;

	public BitmapFont font;
	public float fontScale = 1.5f;

	float screenWidth, screenHeight;
	public Vector3 touchPos = new Vector3();

	int cash;

	SkinManager skin_Manager;

	@Override
	public void create () {

		font = new BitmapFont(Gdx.files.internal("arcade.fnt"),Gdx.files.internal("arcade.png"), false);
		font.setColor(Color.BLACK);
		font.getData().setScale(fontScale,fontScale);

		data = Gdx.app.getPreferences("SaveData");

		loadData();
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		cameraWidth = 800;
		cameraHeight  = (int)(cameraWidth/ screenWidth * screenHeight);


		this.skin_Manager = new SkinManager(this, data);
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		this.initializeTextures();
		MainScreen mainmenu = new MainScreen(this);
		this.setScreen(mainmenu);

	}
	private void loadData(){
		this.cash = data.getInteger( "cash",0);
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
		this.T_wallTexture = new Texture("testwall.jpg");
		this.T_wallTexture2 = new Texture("testwall2.jpg");
		this.T_wallTexture3 = new Texture("testwall3.jpg");
		this.T_backButton = new Texture("back_icon_1.png");
		this.T_ogBall = new Texture("ogball_icon.png");
		this.T_shopIcon = new Texture("shop_icon.png");
		this.T_skinIcon = new Texture("skin_icon.png");
		this.Menulogo = new Texture("Bounchie.png");
		this.T_coin = new Texture("coin.png");
	}
}
