package com.mygdx.gameV2;

import com.badlogic.gdx.Gdx;
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
    protected float wall_yvel = -8;

    protected float jumpspeed = 23;
    protected float gravity  = 1.7f;
    protected float Ball_init_vel = 5.9f;

    protected Texture wall_texture, wall_texture2, wall_texture3, ball_texture;

    public String gamemodeName = "Staggered";

    public Staggered_GameMode(B_Ball game){
        this.game = game;
        this.ball_texture = game.T_ogBall;
        this.wall_texture = game.T_wallTexture;
        this.wall_texture2 = game.T_wallTexture2;
        this.wall_texture3 = game.T_wallTexture3;
        this.Walls = new Wall[num_walls];
        this.SpecialWalls = new Wall[num_special];
        this.GuardWalls = new Wall[num_guard];
        this.AllWalls = new Wall [num_all];
        this.Ball = new Ball(game.cameraWidth/2+80, game.cameraHeight/8+80,game.ballSize,game.ballSize,0,0,game.skin_Manager.getBallSkin());
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

    protected void init_GenerateWalls() {
        Walls[0] = new Wall(game.cameraWidth * 1 / 8, 1100, wallWidth, 500, 0, wall_yvel, wall_texture);
        AllWalls[0] = Walls[0];
        Walls[1] = new Wall(game.cameraWidth * 7 / 8, 400, wallWidth, 650, 0, wall_yvel, wall_texture);
        AllWalls[1] = Walls[1];
        for (int i = 2; i < num_walls; i++) {
            if (i % 2 == 0) {
                Walls[i] = new Wall(generate_staggered_X(i), generate_left_Wall_Y(i), wallWidth, generate_Wall_Height(0), 0, wall_yvel, wall_texture);
                AllWalls[i] = Walls[i];
            } else {
                Walls[i] = new Wall(generate_staggered_X(i), generate_right_Wall_Y(i), wallWidth, generate_Wall_Height(0), 0, wall_yvel, wall_texture);
                AllWalls[i] = Walls[i];
            }

        }
            int place = 6;
            for (int i = 0; i < num_special; i++) {
                SpecialWalls[i] = new Wall(0, 0, wallWidth, 0, 0, wall_yvel, wall_texture);
                AllWalls[place] = SpecialWalls[i];
                place++;
            }
            for (int i = 0; i < num_guard; i++) {
                GuardWalls[i] = new Wall(0, 0, wallWidth, 0, 0, wall_yvel, wall_texture);
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
        return rand.nextInt(500) + Walls[last_wall].y+Walls[last_wall].height+Ball.height+10;
    }
    protected float generate_right_Wall_Y(int wall) {
        int last_wall = wall - 2;
        if (last_wall == -1) {
            last_wall = num_walls - 1;
        }
        return rand.nextInt(500) + Walls[last_wall].y + Walls[last_wall].height + Ball.height + 10;
    }

    public void touch_Update(){
        if(!started&& Gdx.input.justTouched()){
            this.start();
        }
        if(!lose){
            Ball.yvel=jumpspeed;
        }
    }

    public int next(int index, int front){
        if (front > 0) {
            int no = index - front;
            if (no >= 0) {
                return no;
            } else {
                int yea = num_walls + index - front;
                return yea;
            }
        }
        else{
            int no = index - front;
            if (no <= num_walls) {
                return no;
            } else {
                int yes = index - (num_walls + front);
                return yes;
            }
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
                        GuardWalls[i].texture = wall_texture;
                        float difference = 0;
                        float dist = 0;
                        difference = Walls[i].x - Walls[next(i, 2)].x;
                        dist = Walls[i].y - Walls[next(i, 2)].y - Walls[next(i, 2)].height;
                        if (i % 2 == 0) {
                            if (Walls[i].x > Walls[next(i, 2)].x && Math.abs(difference) >= game.cameraWidth * 1 / 6 && Walls[i].y > 800 && dist > 100) {
                                if (Walls[i].x > game.cameraWidth * 1 / 4 && Walls[i].x <= game.cameraWidth * 1 / 3) {
                                    GuardWalls[i].x = wallWidth / 2 + 5;
                                    GuardWalls[i].height = Walls[i].height + rand.nextInt(100) + 200;
                                    GuardWalls[i].y = Walls[i].y - rand.nextInt(150);
                                    GuardWalls[i].texture = wall_texture2;
                                }
                            }
                        } else if (i % 2 == 1) {
                            if (Walls[i].x < Walls[next(i, 2)].x && Math.abs(difference) >= game.cameraWidth * 1 / 6 && Walls[i].y > 800 && dist > 100) {
                                if (Walls[i].x < game.cameraWidth * 3 / 4 && Walls[i].x > game.cameraWidth * 2 / 3) {
                                    GuardWalls[i].x = game.cameraWidth - wallWidth / 2 - 5;
                                    GuardWalls[i].height = Walls[i].height + rand.nextInt(100) + 200;
                                    GuardWalls[i].y = Walls[i].y - rand.nextInt(100);
                                    GuardWalls[i].texture = wall_texture2;
                                }
                            }
                        }
                    }
                }
                for (int i = 0; i < num_walls; i += 2) {
                    SpecialWalls[i].updatePos();
                    if (SpecialWalls[i].y + SpecialWalls[i].height < -10) {
                        SpecialWalls[i].texture = wall_texture;
                        int proper = 1;
                        float distance = 0;
                        float dist = 0;
                        boolean yeah = true;
                        boolean above = true;
                        boolean go = false;
                        while (yeah) {
                            distance = Walls[next(i, -proper)].x - Walls[i].x;
                            if (Walls[next(i, -proper)].y < Walls[i].y) {
                                dist = Walls[i].y - Walls[next(i, -proper)].y - Walls[next(i, -proper)].height;
                                above = false;
                            } else {
                                dist = Walls[next(i, -proper)].y - Walls[i].y - Walls[i].height;
                                above = true;
                            }
                            if (Math.abs(distance) > game.cameraWidth * 5 / 12 && Math.abs(dist) > 25 && Math.abs(dist) < 350 || proper > num_walls) {
                                yeah = false;
                                go = true;
                            } else {
                                proper += 2;
                            }
                        }
                        float spacer = rand.nextInt(Math.round(distance * 1 / 8)) + distance * 1 / 8;
                        if (above && go) {
                            if (Walls[i].y > game.cameraHeight && Walls[next(i, -proper)].y > game.cameraHeight + dist) {
                                SpecialWalls[i].x = Walls[i].x + spacer + rand.nextInt(Math.round(distance - spacer * 2));
                                SpecialWalls[i].y = Walls[i].y + Walls[i].height / 2 + rand.nextInt(Math.round(Math.abs(dist)));
                                SpecialWalls[i].height = rand.nextInt(200) + 250;
                                SpecialWalls[i].texture = wall_texture3;
                            }
                        } else if (go) {
                            if (Walls[next(i, -proper)].y > game.cameraHeight && Walls[next(i, -proper)].y > game.cameraHeight + dist) {
                                SpecialWalls[i].x = Walls[i].x + spacer + rand.nextInt(Math.round(distance - spacer * 2));
                                SpecialWalls[i].y = Walls[next(i, -proper)].y + Walls[next(i, -proper)].height / 2 + rand.nextInt(Math.round(Math.abs(dist)));
                                SpecialWalls[i].height = rand.nextInt(200) + 250;
                                SpecialWalls[i].texture = wall_texture3;
                            }
                        }
                    }
                }
                //WALL LOGIC END

                //BALL LOGIC
            }
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
                            if(AllWalls[i].texture == wall_texture2){
                                score += 3;
                                this.game.cash += 3;
                            }
                            else if(AllWalls[i].texture == wall_texture3){
                                score += 2;
                                this.game.cash += 2;
                            }
                            else {
                                score++;
                                this.game.cash++;
                            }
                        }
                        Ball.xvel = -1 * abs(Ball.xvel);
                        Ball.x = AllWalls[i].x - Ball.width;
                    } else if (dir[i] == 'r') {
                        if (!lose) {
                            if(AllWalls[i].texture == wall_texture2){
                                score += 3;
                                this.game.cash += 3;
                            }
                            else if(AllWalls[i].texture == wall_texture3){
                                score += 2;
                                this.game.cash += 2;
                            }
                            else {
                                score++;
                                this.game.cash++;
                            }
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

    public void lose(){
        if(this.score > this.game.data.getInteger(gamemodeName+"_Highscore",0)){
            this.game.data.putInteger(gamemodeName+"_Highscore",this.score);
        }
        if(!lose){
            game.skin_Manager.getSound(6).play();
            this.game.data.putInteger("cash",this.game.cash);
            this.game.data.flush();
        }
        lose = true;

    }

    public boolean getLose(){
        return this.lose;
    }

    public void start(){
        this.started = true;
        this.score = 0;
        this.Ball.x = game.cameraWidth/2+game.ballSize;
        this.Ball.y = game.cameraHeight/8+game.ballSize;

        if(lose){
            this.init_GenerateWalls();
        }
        this.lose = false;
        Ball.xvel = Ball_init_vel;
    }
    public boolean getStarted(){
        return this.started;
    }
}

