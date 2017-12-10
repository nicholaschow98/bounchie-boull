package com.mygdx.gameV2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.Random;

/**
 * Created by Nick on 2017-11-30.
 */

public class ShopScreen implements Screen {
    OrthographicCamera camera;
    B_Ball game;
    BitmapFont font;
    int fontScale = 2;

    Random rand;

    final int num_of_Buttons = 2;
    Button backButton;
    Button buyButton;
    Button buttons[] = new Button[num_of_Buttons];
    int bought;
    final int price = 100;

    public ShopScreen(final B_Ball game){
        this.game = game;

        font = this.game.font;
        font.setColor(Color.BLACK);
        font.getData().setScale(fontScale,fontScale);

        this.bought =-1;

        camera = new OrthographicCamera(game.cameraWidth, game.cameraHeight);
        camera.setToOrtho(false, game.cameraWidth,game.cameraHeight);
        camera.update();

        rand = new Random();

        initializeButtons();
    }

    private void initializeButtons(){
        backButton= buttons[0]= new styleButton(1.5f,"BACK",game.cameraWidth*1/10, game.cameraHeight*7/8,game.skin_Manager.button_file_names);
        buyButton= buttons[1] = new styleButton(2.5f,"BUY SKIN",100,game.cameraHeight/2, game.skin_Manager.button_file_names);
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
            button.drawSelf(this.game.batch);
        }
        font.draw(game.batch,"Cash: "+game.cash,500,900);
        font.draw(game.batch, game.touchPos.x+", "+game.touchPos.y, 50, 100);

        if(this.bought!=-1){
            font.draw(game.batch, "Ye got skin number "+this.bought +".", 100, 350);
            game.batch.draw(new Texture(game.skin_Manager.filenames[this.bought][0]),300,200);
        }
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
                    case 1:
                        if(this.game.cash>=price&&Gdx.input.justTouched()){
                            rand = new Random();
                            this.game.cash-=price;
                            this.game.data.putInteger("cash",this.game.cash);
                            this.game.data.flush();
                            this.bought = rand.nextInt(game.skin_Manager.num_skins);
                            this.game.skin_Manager.unlockSkin(this.bought);
                        }
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
