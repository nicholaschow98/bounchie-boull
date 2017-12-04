package com.mygdx.gameV2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Nick on 2017-11-30.
 */

public class SkinsScreen implements Screen {
    OrthographicCamera camera;
    B_Ball game;
    BitmapFont font;
    int fontScale = 2;

    final int num_of_Buttons = 1;
    Button backButton;
    Button buttons[];
    Button skinButtons[];

    public SkinsScreen(final B_Ball game){
        this.game = game;
        this.font = game.font;
        font.setColor(Color.BLACK);
        font.getData().setScale(fontScale,fontScale);

        buttons = new Button[num_of_Buttons];
        skinButtons = new Button[game.skin_Manager.num_skins];

        camera = new OrthographicCamera(game.cameraWidth, game.cameraHeight);
        camera.setToOrtho(false, game.cameraWidth,game.cameraHeight);
        camera.update();

        initializeButtons();
        initializeSkinButtons();
    }

    private void initializeButtons(){
        backButton= new Button(game.cameraWidth/8,game.cameraHeight*7/8, game.T_backButton);
        buttons[0] = backButton;
    }
    private void initializeSkinButtons(){
        int j = 0;
        for(int i = 0; i < game.skin_Manager.num_skins;i++){
            int k = i;
            if((i)%4==0){
                j++;
                k=0;
            }
            skinButtons[i] = new Button(game.cameraWidth/8+k*175, game.cameraHeight*(6-j)/8,100,100, game.skin_Manager.ballSkin_T[i]);
        }
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
            button.drawSelf(this.game.batch);
        }
        for(Button skinbutton: skinButtons){
            skinbutton.drawSelf(this.game.batch);
        }

        font.draw(game.batch, game.touchPos.x+", "+game.touchPos.y, 50, 100);
        font.draw(game.batch, "THIS IS THE SKIN MENU BITCHO.\n SKINS AND SHIT BOI", 100, 700);
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
                        //this.dispose();
                        break;
                }
            }
        }
        for(int i = 0;i<game.skin_Manager.num_skins;i++){
            if(skinButtons[i].checkPressed(game.touchPos)){
                game.skin_Manager.changeBallSkin(i);
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
