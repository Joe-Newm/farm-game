package com.farmgame.farmgame.entity;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerAnim {
    public static Animation<TextureRegion> walkAnimationLeftRight;
    public static Animation<TextureRegion> walkAnimationUp;
    public static Animation<TextureRegion> walkAnimationDown;
    public static Animation<TextureRegion> idleAnimationLeftRight;
    public static Animation<TextureRegion> idleAnimationUp;
    public static Animation<TextureRegion> idleAnimationDown;
    public static Animation<TextureRegion> swingPickaxeAnimation;
    public static Animation<TextureRegion> swingAxeAnimation;
    public static ArrayList<Animation<TextureRegion>> animations;
    public static Texture walkSheet;
    public static Texture swingPickaxeSheet;
    public static Texture swingAxeSheet;
    public static float walkAnimationTime;
    public static int selectedAnimation;

    public static void create() {
        walkSheet = new Texture(Gdx.files.internal("player/player-anim-v2.png"));
        swingPickaxeSheet = new Texture(Gdx.files.internal("player/pickaxe-swing.png"));
        swingAxeSheet = new Texture(Gdx.files.internal("player/axe-swing.png"));

        // animations
        //walk anim sheet
        TextureRegion[][] tmpFrames = TextureRegion.split(walkSheet, walkSheet.getWidth() / 4, walkSheet.getHeight() / 3);
        //swing pickaxe sheet
        TextureRegion[][] tmpSwingFrames = TextureRegion.split(swingPickaxeSheet, swingPickaxeSheet.getWidth() / 2, swingPickaxeSheet.getHeight() / 1);
        //swing axe sheet
        TextureRegion[][] tmpAxeSwingFrames = TextureRegion.split(swingAxeSheet, swingAxeSheet.getWidth() / 2, swingAxeSheet.getHeight() / 1);

        // walk anim (left right)
        TextureRegion[] walkFramesHorizontal = new TextureRegion[3];
        walkFramesHorizontal[0] = tmpFrames[0][1];
        walkFramesHorizontal[1] = tmpFrames[0][2];
        walkFramesHorizontal[2] = tmpFrames[0][3];
        // walk anim (up)
        TextureRegion[] walkFramesUp = new TextureRegion[3];
        walkFramesUp[0] = tmpFrames[2][1];
        walkFramesUp[1] = tmpFrames[2][2];
        walkFramesUp[2] = tmpFrames[2][3];
        //walk anim (down)
        TextureRegion[] walkFramesDown = new TextureRegion[3];
        walkFramesDown[0] = tmpFrames[1][1];
        walkFramesDown[1] = tmpFrames[1][2];
        walkFramesDown[2] = tmpFrames[1][3];
        //swing pickaxe anim
        TextureRegion[] swingFrames = new TextureRegion[2];
        swingFrames[0] = tmpSwingFrames[0][0];
        swingFrames[1] = tmpSwingFrames[0][1];
        //swing axe anim
        TextureRegion[] swingAxeFrames = new TextureRegion[2];
        swingAxeFrames[0] = tmpAxeSwingFrames[0][0];
        swingAxeFrames[1] = tmpAxeSwingFrames[0][1];
        //idle (left right) anim
        TextureRegion[] idleFrame = new TextureRegion[1];
        idleFrame[0] = tmpFrames[0][0];
        //idle (up) anim
        TextureRegion[] idleUpFrame = new TextureRegion[1];
        idleUpFrame[0] = tmpFrames[2][0];
        //idle (down) anim
        TextureRegion[] idleDownFrame = new TextureRegion[1];
        idleDownFrame[0] = tmpFrames[1][0];

        // Create the animation
        walkAnimationLeftRight = new Animation<TextureRegion>(0.1f, walkFramesHorizontal);
        walkAnimationUp = new Animation<TextureRegion>(0.1f, walkFramesUp);
        walkAnimationDown = new Animation<TextureRegion>(0.1f, walkFramesDown);
        idleAnimationLeftRight = new Animation<TextureRegion>(0.1f, idleFrame);
        idleAnimationUp = new Animation<TextureRegion>(0.1f, idleUpFrame);
        idleAnimationDown = new Animation<TextureRegion>(0.1f, idleDownFrame);
        swingPickaxeAnimation = new Animation<TextureRegion>(0.1f, swingFrames);
        swingAxeAnimation = new Animation<TextureRegion>(0.1f, swingAxeFrames);

        // add animations to arraylist
        selectedAnimation = 0;
        animations = new ArrayList<>();

        //0
        animations.add(idleAnimationLeftRight);
        //1
        animations.add(idleAnimationUp);
        //2
        animations.add(idleAnimationDown);
        //3
        animations.add(walkAnimationLeftRight);
        //4
        animations.add(walkAnimationUp);
        //5
        animations.add(walkAnimationDown);
        //6
        animations.add(swingPickaxeAnimation);
        //7
        animations.add(swingAxeAnimation);
    }
}
