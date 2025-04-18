package com.farmgame.farmgame.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    public float speed;
    public Texture playTex;
    public Sprite sprite;
    public Vector2 position;
    public boolean lookDirection;
    public Rectangle boundingBox;
    public float prevX;
    public float prevY;
    public TextureRegion currentFrame;
    public boolean isFlipped;
    public Vector2 drawOffset = new Vector2();
    public float tmpSpeed;


    public Player(float speed) {
        this.speed = speed;
        tmpSpeed = this.speed;
        this.playTex = new Texture(Gdx.files.internal("player/player2.png"));

        this.sprite = new Sprite(playTex);
        position = new Vector2( 100  , 100);
        boundingBox = new Rectangle(position.x, position.y, sprite.getWidth() * sprite.getScaleX(), sprite.getHeight() * sprite.getScaleY());
        prevX = 0;
        prevY = 0;

        //animations
        PlayerAnim.create();
        PlayerAnim.walkAnimation.setPlayMode(Animation.PlayMode.LOOP);
        isFlipped = false;

        currentFrame = PlayerAnim.animations.get(PlayerAnim.selectedAnimation).getKeyFrame(PlayerAnim.walkAnimationTime);
        isFlipped = currentFrame.isFlipX();
    }

    public void controls(float delta, TextureRegion currentFrame) {
        boolean movingLeft = false;
        boolean movingRight = false;
        boolean moving = false;

        // movement
        if (!Gdx.input.isKeyPressed(Keys.SPACE)) {
            if (Gdx.input.isKeyPressed(Keys.A)) {
                prevX = position.x;
                position.x -= delta * tmpSpeed;
                movingLeft = true;
                moving = true;
            }
            if (Gdx.input.isKeyPressed(Keys.D)) {
                prevX = position.x;
                position.x += delta * tmpSpeed;
                movingRight = true;
                moving = true;
            }
            if (Gdx.input.isKeyPressed(Keys.W)) {
                prevY = position.y;
                position.y += delta * tmpSpeed;
                moving = true;
            }
            if (Gdx.input.isKeyPressed(Keys.S)) {
                prevY = position.y;
                position.y -= delta * tmpSpeed;
                moving = true;
            }
        }
        if (movingLeft && !currentFrame.isFlipX()) {
            for (Animation<TextureRegion> anim : PlayerAnim.animations) {
                for (TextureRegion frame : anim.getKeyFrames()) {
                    if (!frame.isFlipX()) {
                        frame.flip(true, false);
                        isFlipped = true;
                    }
                }
            }
        }
        if (movingRight && currentFrame.isFlipX()) {
            for (Animation<TextureRegion> anim : PlayerAnim.animations) {
                for (TextureRegion frame : anim.getKeyFrames()) {
                    if (frame.isFlipX()) {
                        frame.flip(true, false);
                        isFlipped = false;
                    }
                }
            }
        }
        System.out.println(isFlipped);
        if (!moving) {
            PlayerAnim.selectedAnimation = 0;
        } else {
            PlayerAnim.selectedAnimation = 1;
        }

        // swing pickaxe
        if (Gdx.input.isKeyPressed(Keys.SPACE)) {
            PlayerAnim.selectedAnimation = 2;
            PlayerAnim.swingPickaxeAnimation.setPlayMode(Animation.PlayMode.LOOP);
            if (isFlipped && PlayerAnim.selectedAnimation == 2) {
                drawOffset.x = -16;
            }
        } else {
            drawOffset.x = 0;
        }
        System.out.println(drawOffset.x);
    }

    public void update(float delta) {
        boundingBox = new Rectangle(position.x, position.y, sprite.getWidth() * sprite.getScaleX(), sprite.getHeight() * sprite.getScaleY());
    }

    public void draw(SpriteBatch batch, float delta) {
        update(delta);
        TextureRegion currentFrame = PlayerAnim.animations.get(PlayerAnim.selectedAnimation).getKeyFrame(PlayerAnim.walkAnimationTime);
        sprite.setPosition(position.x, position.y);
        batch.draw(currentFrame, position.x + drawOffset.x, position.y);
        PlayerAnim.walkAnimationTime += delta;
        controls(delta, currentFrame);
    }
}
