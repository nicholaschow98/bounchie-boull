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
    //GameMode gamemode;
    BaseGameMode gamemodev2;

    BitmapFont font;

    Button backButton, ClassicMode, StaggeredMode, ripButton, WavyMode, TrollMode;

    int num_buttons = 4;
    Button buttons[] = new Button[num_buttons];
    String classic = "Classic";
    String staggered = "Staggered";
    String wavy = "Wavy";

    int Classic_Highscore;
    int Staggered_Highscore;
    int Wavy_Highscore;

    public GameSelectionScreen(B_Ball game){
        this.game = game;
        this.Classic_Highscore = this.game.data.getInteger("Classic_Highscore",0);
        this.Staggered_Highscore = this.game.data.getInteger("Staggered_Highscore",0);
        this.Wavy_Highscore = this.game.data.getInteger("Wavy_Highscore",0);
        //reassign key name
        camera = new OrthographicCamera(game.cameraWidth, game.cameraHeight);
        camera.setToOrtho( false, game.cameraWidth,game.cameraHeight);

        this.font = this.game.font;
        initializeButtons();
    }

    public void pause(){

    }
    private void initializeButtons(){
        ClassicMode = new styleButton(2.5f,"CLASSIC",game.cameraWidth*1/10, game.cameraHeight*8/16,game.skin_Manager.button_file_names);

        StaggeredMode = new styleButton(2.5f,"FREESTYLE",game.cameraWidth*1/10, game.cameraHeight*5/16,game.skin_Manager.button_file_names);

        WavyMode = new styleButton(2.5f,"WAVY",game.cameraWidth*1/10, game.cameraHeight*2/16,game.skin_Manager.button_file_names);
        //TrollMode = new Button (WHATEVER)
        backButton = buttons[0] = new styleButton(1.5f,"BACK",game.cameraWidth*1/10, game.cameraHeight*7/8,game.skin_Manager.button_file_names);
        ripButton  = new styleButton(0.5f,"rip",game.cameraWidth-100, game.cameraHeight*9/10,game.skin_Manager.button_file_names);
        buttons[1] = ClassicMode;
        buttons[2] = StaggeredMode;
        buttons[3] = WavyMode;
    }
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        if (Gdx.input.isTouched()) {
            inputTouched();
        }

        game.batch.begin();
        for (int i = 0; i < num_buttons; i++) {
            buttons[i].drawSelf(this.game.batch);
        }

        //debugging only
        if (Classic_Highscore + Staggered_Highscore > 5) {
            ripButton.drawSelf(game.batch);
        }

        game.font.draw(game.batch, "CHOOSE YOUR GAMEMODE", game.cameraWidth / 10, game.cameraHeight / 9 * 10);
        game.font.draw(game.batch, "Highscore: " + Classic_Highscore, ClassicMode.x + 50, ClassicMode.y - 15);
        game.font.draw(game.batch, "Highscore: " + Staggered_Highscore, StaggeredMode.x + 50, StaggeredMode.y - 15);
        game.font.draw(game.batch, "Highscore: " + Wavy_Highscore, WavyMode.x + 50, WavyMode.y - 15);
        game.batch.end();
    }


    public void inputTouched() {
        game.touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(game.touchPos);
        for (int i = 0; i < num_buttons; i++) {
            if (buttons[i].checkPressed(game.touchPos)&&Gdx.input.justTouched()) {
                switch (i) {
                    case 0:
                        game.setScreen(new MainScreen(this.game));
                        dispose();
                        break;
                    case 1:
                        game.setScreen(new GameScreen(this.game, classic));
                        dispose();
                        break;
                    case 2:
                        //game.setScreen(new GameScreen(this.game, staggered));
                        //TAKEN OUT FOR NOW
                        dispose();
                        break;
                    case 3:
                        game.setScreen(new GameScreen(this.game, wavy));
                        dispose();
                        break;
                }
            }
        }
        if(ripButton.checkPressed(game.touchPos)&&Gdx.input.isTouched(1)){
            game.skin_Manager.deleteSkinProgress();

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
