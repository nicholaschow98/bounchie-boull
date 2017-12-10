package com.mygdx.gameV2;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

import static java.lang.Math.abs;

/**
 * Created by Minha Lee on 12/3/2017.
 */

public class Staggered_GameMode implements GameMode {

    B_Ball game;

    protected Wall [] Walls;
    protected Wall [] SpecialWalls;
    protected Wall [] GuardWalls;
    protected Wall [] AllWalls;

    protected Ball Ball;

    Random rand = new Random();

    protected boolean started = false;
    protected boolean lose = false;
    public int score = 0;

    private int num_special = 6;
    private int num_guard = 6;
    private int num_walls = 6;
    private int num_all = num_special + num_walls + num_guard;

    //private float leftx, rightx;

    protected int wallWidth = 30;
    protected float wall_yvel = -10;

    protected float jumpspeed = 25;
    protected float gravity  = 1.7f;
    protected float Ball_init_vel = 6.8f;

    protected Texture wall_texture;
    protected Texture special_texture;
    protected Texture guard_texture;
    //FOR TESTING ONLY
    protected Texture ball_texture;
    protected Texture one;
    protected Texture two;
    protected Texture three;
    protected Texture four;
    protected Texture five;
    protected Texture six;
    //FOR TESTING ONLY

    Preferences data;

    public Staggered_GameMode(B_Ball game){
        this.game = game;
        this.ball_texture = game.T_ogBall;
        this.wall_texture = game.T_wallTexture;
        //THIS IS FOR TESTING ONLY
        this.special_texture = game.T_shopIcon;
        this.guard_texture = game.T_backButton;
        this.one = game.T_1;
        this.two = game.T_2;
        this.three = game.T_3;
        this.four = game.T_4;
        this.five = game.T_5;
        this.six = game.T_6;
        //FOR TESTING ONLY END
        this.Walls = new Wall[num_walls];
        this.SpecialWalls = new Wall[num_special];
        this.GuardWalls = new Wall[num_guard];
        this.AllWalls = new Wall [num_all];
        this.Ball = new Ball(game.cameraWidth/2+80, game.cameraHeight/8+80,80,80,0,0,game.skin_Manager.getBallSkin());
        this.init_GenerateWalls();
    }

    public Wall[] get_Walls(){
        return this.AllWalls;
    }

    public int getScore(){
        return score;
    }

    public Ball get_Ball(){
        return this.Ball;
    }
    public boolean getStarted(){
        return this.started;
    }
    protected void init_GenerateWalls() {
        Walls[0] = new Wall(game.cameraWidth * 1 / 8, 1100, wallWidth, 500, 0, wall_yvel, one);
        AllWalls[0] = Walls[0];
        Walls[1] = new Wall(game.cameraWidth * 7 / 8, 400, wallWidth, 650, 0, wall_yvel, two);
        AllWalls[1] = Walls[1];
        for (int i = 2; i < num_walls; i++) {
            if (i % 2 == 0) {
                Walls[i] = new Wall(generate_staggered_X(i), generate_left_Wall_Y(i), wallWidth, generate_Wall_Height(0), 0, wall_yvel, three);
                AllWalls[i] = Walls[i];
            } else {
                Walls[i] = new Wall(generate_staggered_X(i), generate_right_Wall_Y(i), wallWidth, generate_Wall_Height(0), 0, wall_yvel, four);
                AllWalls[i] = Walls[i];
            }
        /*
        Walls[4] = new Wall(generate_staggered_X(4), generate_right_Wall_Y(4), wallWidth, generate_Wall_Height(0), 0, wall_yvel, five);
        AllWalls[4] = Walls[4];
        Walls[5] = new Wall(generate_staggered_X(5), generate_right_Wall_Y(5), wallWidth, generate_Wall_Height(0), 0, wall_yvel, six);
        AllWalls[5] = Walls[5];
        */
            //extra walls if spacing gets a little too wack
            //}
        }
            int place = 6;
            for (int i = 0; i < num_special; i++) {
                SpecialWalls[i] = new Wall(0, 0, wallWidth, 0, 0, wall_yvel, special_texture);
                AllWalls[place] = SpecialWalls[i];
                place++;
            }
            for (int i = 0; i < num_guard; i++) {
                GuardWalls[i] = new Wall(0, 0, wallWidth, 0, 0, wall_yvel, guard_texture);
                AllWalls[place] = GuardWalls[i];
                place++;
            }
        }

