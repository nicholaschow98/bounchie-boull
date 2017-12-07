package com.mygdx.gameV2;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Nick on 2017-11-30.
 */

public class SkinManager {
    public int num_skins = 5;
    public int current_skin = 0;

    String [] filenames;
    Texture[] ballSkin_T;

    Preferences data;

    BallSkin ballSkin;
    B_Ball game;
    boolean [] unlocked;

    public SkinManager(B_Ball game, Preferences data){
        this.game = game;
        this.data = data;
        this.current_skin = this.data.getInteger("selected_skin",0);
        loadSkinPaths();
        loadTextures();
        loadSkinUnlocked();
        this.ballSkin = new BasicBallSkin(ballSkin_T[1]);
    }

    private void loadTextures(){
        ballSkin_T= new Texture[num_skins];
        for(int i = 0; i < num_skins;i++){
            this.ballSkin_T[i] = new Texture(filenames[i]);
        }
    }
    private void loadSkinPaths(){
        filenames = new String[num_skins];
        filenames[0] = "ogball_icon.png";
        filenames[1] = "badlogic.jpg";
        filenames[2] = "shop_icon.png";
        filenames[3] = "back_icon_1.png";
        filenames[4] = "testwall.jpg";
    }
    private void loadSkinUnlocked(){
        unlocked = new boolean[num_skins];
        unlocked[0] = true;
        for(int i=1;i < num_skins;i++){
            unlocked[i]=this.data.getBoolean("skin_"+i,false);
        }
    }
    public BallSkin getBallSkin(){
        return this.ballSkin;
    }
    public int getWallSkin(){
        return 0;
    }

    public void unlockSkin(int i){
        this.unlocked[i] = true;
        this.current_skin = i;
        this.data.putBoolean("skin_"+i,true);
        this.data.putInteger("selected_skin",i);
    }

    public void changeBallSkin(int i){
        this.current_skin = i;
        this.ballSkin = new BasicBallSkin(ballSkin_T[i]);
        this.data.putInteger("selected_skin",i);
        this.data.flush();
    }
}
