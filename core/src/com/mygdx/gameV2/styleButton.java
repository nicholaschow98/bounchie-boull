package com.mygdx.gameV2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Nick on 2017-12-08.
 */

public class styleButton extends Button {

    Texture left_texture;
    Texture right_texture;
    float fontWidth;
    float fontHeight;
    String text;
    BitmapFont font;
    float scale;
    float h_padding = 5;
    float v_padding = 15;

    public styleButton(float scale, String text, float x, float y, String [] filenames){
        super(x, y, new Texture(filenames[0]));
        this.left_texture = new Texture(filenames[1]);
        this.right_texture = new Texture(filenames[2]);

        this.scale = scale;

        this.text = text;
        this.font =  new BitmapFont(Gdx.files.internal("arcade.fnt"),Gdx.files.internal("arcade.png"), false);
        this.font.setColor(Color.BLACK);
        this.font.getData().setScale(scale);
        GlyphLayout layout = new GlyphLayout(font, text);
        this.fontWidth = layout.width;
        this.fontHeight = layout.height;

        this.width = h_padding *scale*2+fontWidth+left_texture.getWidth()+right_texture.getWidth();
        this.height = v_padding *scale*2+fontHeight;
    }
    public styleButton( String text, float x, float y, String [] filenames){
        super(x, y, new Texture(filenames[0]));
        this.left_texture = new Texture(filenames[1]);
        this.right_texture = new Texture(filenames[2]);
        this.text = text;
        this.font = new BitmapFont(Gdx.files.internal("arcade.fnt"),Gdx.files.internal("arcade.png"), false);
        GlyphLayout layout = new GlyphLayout(font, text);
        this.fontWidth = layout.width;
        this.fontHeight = layout.height;
        this.width = h_padding *2+fontWidth;
        this.height = v_padding *2+fontHeight;
    }
    public styleButton(float scale, String text,float x, float y, float width, float height, String [] filenames){
        super(x, y, width, height, new Texture(filenames[0]));
        this.left_texture = new Texture(filenames[1]);
        this.right_texture = new Texture(filenames[2]);
        this.scale = scale;
        this.text = text;
        this.font = new BitmapFont(Gdx.files.internal("arcade.fnt"),Gdx.files.internal("arcade.png"), false);
        this.font.getData().setScale(scale);
        GlyphLayout layout = new GlyphLayout(font, text);
        this.fontWidth = layout.width;
        this.fontHeight = layout.height;
        this.width = h_padding *scale*2+fontWidth;
        this.height = v_padding *scale*2+fontHeight;
    }
    public styleButton(float scale, String text, float x, float y, float width, float height,boolean active, String [] filenames){
        super(x, y, width, height, active, new Texture(filenames[0]));
        this.left_texture = new Texture(filenames[1]);
        this.right_texture = new Texture(filenames[2]);
        this.scale = scale;
        this.text = text;
        this.font = new BitmapFont(Gdx.files.internal("arcade.fnt"),Gdx.files.internal("arcade.png"), false);
        this.font.getData().setScale(scale);
        GlyphLayout layout = new GlyphLayout(font, text);
        this.fontWidth = layout.width;
        this.fontHeight = layout.height;
        this.width = h_padding *scale*2+fontWidth;
        this.height = v_padding *scale*2+fontHeight;
    }
    public void drawSelf(SpriteBatch batch){
        batch.draw(left_texture,x,y,left_texture.getWidth(),height);
        batch.draw(texture,x+left_texture.getWidth(),y, width-right_texture.getWidth(), height);
        batch.draw(right_texture,x+width-right_texture.getWidth(),y,right_texture.getWidth(),height);
        font.draw(batch,this.text,x+left_texture.getWidth()+ h_padding *scale,this.y+this.height- v_padding *this.scale);
    }
}
