package com.mygdx.gameV2;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Nick on 2017-11-30.
 */

public class SkinManager {
    public int num_skins = 5;
    public int current_skin = 0;
    String [] filenames;

    Texture[] ballSkin_T;
    BallSkin ballSkin;
    B_Ball game;

    public SkinManager(B_Ball game){
        this.game = game;
        ballSkin_T= new Texture[num_skins];
        filenames = new String[num_skins];
        loadSkinPaths();
        loadTextures();
        this.ballSkin = new BasicBallSkin(ballSkin_T[1]);
    }

    private void loadTextures(){
        for(int i = 0; i < num_skins;i++){
            this.ballSkin_T[i] = new Texture(filenames[i]);
        }
    }
    private void loadSkinPaths(){
        filenames[0] = "ogball_icon.png";
        filenames[1] = "badlogic.jpg";
        filenames[2] = "shop_icon.png";
        filenames[3] = "back_icon_1.png";
        filenames[4] = "testwall.jpg";
    }
    public BallSkin getBallSkin(){
        return this.ballSkin;
    }
    public int getWallSkin(){
        return 0;
    }
    public void changeBallSkin(int i){
        this.current_skin = i;
        this.ballSkin = new BasicBallSkin(ballSkin_T[i]);
    }
}
