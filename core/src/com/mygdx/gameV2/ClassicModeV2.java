package com.mygdx.gameV2;

/*
    Crated by Minha 3-8-20
 */

public class ClassicModeV2 extends BaseGameMode {

    private int num_walls = 6;
    private float wall_xvel = 0;
    public String gamemodeName = "Classic";

    public ClassicModeV2(B_Ball game){
        this.game = game;
        this.ball_texture = game.T_ogBall;
        this.wall_texture = game.T_wallTexture;
        this.Walls = new Wall[num_walls];
        this.Ball = new Ball(game.cameraWidth/2+80, game.cameraHeight/8+80,game.ballSize,game.ballSize,0,0,game.skin_Manager.getBallSkin());
        this.init_GenerateWalls();
    }

    //PLACE HOLDER
    public void specialupdate(){
        if (this.game.data.getInteger(gamemodeName+"_Highscore",0) > 500){
            this.game.data.putInteger(gamemodeName+"_Highscore", 0);
        }
    }

}
