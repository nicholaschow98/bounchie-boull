package com.mygdx.gameV2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


/**
 * Created by Nick on 2017-11-29.
 */

public class MainScreen implements Screen {

    OrthographicCamera camera;
    B_Ball game;

    BitmapFont font;

    final int num_of_Buttons = 3;
    Button gameButton, shopButton, skinButton;
    Button buttons[] = new Button[num_of_Buttons];
    public MainScreen(final B_Ball game){
        this.game = game;
        this.font  = this.game.font;
        camera = new OrthographicCamera(game.cameraWidth, game.cameraHeight);
        camera.setToOrtho(false, game.cameraWidth,game.cameraHeight);
        camera.update();
        initializeButtons();
    }

    private void initializeButtons(){
        gameButton = new Button(100,100, game.T_ogBall);
        shopButton =  new Button(100,300,  game.T_shopIcon);
        skinButton =  new Button(100,500, game.T_skinIcon);
        buttons[0] = gameButton;
        buttons[1] = shopButton;
        buttons[2] = skinButton;
    }

    public void pause(){

    }

    public  void render(float delta){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        if(Gdx.input.isTouched()){
            inputTouched();
        }
        //Start all rendering
        game.batch.begin();
        for(Button button: buttons){
            button.drawSelf(this.game);
        }
        font.draw(game.batch, game.touchPos.x+", "+game.touchPos.y, 50, 100);
        font.getData().setScale(1.5f,1.5f);
        font.draw(game.batch, "cam Height:"+game.cameraHeight+"   screen Height:"+game.screenHeight, 100, 700);
        font.getData().setScale(game.fontScale,game.fontScale);
        game.batch.end();
        //end rendering
    }
    public void inputTouched(){
        game.touchPos.set(Gdx.input.getX(), Gdx.input.getY(),0);
        camera.unproject(game.touchPos);

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