    private float generate_staggered_X(int wall){
        if(wall%2 ==0){
            return rand.nextInt(game.cameraWidth*1/3) + wallWidth/2;
        }
        else{
            return rand.nextInt(game.cameraWidth*1/3)+game.cameraWidth*2/3-wallWidth/2;
        }
    }

    protected float generate_Wall_Height(int type){
        if(type == 0) {
            return rand.nextInt(500) + 450;
        }
        else if(type == 1){
            return rand.nextInt(400)+ 300;
        }
        return 0;
    }

    protected float generate_left_Wall_Y(int wall){
        int last_wall = wall-2;
        if(last_wall == -2){
            last_wall = num_walls-2;
        }
        return rand.nextInt(600) + Walls[last_wall].y+Walls[last_wall].height+Ball.height+10;
    }
    protected float generate_right_Wall_Y(int wall) {
        int last_wall = wall - 2;
        if (last_wall == -1) {
            last_wall = num_walls - 1;
        }
        return rand.nextInt(600) + Walls[last_wall].y + Walls[last_wall].height + Ball.height + 10;
    }

    public void touch_Update(){
        if(!started){
            this.start();
        }
        if(!lose){
            Ball.yvel=jumpspeed;
        }
        if(lose){
            start();
            lose=false;
        }
    }

    public int next(int index, int front){
        int no = index - front;
        if (no >= 0){
            return no;
        }
        else{
            int yea = num_walls + index - front;
            int yes = index - (num_walls - front) ;
            return yea;
        }
    }

