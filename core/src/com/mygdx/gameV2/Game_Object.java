package com.mygdx.gameV2;

import com.badlogic.gdx.graphics.Texture;

import java.util.Arrays;

import static java.lang.Math.abs;

/**
 * Created by Nick on 2017-11-29.
 */

public class Game_Object extends Renderable{
    float xvel,yvel;
    boolean active;

    public Game_Object(){
        super(0, 0, 100, 100, new Texture("badlogic.jpg"));
        xvel = 0;
        yvel = 0;
        active = true;
    }
    public Game_Object(float x, float y, float width, float height, float xvel, float yvel, Texture texture){
        super(x, y, width, height, texture);
        this.xvel = xvel;
        this.yvel = yvel;
        active = true;
    }

    public void updatePos(){
        this.x+=this.xvel;
        this.y+=this.yvel;
    }

    /*
    public void checkForCollison(Game_Object [] game_objects, boolean [] collided){
        if(active == false){
            return;
        }
        collided = new boolean[game_objects.length];
        Arrays.fill(collided, false);

        for(int i = 0; i <game_objects.length;i++){
            if(collision(game_objects[i])&&game_objects[i]!=this){
                collided[i] = true;
            }
        }
    }
    */


    public void checkForDirectionalCollision(Game_Object [] game_objects, boolean [] collided, char [] direction) {

        if(active == false){
            return;
        }
        //collided = new boolean[game_objects.length];
        //direction = new char[game_objects.length];
        Arrays.fill(direction,'0');
        Arrays.fill(collided, false);

        for(int i = 0;i<game_objects.length;i++){

            float w = (float)(0.5 * (this.width + game_objects[i].width));
            float h = (float)(0.5 * (this.height + game_objects[i].height));

            float dx = (float)((this.x + this.width * 0.5) - (game_objects[i].x + game_objects[i].width*0.5));
            float dy = (float)((this.y + this.height * 0.5) - (game_objects[i].y + game_objects[i].height*0.5));

            if (abs(dx) <= w && abs(dy) <= h) {
                collided[i]=true;
                this.onCollide();
                float wy = w * dy;
                float hx = h * dx;

                if (wy > hx) {
                    if (wy > -hx) {
                      direction[i] = 'u';
                    } else {
                        direction[i] = 'l';
                    }
                } else {
                    if (wy > -hx) {
                        direction[i] = 'r';
                    } else {
                        direction[i] = 'd';
                    }
                }
            }
        }
    }

    public void onCollide(){
        //noething
    };

    private boolean collision(Game_Object go){
        if (go.x < this.x + this.width &&
                go.x + go.width > this.x &&
                go.y < this.y + this.height &&
                go.height + go.y > this.y) {
            return true;
        }else{
            return false;
        }
    }

    public static char directionalCollide(Game_Object o1, Game_Object o2){//'0' is no collide, up down left right -> u d l r
        float w = (float)(0.5 * (o1.width + o2.width));
        float h = (float)(0.5 * (o1.height + o2.height));

        float dx = (float)((o1.x + o1.width * 0.5) - (o2.x + o2.width*0.5));
        float dy = (float)((o1.y + o1.height * 0.5) - (o2.y + o2.height*0.5));

        if (abs(dx) <= w && abs(dy) <= h) {
            float wy = w * dy;
            float hx = h * dx;

            if (wy > hx) {
                if (wy > -hx) {
                    return 'u';
                } else {
                    return 'l';
                }
            } else {
                if (wy > -hx) {
                    return 'r';
                } else {
                    return 'd';
                }
            }
        }
        return '0';
    }
}
