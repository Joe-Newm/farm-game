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
    public Texture walkSheet;
    float walkAnimationTime;
    private static final int FRAME_ROWS = 1, FRAME_COLS = 4;

    public Player(float speed) {
        this.speed = speed;
        this.playTex = new Texture(Gdx.files.internal("player/player2.png"));
        walkSheet = new Texture(Gdx.files.internal("player/player-anim.png"));
        this.sprite = new Sprite(playTex);
        position = new Vector2( 100  , 100);
        boundingBox = new Rectangle(position.x, position.y, sprite.getWidth() * sprite.getScaleX(), sprite.getHeight() * sprite.getScaleY());
        prevX = 0;
        prevY = 0;

        //walk anim
        TextureRegion[][] tmpFrames = TextureRegion.split(walkSheet,
            walkSheet.getWidth() / FRAME_COLS,
            walkSheet.getHeight() / FRAME_ROWS);
        // Flatten the 2D array into 1D for the animation
        TextureRegion[] walkFrames = new TextureRegion[3];
        walkFrames[0] = tmpFrames[0][1];
        walkFrames[1] = tmpFrames[0][2];
        walkFrames[2] = tmpFrames[0][3];

// Create the animation
        walkAnimation = new Animation<TextureRegion>(0.1f, walkFrames);
        walkAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void controls(float delta) {
        boolean movingLeft = false;
        boolean movingRight = false;
        TextureRegion currentFrame = walkAnimation.getKeyFrame(walkAnimationTime);

        if (Gdx.input.isKeyPressed(Keys.A)) {

            prevX = position.x;
            position.x -= delta * speed;
            movingLeft = true;
        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            prevX = position.x;
            position.x += delta * speed;
            movingRight = true;
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            prevY = position.y;
            position.y += delta * speed;
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            prevY = position.y;
            position.y -= delta * speed;
        }

        if (movingLeft && !currentFrame.isFlipX()) {
            currentFrame.flip(true, false); // Flip horizontally to face left
        }
        if (movingRight && currentFrame.isFlipX()) {
            currentFrame.flip(true, false); // Flip back to face right
        }

        //animation

    }

    public void update(float delta) {
        boundingBox = new Rectangle(position.x, position.y, sprite.getWidth() * sprite.getScaleX(), sprite.getHeight() * sprite.getScaleY());
    }

    public void draw(SpriteBatch batch, float delta) {
        controls(delta);
        update(delta);
        sprite.setPosition(position.x, position.y);
        TextureRegion currentFrame = walkAnimation.getKeyFrame(walkAnimationTime);
        batch.draw(currentFrame, position.x, position.y);
        walkAnimationTime += delta;
    }
}
