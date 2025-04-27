package com.farmgame.farmgame;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.farmgame.farmgame.screens.GameScreen;

public class FarmGame extends Game {
    public SpriteBatch batch;

    @Override
    public void create() {
        Gdx.graphics.setForegroundFPS(60);
        batch = new SpriteBatch();
        setScreen(new GameScreen(this));  // Start the actual game
    }

    @Override
    public void dispose() {
        batch.dispose();
        super.dispose();
    }
}

