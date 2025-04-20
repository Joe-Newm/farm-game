package com.farmgame.farmgame.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.farmgame.farmgame.entity.Player;

public class ItemSelector extends Table {
    public static int selectedSlot = 0;
    private Skin skin;

    public ItemSelector(Skin skin) {
        this.skin = skin;
        this.setTouchable(Touchable.enabled);

        // Add dummy item slots
        for (int i = 0; i < 9; i++) {
            final int index = i;

            Color slotColor = (i == selectedSlot) ? Color.LIGHT_GRAY : Color.DARK_GRAY;
            Drawable box = skin.newDrawable("white", slotColor);

            Container<Table> slot = new Container<>();
            slot.setBackground(box);

            // add icon from inventory
            Table content = new Table();
            if (Player.inventory[index] != null) {
                TextureRegionDrawable itemDrawable = new TextureRegionDrawable(new TextureRegion(Player.inventory[index].icon));
                Image itemImage = new Image(itemDrawable);
                content.add(itemImage).size(48, 48);
            }
            slot.setActor(content);

            slot.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedSlot = index;
                    System.out.println("Clicked slot: " + selectedSlot);
                    redraw();
                }
            });
            slot.setTouchable(Touchable.enabled);
            this.add(slot).size(64, 64).pad(4);
        }
    }

    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.DPAD_RIGHT)) {
            selectedSlot++;
            System.out.println("Pressed button, index is now: " + selectedSlot);
            redraw();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DPAD_LEFT)) {
            selectedSlot--;
            System.out.println("Pressed button, index is now: " + selectedSlot);
            redraw();
        }
    }

    public void redraw() {
        this.clear();
        if (selectedSlot > 8) {
            selectedSlot = 0;
        }
        if (selectedSlot < 0) {
            selectedSlot = 8;
        }
        for (int i = 0; i < 9; i++) {
            final int index = i;

            Color slotColor = (i == selectedSlot) ? Color.LIGHT_GRAY : Color.DARK_GRAY;
            Drawable box = skin.newDrawable("white", slotColor);

            Container<Table> slot = new Container<>();
            slot.setBackground(box);

            // add icon from inventory
            Table content = new Table();
            if (Player.inventory[index] != null) {
                TextureRegionDrawable itemDrawable = new TextureRegionDrawable(new TextureRegion(Player.inventory[index].icon));
                Image itemImage = new Image(itemDrawable);
                content.add(itemImage).size(48, 48);
            }
            slot.setActor(content);

            slot.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedSlot = index;
                    System.out.println("Clicked slot: " + selectedSlot);
                    redraw();
                }
            });

            slot.setTouchable(Touchable.enabled);
            this.add(slot).size(64, 64).pad(4);
        }
    }
}
