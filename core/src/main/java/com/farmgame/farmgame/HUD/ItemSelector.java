package com.farmgame.farmgame.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.farmgame.farmgame.entity.Player;

public class ItemSelector extends Table {
    public static int selectedSlot;
    private Skin skin;

    public ItemSelector(Skin skin) {
        this.skin = skin;
        this.setTouchable(Touchable.enabled);
        selectedSlot = 0;

        this.setBackground(skin.newDrawable("white", Color.BLACK));

        skin.getAtlas().getTextures().first().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);


        redraw();


    }

    public void update() {
//        if (Gdx.input.isKeyJustPressed(Input.Keys.DPAD_RIGHT)) {
//            selectedSlot++;
//            System.out.println("Pressed button, index is now: " + selectedSlot);
//            redraw();
//        }
//        if (Gdx.input.isKeyJustPressed(Input.Keys.DPAD_LEFT)) {
//            selectedSlot--;
//            System.out.println("Pressed button, index is now: " + selectedSlot);
//            redraw();
//        }
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

            Container<Actor> slot = new Container<>();
            slot.size(64, 64);
            slot.setBackground(box);

            // add icon from inventory
            Stack content = new Stack();
            //content.setFillParent(true);
            if (Player.inventory[index] != null) {
                TextureRegionDrawable itemDrawable = new TextureRegionDrawable(new TextureRegion(Player.inventory[index].icon));

                // Item image
                Image itemImage = new Image(itemDrawable);
                itemImage.setScaling(Scaling.fit);
                itemImage.setAlign(Align.center);

                // Quantity label
                Label.LabelStyle labelStyle = new Label.LabelStyle();
                labelStyle.font = skin.getFont("default-font");
                labelStyle.fontColor = Color.WHITE;

                Label quantityLabel = new Label(String.valueOf(Player.inventory[index].quantity), labelStyle);

                // Wrap quantity label in a table to position it bottom-right
                Table quantityTable = new Table();
                quantityTable.add(quantityLabel).bottom().right().expand().pad(4);

                // Add image and label overlay
                content.add(itemImage);
                content.add(quantityTable);
            }
            slot.setActor(content);

            slot.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedSlot = index;
                    redraw();
                }
            });

            slot.setTouchable(Touchable.enabled);
            this.add(slot).size(64, 64).pad(4);
        }
    }


}
