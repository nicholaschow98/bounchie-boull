package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Nick on 2017-11-29.
 */

public class GameScreen implements Screen {
    OrthographicCamera camera;
    B_Ball game;
    GameMode gamemode;

    BitmapFont font = new BitmapFont();

    public GameScreen(final B_Ball game){
        this.game = game;
        camera = new OrthographicCamera(game.cameraWidth, game.cameraHeight);
        camera.setToOrtho( false, game.cameraWidth,game.cameraHeight);
        gamemode= new Classic_GameMode(this.game);

    }
    public void pause(){

    }

    public  void render(float delta){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        gamemode.update();
        game.batch.begin();
        for(Wall wall : gamemode.get_Walls()){
            wall.drawSelf(this.game);
        }
        font.getData().setScale(3);
        font.setColor(Color.BLACK);
        font.draw(game.batch, ""+gamemode.getScore(), 300, 700);
        gamemode.get_Ball().drawSelf(this.game);
        game.batch.end();

        if(Gdx.input.isTouched()){
            game.touchPos.set(Gdx.input.getX(), Gdx.input.getY(),0);
            camera.unproject(game.touchPos);
            gamemode.touch_Update();
        }
    }
    public void hide(){

    }

    public void dispose(){
        game.batch.dispose();
        //game.img.dispose();
    }

    public void resize(int width, int height){

    }
    public void show(){

    }
    public void resume(){

    }

}
