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
import com.farmgame.farmgame.HUD.ItemSelector;
import com.farmgame.farmgame.items.Item;

public class Player {
    public float speed;
    public Texture playTex;
    public static Sprite sprite;
    public static Vector2 position;
    public boolean lookDirection;
    public Rectangle boundingBox;
    public float prevX;
    public float prevY;
    public TextureRegion currentFrame;
    public static boolean isFlipped;
    public static Vector2 drawOffset = new Vector2();
    public float tmpSpeed;
    public static Item[] inventory;
    public Rectangle hitBox;
    public Item pickaxe;


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

        // inventory
        this.inventory = new Item[9];
        //add pickaxe to inventory
        pickaxe = new Item("pickaxe", new Texture(Gdx.files.internal("items/pickaxe-item.png")), 1);
        inventory[0] = pickaxe;
    }

    public void controls(float delta, TextureRegion currentFrame) {
        boolean movingLeft = false;
        boolean movingRight = false;
        boolean moving = false;

        // movement
        if (PlayerAnim.selectedAnimation != 2) {
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
        if (!moving) {
            PlayerAnim.selectedAnimation = 0;
        } else {
            PlayerAnim.selectedAnimation = 1;
        }
    }

    public void hitBox() {
        float spriteX = sprite.getX();
        float spriteY = sprite.getY();
        float spriteWidth = sprite.getWidth() ;
        float spriteHeight = sprite.getHeight();

        if (PlayerAnim.selectedAnimation == 2) {
            if (isFlipped) {
                hitBox = new Rectangle(spriteX - 16, spriteY, spriteWidth, spriteHeight);
            } else {
                hitBox = new Rectangle(spriteX + spriteWidth, spriteY, spriteWidth, spriteHeight);
            }
        } else {
            hitBox = null;
        }
    }
    public void listenForItemAction() {
        // listen for item action.
        if (inventory[ItemSelector.selectedSlot] != null) {
            inventory[ItemSelector.selectedSlot].action();
        }
    }

    public void update(float delta) {
        boundingBox = new Rectangle(position.x, position.y, sprite.getWidth() * sprite.getScaleX(), sprite.getHeight() * sprite.getScaleY());
        listenForItemAction();
        hitBox();
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
