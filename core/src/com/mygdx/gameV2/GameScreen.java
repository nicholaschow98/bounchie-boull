package com.mygdx.gameV2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Nick on 2017-11-29.
 */

public class GameScreen implements Screen {
    OrthographicCamera camera;
    B_Ball game;
    GameMode gamemode;

    BitmapFont font;

    Button backButton;

    public GameScreen(final B_Ball game, String mode){
        this.game = game;
        camera = new OrthographicCamera(game.cameraWidth, game.cameraHeight);
        camera.setToOrtho( false, game.cameraWidth,game.cameraHeight);
        backButton = new Button(game.cameraWidth*2/8,game.cameraHeight*5/8, game.T_backButton);
        this.font = this.game.font;
        if(mode.equals("Classic")){
            gamemode = new Classic_GameMode(this.game);
        }
        else if(mode.equals("Staggered")){
            gamemode = new Staggered_GameMode(this.game);
        }
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
            wall.drawSelf(this.game.batch);
        }
        font.getData().setScale(7);
        font.setColor(Color.BLACK);

        gamemode.get_Ball().drawSelf(this.game.batch);
        if(gamemode.getLose()==true){
            game.batch.draw(game.img,game.cameraWidth/8,game.cameraHeight*2/3,game.cameraWidth*6/8,game.cameraHeight/4);
            backButton.activate();
            backButton.drawSelf(this.game.batch);
        }
        else{
            backButton.deactivate();
        }
        font.draw(game.batch, ""+gamemode.getScore(), game.cameraWidth/2-font.getSpaceWidth()/2, game.cameraHeight*7/8);//-font.getSpaceWidth()/2
        game.batch.end();

        if(Gdx.input.isTouched()){
            game.touchPos.set(Gdx.input.getX(), Gdx.input.getY(),0);
            camera.unproject(game.touchPos);

            if(backButton.checkPressed(game.touchPos)){
                game.setScreen(new MainScreen(game));
                this.dispose();
            }
            gamemode.touch_Update();
        }
        font.getData().setScale(2);
    }
    public void hide(){

    }

    public void dispose(){
        backButton.dispose();
        //gamemode.dispose();//NEED TO IMPLEMENT
        //game.batch.dispose();
        //game.img.dispose();
    }

    public void resize(int width, int height){

    }
    public void show(){

    }
    public void resume(){

    }

}
