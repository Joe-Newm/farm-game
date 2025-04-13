package com.farmgame.farmgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player {
    float speed;
    Texture playTex;
    Sprite sprite;
    Vector2 position;
    boolean lookDirection;

    public Player(float speed) {
        this.speed = speed;
        this.playTex = new Texture(Gdx.files.internal("player/player.png"));
        this.sprite = new Sprite(playTex);
        sprite.scale(1);
        position = new Vector2( 100  , 100);
    }

    public void update(float delta) {
        boolean movingLeft = false;
        boolean movingRight = false;

        if (Gdx.input.isKeyPressed(Keys.A)) {
            position.x -= delta * speed;
            movingLeft = true;
        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            position.x += delta * speed;
            movingRight = true;
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            position.y += delta * speed;
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            position.y -= delta * speed;
        }

        if (movingLeft && !sprite.isFlipX()) {
            sprite.flip(true, false); // Flip horizontally to face left
        }
        if (movingRight && sprite.isFlipX()) {
            sprite.flip(true, false); // Flip back to face right
        }

    }

    public void draw(SpriteBatch batch, float delta) {
        update(delta);
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }
}
