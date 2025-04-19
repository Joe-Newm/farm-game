package com.farmgame.farmgame.entity;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerAnim {
    public static Animation<TextureRegion> walkAnimation; // Must declare frame type (TextureRegion)
    public static Animation<TextureRegion> idleAnimation;
    public static Animation<TextureRegion> swingPickaxeAnimation;
    public static ArrayList<Animation<TextureRegion>> animations;
    public static Texture walkSheet;
    public static Texture swingPickaxeSheet;
    public static float walkAnimationTime;
    public static int selectedAnimation;
    public static void create() {
        walkSheet = new Texture(Gdx.files.internal("player/player-anim.png"));
        swingPickaxeSheet = new Texture(Gdx.files.internal("player/pickaxe-swing.png"));

        // animations
        //walk anim sheet
        TextureRegion[][] tmpFrames = TextureRegion.split(walkSheet, walkSheet.getWidth() / 4, walkSheet.getHeight() / 1);
        //swing pickaxe sheet
        TextureRegion[][] tmpSwingFrames = TextureRegion.split(swingPickaxeSheet, swingPickaxeSheet.getWidth() / 2, swingPickaxeSheet.getHeight() / 1);

        // walk anim
        TextureRegion[] walkFrames = new TextureRegion[3];
        walkFrames[0] = tmpFrames[0][1];
        walkFrames[1] = tmpFrames[0][2];
        walkFrames[2] = tmpFrames[0][3];
        //swing pickaxe anim
        TextureRegion[] swingFrames = new TextureRegion[2];
        swingFrames[0] = tmpSwingFrames[0][0];
        swingFrames[1] = tmpSwingFrames[0][1];
        //idle anim
        TextureRegion[] idleFrame = new TextureRegion[1];
        idleFrame[0] = tmpFrames[0][0];

        // Create the animation
        walkAnimation = new Animation<TextureRegion>(0.1f, walkFrames);
        idleAnimation = new Animation<TextureRegion>(0.1f, idleFrame);
        swingPickaxeAnimation = new Animation<TextureRegion>(0.1f, swingFrames);

        // add animations to arraylist
        selectedAnimation = 0;
        animations = new ArrayList<>();

        //0
        animations.add(idleAnimation);
        //1
        animations.add(walkAnimation);
        //2
        animations.add(swingPickaxeAnimation);
    }
}
