package com.farmgame.farmgame.screens;

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
import com.farmgame.farmgame.FarmGame;
import com.farmgame.farmgame.entity.Player;

import static com.farmgame.farmgame.HUD.ItemSelector.selectedSlot;

public class PauseScreen implements Screen {
    private final FarmGame game;
    private final Screen returnTo;
    private Skin skin;

    private Stage stage;

    public PauseScreen(FarmGame game, Screen returnTo) {
        this.game = game;
        this.returnTo = returnTo;
        DragAndDrop dragAndDrop = new DragAndDrop();


        stage = new Stage();
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        skin.getAtlas().getTextures().first().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.setTouchable(Touchable.enabled);

        Label pauseLabel = new Label("GAME PAUSE", skin);


        table.center();
        //table.add(pauseLabel).colspan(9).center().padBottom(600);


        // Add item slots
        for (int i = 0; i < 9; i++) {
            final int index = i;

            Color slotColor = Color.DARK_GRAY;
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



            slot.setTouchable(Touchable.enabled);
            table.add(slot).size(64, 64).pad(4);
        }

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.1f, 1); // Dark background
        stage.act(delta);
        stage.draw();

        controls();
    }

    public void controls() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(returnTo);
        }
    }

    // other Screen methods can be left empty or basic
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void show() {}
    @Override public void dispose() {
        stage.dispose();
    }
}
