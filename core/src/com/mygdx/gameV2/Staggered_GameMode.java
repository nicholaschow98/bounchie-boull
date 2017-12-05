package com.mygdx.gameV2;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

import static java.lang.Math.abs;

/**
 * Created by Minha Lee on 12/3/2017.
 */

public class Staggered_GameMode extends Classic_GameMode {

    public Staggered_GameMode(B_Ball game, Preferences data){
        super(game, data);
        this.gamemodeName = "Staggered";
    }

    protected void init_GenerateWalls(){
        Walls[0] = new Wall(rand.nextInt(game.cameraWidth*1/3) + wallWidth/2,1100, wallWidth,500, 0, wall_yvel,wall_texture);
        Walls[1] = new Wall (game.cameraWidth*7/8,400,wallWidth,800,0,wall_yvel,wall_texture);
        for(int i = 2; i<num_walls;i++){
            if(i%2==0) {
                Walls[i] = new Wall(rand.nextInt(game.cameraWidth*1/3) + wallWidth/2, generate_left_Wall_Y(i), wallWidth, generate_Wall_Height(), 0, wall_yvel, wall_texture);
            }
            //else if(i&3 == 0) {
            //        Walls[i] = new Wall(rand.nextInt(game.cameraWidth*2/3 - game.cameraWidth*1/3) + wallWidth/2,
            else{
                Walls[i] = new Wall(rand.nextInt(game.cameraWidth*1/3)+game.cameraWidth*2/3-wallWidth/2, generate_right_Wall_Y(i), wallWidth, generate_Wall_Height(), 0, wall_yvel, wall_texture);
            }
        }
    }
    //protected float generate_left_Wall_X(int wall){
      //  int

    protected float generate_Wall_Height(){
        return rand.nextInt(500)+200;
    }

    public void update(){
        if(started){
            //WALL LOGIC
            if(!lose) {
                for (int i = 0; i<num_walls;i++) {
                    Walls[i].updatePos();
                    if(Walls[i].y + Walls[i].height < -10){
                        if(i%2==0){
                            Walls[i].y = generate_left_Wall_Y(i);
                            Walls[i].x = rand.nextInt(game.cameraWidth*1/3) + wallWidth/2;
                        }else{
                            Walls[i].y = generate_right_Wall_Y(i);
                            Walls[i].x = rand.nextInt(game.cameraWidth*1/3)+game.cameraWidth*2/3 -wallWidth/2;
                        }
                        Walls[i].height = generate_Wall_Height();
                    }
                }
            }
            //WALL LOGIC END

            //BALL LOGIC
            this.Ball.updatePos();
            boolean [] collided = new boolean[num_walls];
            char [] dir = new char[num_walls];//up down left right -> u d l r
            Ball.checkForDirectionalCollision(Walls,collided, dir);

            for(int i = 0;i<num_walls;i++){
                if(collided[i]){
                    if(dir[i]=='u'){//ball collided with top of wall
                        Ball.yvel=(float)0.5*abs(Ball.yvel);
                        Ball.y = Walls[i].y+Walls[i].height;
                    }else if(dir[i]=='d'){//ball collided with bottom of wall
                        Ball.yvel=-1*abs(Ball.yvel);
                        Ball.y = Walls[i].y-Ball.height;
                    }else if(dir[i]=='l'){
                        if(!lose){
                            score++;
                            incDiff();
                        }
                        Ball.xvel=-1*abs(Ball.xvel);
                        Ball.x = Walls[i].x-Ball.width;
                    }else if(dir[i]=='r'){
                        if(!lose){
                            score++;
                            incDiff();
                        }
                        Ball.xvel=abs(Ball.xvel);
                        Ball.x = Walls[i].x+Walls[i].width;
                    }
                }
            }
            if(Ball.x+Ball.width>=game.cameraWidth){
                Ball.x = game.cameraWidth-Ball.width;
                Ball.xvel*=-0.5;
                lose();
            }else if(Ball.x<0){
                Ball.x = 0;
                Ball.xvel*=-0.5;
                lose();
            }
            if(Ball.y<0){
                lose();
                Ball.y = 0;
                Ball.yvel*=-0.5;
                Ball.xvel*=0.9;
                if(Ball.yvel>-0.5&&Ball.yvel<0){
                    Ball.yvel = 0;
                }
            }
            Ball.yvel-=gravity;
            //BALL LOGIC END
        }
    }
    public void lose(){
        lose = true;
    }
    private void incDiff(){
        if(Ball.xvel>0){
            Ball.xvel+=Ball_init_vel/300;
        }else{
            Ball.xvel-=Ball_init_vel/100;
        }
        for(Wall wall:Walls){
            wall.yvel+=wall_yvel/60;
        }
    }
    public boolean getLose(){
        return this.lose;
    }
    public void start(){
        this.started = true;
        this.score = 0;
        this.Ball.x = game.cameraWidth/2+80;
        this.Ball.y = game.cameraHeight/8+80;
        if(lose){
            this.init_GenerateWalls();
        }
        this.lose = false;
        Ball.xvel = Ball_init_vel;

    }
}

