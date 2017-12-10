package com.mygdx.gameV2;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.Preferences;
        import com.badlogic.gdx.graphics.Texture;

        import java.util.Random;

        import static java.lang.Math.abs;
/**
 * Created by Zachary on 12/9/2017.
 */

public class GM2 implements GameMode {

    B_Ball game;
    //protected int count1=0;
    protected Wall [] Walls;
    protected Ball Ball;

    Random rand = new Random();

    protected boolean started = false;
    protected boolean lose = false;
    public int score = 0;

    protected int num_walls = 8;

    protected int wallWidth = 65;
    protected float wall_yvel = -13;
    protected float wall_xvel= 0;

    protected float jumpspeed = 20;
    protected float gravity  = 1.3f;
    protected float Ball_init_vel = 6.8f;

    protected Texture wall_texture;
    protected Texture ball_texture;


    public String gamemodeName = "NUMBA2";

    public GM2(B_Ball game){
        this.game = game;
        this.ball_texture = game.T_ogBall;
        this.wall_texture = game.T_wallTexture;
        this.Walls = new Wall[num_walls];
        this.Ball = new Ball(game.cameraWidth/2+80, game.cameraHeight/8+80,80,80,0,0,game.skin_Manager.getBallSkin());
        this.init_GenerateWalls();
    }

    public Wall[] get_Walls(){
        return this.Walls;
    }

    public int getScore(){
        return score;
    }

    public Ball get_Ball(){
        return this.Ball;
    }




    protected void init_GenerateWalls(){
        Walls[0] = new Wall(game.cameraWidth/8+wallWidth/2,1100, wallWidth,500, wall_xvel, wall_yvel,wall_texture);
        Walls[1] = new Wall (game.cameraWidth*7/8-wallWidth/2,400,wallWidth,800,wall_xvel, wall_yvel,wall_texture);
        for(int i = 2; i<num_walls;i++){
            if(i%2==0) {
                Walls[i] = new Wall(game.cameraWidth / 8 + wallWidth / 2, generate_left_Wall_Y(i), wallWidth, generate_Wall_Height(), wall_xvel, wall_yvel, wall_texture);
            }else{
                Walls[i] = new Wall(game.cameraWidth*7/ 8 - wallWidth / 2, generate_right_Wall_Y(i), wallWidth, generate_Wall_Height(), wall_xvel, wall_yvel, wall_texture);
            }
        }
    }

    protected float generate_left_Wall_Y(int wall){
        int last_wall = wall-2;
        if(last_wall == -2){
            last_wall = num_walls-2;
        }
        return rand.nextInt(800) + Walls[last_wall].y+Walls[last_wall].height+Ball.height+10;
    }
    protected float generate_right_Wall_Y(int wall){
        int last_wall = wall-2;
        if(last_wall == -1){
            last_wall = num_walls-1;
        }
        return rand.nextInt(800) + Walls[last_wall].y+Walls[last_wall].height+Ball.height+10;
    }
    protected float generate_Wall_Height(){
        return rand.nextInt(900)+300-this.score;
    }

    public void touch_Update(){
        if(!started&&Gdx.input.justTouched()){
            this.start();
        }
        if(!lose){
            Ball.yvel=jumpspeed;
        }
    }
    public void update(){

        if(started){
            //WALL LOGIC
            if(!lose) {
                for (int i = 0; i<num_walls;i++) {
                    Walls[i].updatePos();
                    if(Walls[i].y+Walls[i].height < -10){
                        if(i%2==0){
                            Walls[i].y = generate_left_Wall_Y(i);
                        }else{
                            Walls[i].y = generate_right_Wall_Y(i);
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
                            this.game.cash++;
                        }
                        Ball.xvel=-1*abs(Ball.xvel);
                        Ball.x = Walls[i].x-Ball.width;
                    }else if(dir[i]=='r'){
                        if(!lose){
                            score++;
                            incDiff();
                            this.game.cash++;
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
        if(this.score > this.game.data.getInteger(gamemodeName+"_Highscore",0)){
            this.game.data.putInteger(gamemodeName+"_Highscore",this.score);
        }
        this.game.data.putInteger("cash",this.game.cash);
        this.game.data.flush();
        lose = true;
    }
    private void incDiff(){
        if(Ball.xvel>0){
            Ball.xvel+=Ball_init_vel/300;
        }else{
            Ball.xvel-=Ball_init_vel/300;
        }
        for(Wall wall:Walls){
            wall.yvel+=wall_yvel/130;
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
    public boolean getStarted(){
        return this.started;
    }
}
