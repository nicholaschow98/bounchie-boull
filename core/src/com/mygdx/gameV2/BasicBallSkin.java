package com.mygdx.gameV2;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Nick on 2017-12-03.
 */

public class BasicBallSkin implements BallSkin {
    private Texture ballTexture;
    private String name;
    private String description;
    private int id;
    private int ide;
    private Sound[] sounds;
    public BasicBallSkin(String filename, Sound[] goodsound){
        this.ballTexture = new Texture(filename);
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

    }

    public void playSound(){
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
