package com.mygdx.gameV2;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static java.lang.Math.abs;

/**
 * Created by Nick on 2017-12-03.
 */

public class AnimatedBallSkin implements BallSkin {
    private Texture ballTexture;
    private Texture [] textures;
    private int numTextures;
    private int currentTexture;
    private String name;
    private String description;
    private int id;
    private int ide;
    private Sound[] sounds;
    public AnimatedBallSkin(String [] filename, Sound[] goodsound){

        this.numTextures = filename.length;
        this.textures = new Texture[numTextures];
        currentTexture = 0;
        for(int i = 0;i<numTextures;i++){
            textures[i] = new Texture(filename[i]);
        }
        this.ballTexture = textures[currentTexture];
        this.sounds = goodsound;
    }
    public Texture getTexture(){
        return this.ballTexture;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public int getID(){
        return this.id;
    }

    public void onCollide(Ball ball){
        float vol = (abs(ball.xvel)+abs(ball.yvel))/10;
        if(vol>=1){
            vol = 1;
        }
        this.playSound(vol);
        currentTexture++;
        if(currentTexture>=numTextures){
            currentTexture=0;
        }
        this.ballTexture = textures[currentTexture];
    }

    public void playSound(float vol){
        sounds[0].play();
    }

    public void onDeath(Ball ball){
        //nothin
    }

    public void onPickUp(Ball ball){
        //nothin
    }

    public void onTouch(Ball ball){
        //nothin
    }

    public void skinRender(SpriteBatch batch){
        //nothin
    }
}
