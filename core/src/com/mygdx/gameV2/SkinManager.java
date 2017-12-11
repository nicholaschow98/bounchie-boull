package com.mygdx.gameV2;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
/**
 * Created by Nick on 2017-11-30.
 */

public class SkinManager{
    public int num_skins = 24;
    public int num_basic_skins = 24;
    public int current_skin = 0;

    String [][] filenames;
    Texture[] ballSkin_T;

    Preferences data;

    BallSkin ballSkin;
    B_Ball game;
    boolean [] unlocked;
    String [] button_file_names;

    String [] files;
    Sound [] sounds;
    int num_sounds = 8;


    public SkinManager(B_Ball game, Preferences data){
        this.game = game;
        this.data = data;
        this.current_skin = this.data.getInteger("selected_skin",0);
        loadSkinPaths();
        loadTextures();
        loadSkinUnlocked();
        loadSoundPaths();
        loadSounds();
        changeBallSkin(this.current_skin);
        this.button_file_names= new String[] {"sbutton/defButton_0.png","sbutton/defButton_1.png","sbutton/defButton_2.png"};
    }

    private void loadSoundPaths(){
        files = new String[num_sounds];
        for(int i = 0; i < num_sounds; i++){
            files[i] = "sounds/Sound" + i + ".wav";
        }
    }

    private void loadSounds(){
        sounds = new Sound[num_sounds];
        for(int i = 0; i < num_sounds; i++){
            sounds[i] = Gdx.audio.newSound(Gdx.files.internal(files[i]));
        }
    }


    private void loadSkinPaths(){
        filenames = new String[num_skins][10];
        for(int i = 0;i<num_basic_skins;i++){
            filenames[i][0] = "skins/Skin"+i+".png";
        }
    }

    private void loadTextures(){
        ballSkin_T= new Texture[num_skins];
        for(int i = 0; i < num_skins;i++){
            this.ballSkin_T[i] = new Texture(filenames[i][0]);
        }
    }

    private void loadSkinUnlocked(){
        unlocked = new boolean[num_skins];
        unlocked[0] = true;
        for(int i=1;i < num_skins;i++){
            unlocked[i]=this.data.getBoolean("skin_"+i,false);
        }
    }

    public Sound getSound(int id){
        return this.sounds[id];
    }

    public void deleteSkinProgress(){
        for(int i = 0; i<num_skins;i++){
            this.data.putBoolean("skin_"+i,false);
        }
        this.game.cash = 0;
        this.data.putInteger("cash",0);
        this.data.flush();
        loadSkinUnlocked();
        changeBallSkin(0);

    }
    public BallSkin getBallSkin(){
        return this.ballSkin;
    }

    public int getWallSkin(){
        return 0;
    }

    public void unlockSkin(int i){
        this.unlocked[i] = true;
        this.data.putBoolean("skin_"+i,true);
        this.data.putInteger("selected_skin",i);
        this.data.flush();
    }

    public void changeBallSkin(int i){
        this.current_skin = i;
        this.ballSkin = new BasicBallSkin(filenames[i][0], sounds);
        this.data.putInteger("selected_skin",i);
        this.data.flush();
    }
}
