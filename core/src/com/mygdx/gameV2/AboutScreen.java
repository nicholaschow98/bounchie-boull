package com.mygdx.gameV2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import static java.lang.Math.abs;


/**
 * Created by Minha Lee on 12/11/2017.
 */

public class AboutScreen implements Screen{

    OrthographicCamera camera;
    B_Ball game;

    BitmapFont font;
    int fontScale = 2;

    Button backButton;

    Ball Ball;

    public AboutScreen(final B_Ball game){
        this.game = game;
        font = this.game.font;
        font.setColor(Color.BLACK);
        font.getData().setScale(fontScale,fontScale);
        camera = new OrthographicCamera(game.cameraWidth, game.cameraHeight);
        camera.setToOrtho(false, game.cameraWidth,game.cameraHeight);
        camera.update();
        initializeButtons();
        this.Ball = new Ball(game.cameraWidth/2+80, game.cameraHeight/8+80,game.ballSize,game.ballSize,0,0,game.skin_Manager.getBallSkin());
        this.Ball.xvel=0.001f;
    }

    private void initializeButtons(){
        backButton = new styleButton(1.5f,"BACK",game.cameraWidth*1/10, game.cameraHeight*7/8,game.skin_Manager.button_file_names);
    }

    public void pause(){
        //nothing
    }

    public void render(float delta){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        updateBall();
        if(Gdx.input.isTouched()){
            inputTouched();
        }
    //Start all rendering
        game.batch.begin();

        backButton.drawSelf(this.game.batch);
        font.draw(game.batch,"This is our sick game. \n I'll add more to this \n when I can \n Also please give us \n a 100 we are so tired \n \n special thanks to Nate \n for art contributions",100,900);

        Ball.drawSelf(this.game.batch);

        game.batch.end();
    //end rendering
    }

    private void inputTouched(){
        game.touchPos.set(Gdx.input.getX(), Gdx.input.getY(),0);
        camera.unproject(game.touchPos);

        Ball.xvel=Ball.xvel/abs(Ball.xvel)*10f;
        Ball.yvel = 25;

        if(backButton.checkPressed(game.touchPos)){
            game.setScreen(new MainScreen(this.game));
            backButton.dispose();
        }
    }

    public void updateBall(){
        Ball.updatePos();
        if(Ball.x+Ball.width>=game.cameraWidth){
            Ball.x = game.cameraWidth-Ball.width;
            Ball.xvel*=-1;
        }else if(Ball.x<0){
            Ball.x = 0;
            Ball.xvel*=-1;
        }
        if(Ball.y < 0){
            Ball.y = 0;
            Ball.yvel*= -0.5;
            Ball.xvel*=0.9;
            if(Ball.yvel > -0.5 && Ball.yvel < 0){
                Ball.yvel = 0;
            }
        }
        if(Ball.y > game.cameraHeight*3){
            Ball.y = 0;
            Ball.yvel = 0;
            game.skin_Manager.debugAll();
        }
        Ball.yvel -= 1.85f;

    }

    public void dispose(){
        //nothing
    }

    public void hide(){
        //nothing
    }

    public void resize(int width, int height){
        //nothing
    }

    public void show(){
        //nothing
    }

    public void resume(){
        //nothing
    }
}