    public void update() {
        if (started) {
            //WALL LOGIC
            if (!lose) {
                for (int i = 0; i < num_walls; i++) {
                    Walls[i].updatePos();
                    if (Walls[i].y + Walls[i].height < -10) {
                        if (i % 2 == 0) {
                            Walls[i].y = generate_left_Wall_Y(i);
                            Walls[i].x = rand.nextInt(game.cameraWidth * 1 / 3) + wallWidth / 2;
                        } else {
                            Walls[i].y = generate_right_Wall_Y(i);
                            Walls[i].x = rand.nextInt(game.cameraWidth * 1 / 3) + game.cameraWidth * 2 / 3 - wallWidth / 2;
                        }
                        Walls[i].height = generate_Wall_Height(0);
                    }
                    GuardWalls[i].updatePos();
                    if (GuardWalls[i].y + GuardWalls[i].height < -10) {
                        float difference = 0;
                        float dist = 0;
                        difference = Walls[i].x - Walls[next(i, 2)].x ;
                        dist = Walls[i].y - Walls[next(i, 2)].y - Walls[next(i, 2)].height;
                        if(i%2 == 0){
                            if (Walls[i].x > Walls[next(i,2)].x && Math.abs(difference) >= game.cameraWidth*1/6 && Walls[i].y > 800 && dist > 100){
                                if (Walls[i].x > game.cameraWidth * 1 / 4 && Walls[i].x <= game.cameraWidth * 1/3){
                                    GuardWalls[i].x = wallWidth/2 + 5;
                                    GuardWalls[i].height = Walls[i].height -50 + rand.nextInt(100);
                                    GuardWalls[i].y = Walls[i].y - rand.nextInt(150);
                                }
                            }
                        }
                        else if(i%2 == 1){
                            if (Walls[i].x < Walls[next(i,2)].x && Math.abs(difference) >= game.cameraWidth * 1/6 && Walls[i].y > 800 && dist > 100){
                                if(Walls[i].x < game.cameraWidth * 3/4 && Walls[i].x > game.cameraWidth*2/3){
                                    GuardWalls[i].x = game.cameraWidth - wallWidth / 2 - 5;
                                    GuardWalls[i].height = Walls[i].height + 100;
                                    GuardWalls[i].y = Walls[i].y - rand.nextInt(100);
                                }
                            }
                        }
                    }
                }
                for (int i = 1; i < num_walls; i += 2) {
                    SpecialWalls[i].updatePos();
                    if (SpecialWalls[i].y + SpecialWalls[i].height < -10) {
                        float distance = Walls[i].x - Walls[next(i, 1)].x;
                        float dist = Walls[next(i, 1)].y - Walls[i].x - Walls[i].height;
                        if (Math.abs(distance) > game.cameraWidth * 5 / 12 && Math.abs(dist) > 25 && Math.abs(dist) < 350 && Walls[i].y > game.cameraHeight) {
                            int spacer = rand.nextInt(game.cameraWidth * 1 / 8);
                            SpecialWalls[i].x = Walls[next(i,1)].x + spacer + rand.nextInt(Math.round(distance - spacer));
                            if(Walls[next(i,1)].y < Walls[i].y + Walls[i].height && Walls[next(i, 1)].y > game.cameraHeight) {
                                SpecialWalls[i].y = Walls[next(i,1)].y + Walls[next(i,1)].height * 1 / 2 + rand.nextInt(200);
                                SpecialWalls[i].height = rand.nextInt(200) + 250;
                            }
                            else{
                                SpecialWalls[i].y = Walls[i].y + Walls[i].height/2 + rand.nextInt(200);
                                SpecialWalls[i].height = rand.nextInt(300) + 250;
                            }
                        }
                        //else if()
                    }
                }



                //WALL LOGIC END

                //BALL LOGIC
                this.Ball.updatePos();
                boolean[] collided = new boolean[num_all];
                char[] dir = new char[num_all];//up down left right -> u d l r
                Ball.checkForDirectionalCollision(AllWalls, collided, dir);

                for (int i = 0; i < num_all; i++) {
                    if (collided[i]) {
                        if (dir[i] == 'u') {//ball collided with top of wall
                            Ball.yvel = (float) 0.5 * abs(Ball.yvel);
                            Ball.y = AllWalls[i].y + AllWalls[i].height;
                        } else if (dir[i] == 'd') {//ball collided with bottom of wall
                            Ball.yvel = -1 * abs(Ball.yvel);
                            Ball.y = AllWalls[i].y - Ball.height;
                        } else if (dir[i] == 'l') {
                            if (!lose) {
                                score++;
                            }
                            Ball.xvel = -1 * abs(Ball.xvel);
                            Ball.x = AllWalls[i].x - Ball.width;
                        } else if (dir[i] == 'r') {
                            if (!lose) {
                                score++;
                                //incDiff();
                            }
                            Ball.xvel = abs(Ball.xvel);
                            Ball.x = AllWalls[i].x + AllWalls[i].width;
                        }
                    }
                }
                if (Ball.x + Ball.width >= game.cameraWidth) {
                    Ball.x = game.cameraWidth - Ball.width;
                    Ball.xvel *= -0.5;
                    lose();
                } else if (Ball.x < 0) {
                    Ball.x = 0;
                    Ball.xvel *= -0.5;
                    lose();
                }
                if (Ball.y < 0) {
                    lose();
                    Ball.y = 0;
                    Ball.yvel *= -0.5;
                    Ball.xvel *= 0.9;
                    if (Ball.yvel > -0.5 && Ball.yvel < 0) {
                        Ball.yvel = 0;
                    }
                }
                Ball.yvel -= gravity;
                //BALL LOGIC END
            }
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

