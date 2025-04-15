package com.farmgame.farmgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    public Player(float speed) {
        this.speed = speed;
        this.playTex = new Texture(Gdx.files.internal("player/player2.png"));

        this.sprite = new Sprite(playTex);
        position = new Vector2( 100  , 100);
        boundingBox = new Rectangle(position.x, position.y, sprite.getWidth() * sprite.getScaleX(), sprite.getHeight() * sprite.getScaleY());
        prevX = 0;
        prevY = 0;
    }

    public void controls(float delta) {
        boolean movingLeft = false;
        boolean movingRight = false;

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

        if (movingLeft && !sprite.isFlipX()) {
            sprite.flip(true, false); // Flip horizontally to face left
        }
        if (movingRight && sprite.isFlipX()) {
            sprite.flip(true, false); // Flip back to face right
        }

    }

    public void update(float delta) {
        boundingBox = new Rectangle(position.x, position.y, sprite.getWidth() * sprite.getScaleX(), sprite.getHeight() * sprite.getScaleY());
    }

    public void draw(SpriteBatch batch, float delta) {
        controls(delta);
        update(delta);
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }
}
