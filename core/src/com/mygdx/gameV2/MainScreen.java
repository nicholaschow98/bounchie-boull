package com.mygdx.gameV2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static java.lang.Math.abs;


/**
 * Created by Nick on 2017-11-29.
 */

public class MainScreen implements Screen {

    OrthographicCamera camera;
    B_Ball game;

    BitmapFont font;

    final int num_of_Buttons = 4;
    Button gameButton, shopButton, skinButton, aboutButton;
    Button buttons[] = new Button[num_of_Buttons];

    Ball Ball;

    private Texture logo;

    public MainScreen(final B_Ball game){
        this.game = game;
        this.font  = this.game.font;
        camera = new OrthographicCamera(game.cameraWidth, game.cameraHeight);
        camera.setToOrtho(false, game.cameraWidth,game.cameraHeight);
        camera.update();
        initializeButtons();
        logo = game.Menulogo;
        this.Ball = new Ball(game.cameraWidth/2+80, game.cameraHeight/8+80,game.ballSize,game.ballSize,0,0,game.skin_Manager.getBallSkin());
        this.Ball.xvel=0.001f;

    }

    private void initializeButtons(){
        gameButton = new styleButton(2,"PLAY",game.cameraWidth*1/20, game.cameraHeight*10/32,game.skin_Manager.button_file_names);
        shopButton =  new styleButton(2,"SHOP",game.cameraWidth*1/20, game.cameraHeight*7/32,game.skin_Manager.button_file_names);
        skinButton =  new styleButton(2,"SKINS",game.cameraWidth*1/20, game.cameraHeight*4/32,game.skin_Manager.button_file_names);
        aboutButton = new styleButton(2, "ABOUT", game.cameraWidth* 1/20, game. cameraHeight * 1/32, game.skin_Manager.button_file_names);
        buttons[0] = gameButton;
        buttons[1] = shopButton;
        buttons[2] = skinButton;
        buttons[3] = aboutButton;
    }

    public void pause(){

    }

    public  void render(float delta){
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
        game.batch.draw(logo, 0, game.cameraHeight*1/2-100, game.cameraWidth, game.cameraWidth);
        for(Button button: buttons){
            button.drawSelf(this.game.batch);
        }
        font.getData().setScale(1.5f,1.5f);
        font.getData().setScale(game.fontScale,game.fontScale);
        Ball.drawSelf(this.game.batch);

        //font.draw(game.batch,"Bounchie Ball 2!!",200,900);

        game.batch.end();
        //end rendering
    }

    public void updateBall(){
        Ball.updatePos();
        if(Ball.x+Ball.width>game.cameraWidth){
            Ball.x = game.cameraWidth-Ball.width;
            Ball.xvel*=-1;
            if(abs(Ball.xvel)>3) {
                game.skin_Manager.getSound(0).play();
            }
        }else if(Ball.x<0){
            if(abs(Ball.xvel)>3) {
                game.skin_Manager.getSound(0).play();
            }
            Ball.x = 0;
            Ball.xvel*=-1;
        }
        if(Ball.y<game.cameraHeight/2-85){
            Ball.y = game.cameraHeight/2-85;
            if(abs(Ball.yvel)>5) {
                game.skin_Manager.getSound(0).play();
            }
            Ball.yvel*=-0.5;
            Ball.xvel*=0.9;
            if(Ball.yvel > -0.5 && Ball.yvel < 0){
                Ball.yvel = 0;
            }
        }

        if(Ball.y > game.cameraHeight*3){
            Ball.y = 0;
            Ball.yvel = 0;
        }
        Ball.yvel -= 1.85f;
    }

    public void inputTouched(){
        game.touchPos.set(Gdx.input.getX(), Gdx.input.getY(),0);
        camera.unproject(game.touchPos);

        Ball.xvel=Ball.xvel/abs(Ball.xvel)*10f;
        Ball.yvel = 25;

        for(int i = 0;i<num_of_Buttons;i++){
            if(buttons[i].checkPressed(game.touchPos)){
                switch(i){
                    case 0:
                        game.setScreen(new GameSelectionScreen(this.game));
                        dispose();
                        break;
                    case 1:
                        game.setScreen(new ShopScreen(this.game));
                        dispose();
                        break;
                    case 2:
                        game.setScreen(new SkinsScreen(this.game));
                        dispose();
                        break;
                    case 3:
                        game.setScreen(new AboutScreen(this.game));
                        dispose();
                        break;
                }
            }
        }
    }
    public void hide(){

    }

    public void dispose(){
        for(Button button:buttons){
            button.dispose();
        }
    }

    public void resize(int width, int height){
    }
    public void show(){

    }
    public void resume(){

    }


}
