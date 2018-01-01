package com.mygdx.gameV2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

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
    float camera_Y_Displacement;

    public SkinsScreen(final B_Ball game){
        this.game = game;
        this.font = game.font;
        font.setColor(Color.BLACK);
        font.getData().setScale(fontScale,fontScale);

        buttons = new Button[num_of_Buttons];
        skinButtons = new Button[game.skin_Manager.num_skins];

        camera = new OrthographicCamera(game.cameraWidth, game.cameraHeight);
        camera.setToOrtho(false, game.cameraWidth,game.cameraHeight);
        camera_Y_Displacement= 0;
        camera.update();

        initializeButtons();
        initializeSkinButtons();
    }

    private void initializeButtons(){
        backButton= new styleButton(1.5f,"BACK",game.cameraWidth*1/10, game.cameraHeight*7/8,game.skin_Manager.button_file_names);
        buttons[0] = backButton;
    }

    private void initializeSkinButtons(){
        int j = 0;
        int k = 0;
        for(int i = 0; i < game.skin_Manager.num_skins;i++){
            if((i)%4==0){
                j++;
                k=0;
            }
            skinButtons[i] = new Button(game.cameraWidth/8+k*175, game.cameraHeight*(7-j)/8,100,100, game.skin_Manager.ballSkins[i].getTexture());
            k++;
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
        for(int i = 0;i<game.skin_Manager.num_skins;i++){
            if(game.skin_Manager.unlocked[i]){
                if(game.skin_Manager.current_skin==i){
                    game.batch.draw(new Texture("dbox.png"),skinButtons[i].x-30,skinButtons[i].y-30,skinButtons[i].width+60,skinButtons[i].height+60);
                }
                skinButtons[i].drawSelf(this.game.batch);
            }else{
                game.batch.draw(new Texture("about_icon.png"),skinButtons[i].x,skinButtons[i].y,skinButtons[i].width,skinButtons[i].height);
            }

        }
        font.draw(game.batch, "Select your skin", 330, game.cameraHeight*10/11);
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
            if(game.skin_Manager.unlocked[i]&&Gdx.input.justTouched()){
                if (skinButtons[i].checkPressed(game.touchPos)) {
                    game.skin_Manager.changeBallSkin(i);
                }
            }
        }
        if(!Gdx.input.justTouched()){//Touch dragged
            camera.translate(0,2*Gdx.input.getDeltaY());
            if(camera.position.y>=game.cameraHeight/2){
                camera.position.y = game.cameraHeight / 2;
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
