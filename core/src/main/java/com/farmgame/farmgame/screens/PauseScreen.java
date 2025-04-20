package com.farmgame.farmgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.farmgame.farmgame.FarmGame;

public class PauseScreen implements Screen {
    private final FarmGame game;
    private final Screen returnTo;

    private Stage stage;

    public PauseScreen(FarmGame game, Screen returnTo) {
        this.game = game;
        this.returnTo = returnTo;

        stage = new Stage();
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label pauseLabel = new Label("GAME PAUSE", skin);


        table.center();
        table.add(pauseLabel).padBottom(20);
        table.row();

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
