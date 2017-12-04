package com.mygdx.gameV2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Minha Lee on 12/3/2017.
 */

public class GameSelectionScreen implements Screen{

    OrthographicCamera camera;
    B_Ball game;
    GameMode gamemode;

    BitmapFont font;

    Button backButton, ClassicMode, StaggeredMode, TrollMode;

    int Modes = 2;
    Button buttons[] = new Button[Modes];
    String classic = "Classic";
    String staggered = "Staggered";

    public GameSelectionScreen(B_Ball game){
        this.game = game;
        camera = new OrthographicCamera(game.cameraWidth, game.cameraHeight);
        camera.setToOrtho( false, game.cameraWidth,game.cameraHeight);

        this.font = this.game.font;
        initializeButtons();
    }

    public void pause(){

    }
    private void initializeButtons(){
        ClassicMode = new Button(game.cameraWidth*1/3, game.cameraHeight*3/8, game.T_ogBall);
        StaggeredMode =  new Button(game.cameraWidth*2/3, game.cameraHeight*3/8, game.T_ogBall);
        //TrollMode = new Button (WHATEVER)
        backButton = new Button(game.cameraWidth*2/8,game.cameraHeight*5/8, game.T_backButton);
        buttons[0] = ClassicMode;
        buttons[1] = StaggeredMode;
        //buttons[2] = TrollMode;
    }
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        if(Gdx.input.isTouched()){
            inputTouched();
        }
        /*gamemode.update();
        game.batch.begin();
        for(Wall wall : gamemode.get_Walls()){
            wall.drawSelf(this.game);
        }*/

        //font.getData().setScale(7);
        //font.setColor(Color.BLACK);

        /*gamemode.get_Ball().drawSelf(this.game);
        if(gamemode.getLose()==true){
            game.batch.draw(game.img,game.cameraWidth/8,game.cameraHeight*2/3,game.cameraWidth*6/8,game.cameraHeight/4);
            backButton.activate();
            backButton.drawSelf(this.game);
        }
        else{
            backButton.deactivate();
        }*/
        /*
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
    }*/
        game.batch.begin();
        for(int i = 0; i < Modes; i++){
            buttons[i].drawSelf(this.game.batch);
        }
        game.batch.end();
    }

    public void inputTouched() {
        game.touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(game.touchPos);

        for (int i = 0; i < Modes; i++) {
            if (buttons[i].checkPressed(game.touchPos)) {
                switch (i) {
                    case 0:
                        game.setScreen(new GameScreen(this.game, classic));
                        dispose();
                        break;
                    case 1:
                        game.setScreen(new GameScreen(this.game, staggered));
                        dispose();
                        break;
                    case 2:
                        //game.setScreen(new GameScreen(this.game));
                        //dispose();
                        break;
                }
            }
        }
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
