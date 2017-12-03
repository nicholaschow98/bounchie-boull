package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Nick on 2017-11-30.
 */

public class ShopScreen implements Screen {
    OrthographicCamera camera;
    B_Ball game;
    BitmapFont font;
    int fontScale = 2;

    final int num_of_Buttons = 1;
    Button backButton;
    Button buttons[] = new Button[num_of_Buttons];

    public ShopScreen(final B_Ball game){
        this.game = game;

        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(fontScale,fontScale);

        camera = new OrthographicCamera(game.cameraWidth, game.cameraHeight);
        camera.setToOrtho(false, game.cameraWidth,game.cameraHeight);
        camera.update();

        initializeButtons();
    }

    private void initializeButtons(){
        backButton= new Button(game.cameraWidth/8,game.screenHeight*7/8, game.T_backButton);
        buttons[0] = backButton;
    }

    public void pause(){

    }

    public void render(float delta){
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
        font.draw(game.batch, "THIS IS THE SHOP BITCHO", 100, 700);
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
                        game.setScreen(new MainScreen(this.game));
                        this.dispose();
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
