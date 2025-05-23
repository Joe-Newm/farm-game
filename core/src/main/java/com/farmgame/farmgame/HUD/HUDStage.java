package com.farmgame.farmgame.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.farmgame.farmgame.HUD.Inventory;
import com.farmgame.farmgame.screens.GameScreen;

public class HUDStage extends Stage {
    private Table root;
    public ItemSelector itemSelector;
    public Inventory playerInventory;

    public HUDStage() {
        super(new ScreenViewport());
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        skin.getAtlas().getTextures().first().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        root = new Table();
        root.setFillParent(true);
        this.addActor(root);

        itemSelector = new ItemSelector(skin);
        playerInventory = new Inventory(this);

        root.bottom().center().add(itemSelector).padBottom(-800);
        root.row();
        root.add(playerInventory);
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (amountY == 1) {
            ItemSelector.selectedSlot++;
            itemSelector.redraw();
        } else if (amountY == -1) {
            ItemSelector.selectedSlot--;
            itemSelector.redraw();
        }
        return false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        itemSelector.update();
        playerInventory.setVisible(GameScreen.isPaused);
    }

    @Override
    public void draw() {
        super.draw();
    }

}
