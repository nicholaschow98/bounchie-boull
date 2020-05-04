package com.mygdx.gameV2;

import com.badlogic.gdx.graphics.Texture;

public class WavyModeV2 extends BaseGameMode {

    protected Wall [] Rings;
    protected Wall [] AllWalls; //what is the purpose of this

    protected int num_walls = 8;
    protected float wall_xvel = 1;
    protected Texture coin_texture;

    public String gamemodeName = "Wavy";

    public WavyModeV2(B_Ball game) {
        this.game = game;
        this.ball_texture = game.T_ogBall;
        this.wall_texture = game.T_wallTexture;
        this.coin_texture = game.T_coin;
        this.Walls = new Wall[num_walls];
        this.Rings = new Wall[num_walls];
        this.AllWalls = new Wall[num_walls * 2];
        this.Ball = new Ball(game.cameraWidth / 2 + 80, game.cameraHeight / 8 + 80, game.ballSize, game.ballSize, 0, 0, game.skin_Manager.getBallSkin());
        this.init_GenerateWalls();
    }

    //THIS IS JUST COPY AND PASTED FIX IT UP A LIL BEFORE TRIAL 1

    private float generate_ring_y(int wall){
        int before = wall - 1;
        if (before == -1){
            before = num_walls-1;
        }
        return Rings[before].y + game.cameraHeight + rand.nextInt(600);
    }

    public void specialupdate(){
        if (started){
            if (!lose){
                //COIN INIT
                for (int i = 0; i<num_walls;i++) {
                    Rings[i].updatePos();
                    int yeah = rand.nextInt(200);
                    if (yeah == 2 && Rings[i].y + Rings[i].height < -10) {
                        Rings[i].y = generate_ring_y(i);
                        Rings[i].x = game.cameraWidth / 3 + rand.nextInt(game.cameraWidth / 3);
                    }
                }
            }


            boolean [] collided2 = new boolean[num_walls];
            char [] dir2 = new char[num_walls];//up down left right -> u d l r
            Ball.checkForDirectionalCollision(Rings,collided2, dir2);

            for (int i = 0;i<num_walls;i++){
                //EXTRA BALL LOGIC FOR COINS
                if(collided2[i]){
                    //if(dir[i] == 'r' || dir[i] == 'l'){
                    if(!lose) {
                        score += 4;
                        Rings[i].x = -Rings[i].width;
                    }
                }
                //Wall Xvel movement Logic (Move to own loop?)
                if(Walls[i].x > game.cameraWidth*1/2){
                    if (Walls[i].x+Walls[i].width>game.cameraWidth || Walls[i].x < game.cameraWidth*2/3){
                        Walls[i].xvel *= -1;
                    }
                }
                else if(Walls[i].x < game.cameraWidth*1/2){
                    if(Walls[i].x < 0 || Walls[i].x > game.cameraWidth*1/3){
                        Walls[i].xvel*= -1;
                    }
                }
            }

        }
    }
}
