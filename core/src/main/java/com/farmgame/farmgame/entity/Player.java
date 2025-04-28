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
import com.farmgame.farmgame.utils.Drawable;
import com.farmgame.farmgame.HUD.ItemSelector;
import com.farmgame.farmgame.items.Item;

public class Player implements Drawable {
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
    public static boolean facingUp = false;
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
        PlayerAnim.walkAnimationLeftRight.setPlayMode(Animation.PlayMode.LOOP);
        PlayerAnim.walkAnimationUp.setPlayMode(Animation.PlayMode.LOOP);
        PlayerAnim.walkAnimationDown.setPlayMode(Animation.PlayMode.LOOP);
        isFlipped = false;

        currentFrame = PlayerAnim.animations.get(PlayerAnim.selectedAnimation).getKeyFrame(PlayerAnim.walkAnimationTime);
        isFlipped = currentFrame.isFlipX();

        // inventory
        this.inventory = new Item[18];
        //add pickaxe to inventory
    }

    public void controls(float delta, TextureRegion currentFrame) {
        boolean movingLeft = false;
        boolean movingRight = false;
        boolean movingUp = false;
        boolean movingDown = false;
        boolean moving = false;

        Vector2 direction = new Vector2(0,0);

        // movement
        if (PlayerAnim.selectedAnimation != 6 && PlayerAnim.selectedAnimation != 7) {
            if (Gdx.input.isKeyPressed(Keys.A)) {
                direction.x -= 1;
                movingLeft = true;
                moving = true;
            }
            if (Gdx.input.isKeyPressed(Keys.D)) {
                direction.x += 1;
                movingRight = true;
                moving = true;
            }
            if (Gdx.input.isKeyPressed(Keys.W)) {
                direction.y += 1;
                movingUp = true;
                moving = true;
            }
            if (Gdx.input.isKeyPressed(Keys.S)) {
                direction.y -= 1;
                movingDown = true;
                moving = true;
            }
            if (!direction.isZero()) {
                direction.nor();
                prevX = position.x;
                prevY = position.y;
                position.add(direction.scl(delta * tmpSpeed));
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
        } else if (movingRight || movingLeft) {
            PlayerAnim.selectedAnimation = 3;
        } else if (movingUp) {
            PlayerAnim.selectedAnimation = 4;
        } else if (movingDown) {
            PlayerAnim.selectedAnimation = 5;
        }
    }

    public void hitBox() {
        float spriteX = sprite.getX();
        float spriteY = sprite.getY();
        float spriteWidth = sprite.getWidth() ;
        float spriteHeight = sprite.getHeight();

        if (PlayerAnim.selectedAnimation == 6 || PlayerAnim.selectedAnimation == 7) {
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
    @Override
    public int getDrawLayer() {
        return 10; // Player layer
    }

    @Override
    public float getY() {
        return position.y;
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        update(delta);
        TextureRegion currentFrame = PlayerAnim.animations.get(PlayerAnim.selectedAnimation).getKeyFrame(PlayerAnim.walkAnimationTime);
        sprite.setPosition(position.x, position.y);
        batch.draw(currentFrame, position.x + drawOffset.x, position.y);
        PlayerAnim.walkAnimationTime += delta;
        controls(delta, currentFrame);
    }
}
