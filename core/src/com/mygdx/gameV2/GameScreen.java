package com.mygdx.gameV2;

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

    BitmapFont font;
    int num_buttons = 2;
    Button backButton;
    Button playButton;
    Button [] buttons = new Button[num_buttons];

    int highscore;

    String mode;
    public GameScreen(final B_Ball game, String mode){
        this.game = game;
        this.mode = mode;
        camera = new OrthographicCamera(game.cameraWidth, game.cameraHeight);
        camera.setToOrtho( false, game.cameraWidth,game.cameraHeight);
        backButton = buttons[0] = new styleButton(1.5f,"BACK",game.cameraWidth*1/4-40, game.cameraHeight*1/2,game.skin_Manager.button_file_names);
        playButton = buttons[1] = new styleButton(1.5f,"RETRY",game.cameraWidth*2/4+20, game.cameraHeight*1/2,game.skin_Manager.button_file_names);

        this.font = this.game.font;
        if(mode.equals("Classic")){
            gamemode = new Classic_GameMode(this.game);
            this.highscore = this.game.data.getInteger("Classic_Highscore", 0);
        }
        else if(mode.equals("Staggered")){
            gamemode = new Staggered_GameMode(this.game);
            this.highscore = this.game.data.getInteger("Staggered_Highscore", 0);
        }
        else if(mode.equals("GM2")){
            gamemode = new GM2(this.game);
            this.highscore = this.game.data.getInteger("GM2_Highscore", 0);
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
        font.setColor(Color.BLACK);
        for(Wall wall : gamemode.get_Walls()){
            wall.drawSelf(this.game.batch);
        }
        if(!gamemode.getStarted()){
            font.draw(game.batch, "TAP TO START", game.cameraWidth/2-200, game.cameraHeight*5/8);
        }

        gamemode.get_Ball().drawSelf(this.game.batch);
        if(gamemode.getLose()==true){
            if(gamemode.getScore()>this.highscore){
                this.highscore = gamemode.getScore();
            }
            font.getData().setScale(1.5f);
            font.draw(game.batch, "Highscore x "+this.highscore, game.cameraWidth/4+56, game.cameraHeight*5/8);//-font.getSpaceWidth()/2
            for(Button button:buttons){
                button.activate();
                button.drawSelf(this.game.batch);
            }
        }
        else{
             for(Button button:buttons){
                    button.deactivate();
                }
            }
        font.getData().setScale(7);
        font.draw(game.batch, ""+gamemode.getScore(), game.cameraWidth/2-70, game.cameraHeight*6/8);//-font.getSpaceWidth()/2
        game.batch.end();

        if(Gdx.input.isTouched()){
            game.touchPos.set(Gdx.input.getX(), Gdx.input.getY(),0);
            camera.unproject(game.touchPos);

            for(int i = 0; i < num_buttons;i++){
                if(buttons[i].checkPressed(game.touchPos)){
                    switch (i){
                        case 0:
                        {
                            game.setScreen(new MainScreen(game));
                            this.dispose();
                            break;
                        }
                        case 1:
                        {
                            gamemode.start();
                            break;
                        }
                    }
                }
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
