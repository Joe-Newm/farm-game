package com.farmgame.farmgame.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.farmgame.farmgame.FarmGame;
import com.farmgame.farmgame.HUD.ItemSelector;
import com.farmgame.farmgame.entity.Player;

import java.util.ArrayList;


import static com.farmgame.farmgame.HUD.ItemSelector.selectedSlot;

public class Inventory extends Table {
    private Viewport viewport;
    private DragAndDrop dragAndDrop;
    public Stage stage;


    public Inventory(Stage stage) {
        dragAndDrop = new DragAndDrop();
        dragAndDrop.setTapSquareSize(0);
        this.stage = stage;



        //inventoryTable.setFillParent(true);
        this.setTouchable(Touchable.enabled);
        this.center();

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        this.setBackground(skin.newDrawable("white", Color.BLACK) );
        skin.getAtlas().getTextures().first().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        //Label pauseLabel = new Label("GAME PAUSE", skin);
        this.center();
        this.setPosition(
            (stage.getWidth() - this.getPrefWidth()) / 2f,
            (stage.getHeight() - this.getPrefHeight()) / 2f
        );

        drawInventory();
    }


    public void drawInventory() {
        this.clear();
        dragAndDrop.clear();

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        skin.getAtlas().getTextures().first().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);




        // Add item slots
        for (int i = 0; i < 18; i++) {
            final int index = i;

            Color slotColor = Color.DARK_GRAY;
            Drawable box = skin.newDrawable("white", slotColor);

            Container<Table> slot = new Container<>();
            slot.setBackground(box);
            if (i == 9) {
                this.row();
            }
            // add icon from inventory
            Table content = new Table();
            Image itemImage;

            if (Player.inventory[index] != null) {
                TextureRegionDrawable itemDrawable = new TextureRegionDrawable(new TextureRegion(Player.inventory[index].icon));
                itemImage = new Image(itemDrawable);
                //payload.setDragActor(new Image(itemDrawable));
                content.add(itemImage).size(48, 48);
            } else {
                itemImage = new Image();
                content.add(itemImage).size(48, 48);
            }
            slot.setActor(content);
            slot.setTouchable(Touchable.enabled);
            this.add(slot).size(64, 64).pad(4);

            dragAndDrop.addSource(new DragAndDrop.Source(itemImage) {
                @Override
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    if (Player.inventory[index] == null) {return null;}

                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    payload.setObject(index); // store source index
                    payload.setDragActor(new Image(itemImage.getDrawable())); // ghost image
                    return payload;
                }
            });

            // === Drag Target ===
            dragAndDrop.addTarget(new DragAndDrop.Target(slot) {
                @Override
                public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                    System.out.println("Dragging over slot: " + index);

                    return true;
                }

                @Override
                public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                    int sourceIndex = (int) payload.getObject();
                    int targetIndex = index;

                    // Swap items
                    var temp = Player.inventory[sourceIndex];
                    Player.inventory[sourceIndex] = Player.inventory[targetIndex];
                    Player.inventory[targetIndex] = temp;
                    System.out.println("DROP TRIGGERED: " + sourceIndex + " → " + targetIndex);

                    // draw inventory
                    Gdx.app.postRunnable(() -> drawInventory());
                }
            });
        }
        //Gdx.input.setInputProcessor(stage);
    }


    public void render(float delta) {
        //ScreenUtils.clear(0, 0, 0.1f, 1); // Dark background


    }

    public void controls() {
    }

}
