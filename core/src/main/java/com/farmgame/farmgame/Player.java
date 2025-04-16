package com.farmgame.farmgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Player {
    float speed;
    Texture playTex;
    Sprite sprite;
    Vector2 position;
    boolean lookDirection;
    public Rectangle boundingBox;
    public float prevX;
    public float prevY;
    public Animation<TextureRegion> walkAnimation; // Must declare frame type (TextureRegion)
    public Animation<TextureRegion> idleAnimation;
    public Animation<TextureRegion> swingPickaxeAnimation;
    public ArrayList<Animation<TextureRegion>> animations;
    public Texture walkSheet;
    public Texture swingPickaxeSheet;
    float walkAnimationTime;
    public int selectedAnimation;

    public Player(float speed) {
        this.speed = speed;
        this.playTex = new Texture(Gdx.files.internal("player/player2.png"));
        walkSheet = new Texture(Gdx.files.internal("player/player-anim.png"));
        swingPickaxeSheet = new Texture(Gdx.files.internal("player/pickaxe-swing.png"));
        this.sprite = new Sprite(playTex);
        position = new Vector2( 100  , 100);
        boundingBox = new Rectangle(position.x, position.y, sprite.getWidth() * sprite.getScaleX(), sprite.getHeight() * sprite.getScaleY());
        prevX = 0;
        prevY = 0;

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
        animations.add(idleAnimation);
        animations.add(walkAnimation);
        animations.add(swingPickaxeAnimation);
    }

    public void controls(float delta, TextureRegion currentFrame) {
        boolean movingLeft = false;
        boolean movingRight = false;
        boolean moving = false;


        // movement
        if (Gdx.input.isKeyPressed(Keys.A)) {
            prevX = position.x;
            position.x -= delta * speed;
            movingLeft = true;
            moving = true;
            idleAnimation.setPlayMode(Animation.PlayMode.LOOP);
        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            prevX = position.x;
            position.x += delta * speed;
            movingRight = true;
            moving = true;

            walkAnimation.setPlayMode(Animation.PlayMode.LOOP);
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            prevY = position.y;
            position.y += delta * speed;

            moving = true;
            walkAnimation.setPlayMode(Animation.PlayMode.LOOP);
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            prevY = position.y;
            position.y -= delta * speed;
            moving = true;
            walkAnimation.setPlayMode(Animation.PlayMode.LOOP);
        }
        if (movingLeft && !currentFrame.isFlipX()) {
            for (Animation<TextureRegion> anim : animations) {
                for (TextureRegion frame : anim.getKeyFrames()) {
                    if (!frame.isFlipX()) {
                        frame.flip(true, false);
                    }
                }
            }
        }
        if (movingRight && currentFrame.isFlipX()) {
            for (Animation<TextureRegion> anim : animations) {
                for (TextureRegion frame : anim.getKeyFrames()) {
                    if (frame.isFlipX()) {
                        frame.flip(true, false);
                    }
                }
            }
        }
        if (!moving) {
            selectedAnimation = 0;
        } else {
            selectedAnimation = 1;
        }

        // swing pickaxe
        if (Gdx.input.isKeyPressed(Keys.SPACE)) {
            selectedAnimation = 2;
            swingPickaxeAnimation.setPlayMode(Animation.PlayMode.LOOP);
        }

    }

    public void update(float delta) {
        boundingBox = new Rectangle(position.x, position.y, sprite.getWidth() * sprite.getScaleX(), sprite.getHeight() * sprite.getScaleY());
    }

    public void draw(SpriteBatch batch, float delta) {
        update(delta);
        TextureRegion currentFrame = animations.get(selectedAnimation).getKeyFrame(walkAnimationTime);
        sprite.setPosition(position.x, position.y);
        batch.draw(currentFrame, position.x, position.y);
        walkAnimationTime += delta;
        controls(delta, currentFrame);
    }
}
