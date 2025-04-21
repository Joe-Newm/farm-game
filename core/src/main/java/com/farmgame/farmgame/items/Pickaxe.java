package com.farmgame.farmgame.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.farmgame.farmgame.HUD.ItemSelector;
import com.farmgame.farmgame.entity.Player;
import com.farmgame.farmgame.entity.PlayerAnim;

public class Pickaxe extends Item {
    public Sprite sprite;

    public Pickaxe(Vector2 position, int quantity) {
        super(position, quantity);
        this.name = "pickaxe";
        this.icon = new Texture(Gdx.files.internal("items/pickaxe-item.png"));
        sprite = new Sprite(icon);
        this.quantity = quantity;
        this.position = position;
        offsetFlag = false;
    }

    @Override
    public void update(float delta) {
        boundingBox = new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void action() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            // swing pickaxe
            if (Player.inventory[ItemSelector.selectedSlot] != null) {
                if (Player.inventory[ItemSelector.selectedSlot].name == "pickaxe") {
                    PlayerAnim.selectedAnimation = 6;
                    PlayerAnim.swingPickaxeAnimation.setPlayMode(Animation.PlayMode.LOOP);
                    if (Player.isFlipped && PlayerAnim.selectedAnimation == 6) {
                        Player.drawOffset.x = -16;
                        offsetFlag = true;
                    }
                }
            }
        }

        if (PlayerAnim.selectedAnimation != 6 && offsetFlag) {
            Player.drawOffset.x = 0;
            offsetFlag = false;
        }

    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        update(delta);
        sprite.draw(batch);
        sprite.setPosition(position.x, position.y);
    }


}
