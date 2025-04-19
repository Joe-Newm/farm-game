package com.farmgame.farmgame.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUDStage extends Stage {
    private Table root;
    private ItemSelector itemSelector;

    public HUDStage() {
        super(new ScreenViewport());
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        skin.getAtlas().getTextures().first().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        root = new Table();
        root.setFillParent(true);
        this.addActor(root);

        itemSelector = new ItemSelector(skin);
        root.bottom().center().add(itemSelector).padBottom(-600);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        itemSelector.update();
    }
}
