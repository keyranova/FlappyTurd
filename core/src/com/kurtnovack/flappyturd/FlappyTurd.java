package com.kurtnovack.flappyturd;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

public class FlappyTurd extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;

    TextureRegion bird1;
    TextureRegion bird2;
    Animation wingsAnimation;
    TextureRegion currentFrame;

    Texture topTube;
    Texture bottomTube;
    float gap = 400;
    float maxTubeOffset;
    Random randomGenerator;
    float tubeVelocity = 4;
    int numberOfTubes = 4;
    float[] tubeX = new float[numberOfTubes];
    float[] tubeOffset = new float[numberOfTubes];
    float distanceBetweenTubes;

    float stateTime;
    float birdY = 0;
    float velocity = 0;

    int gameState = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");

        bird1 = new TextureRegion(new Texture("bird.png"));
        bird2 = new TextureRegion(new Texture("bird2.png"));
        wingsAnimation = new Animation(0.1f, bird1, bird2);
        stateTime = 0f;
        birdY = (Gdx.graphics.getHeight() / 2) - (bird1.getRegionHeight() / 2);

        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        maxTubeOffset = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;
        randomGenerator = new Random();
        distanceBetweenTubes = Gdx.graphics.getWidth() * 3 / 4;

        for(int i = 0; i < numberOfTubes; i++) {
            tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
            tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + i * distanceBetweenTubes;
        }
	}

	@Override
	public void render () {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = wingsAnimation.getKeyFrame(stateTime, true);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (gameState != 0) {

            if (Gdx.input.justTouched()) {
                velocity = -30;

            }

            for(int i = 0; i < numberOfTubes; i++) {
                if (tubeX[i] < - topTube.getWidth()) {
                    tubeX[i] += numberOfTubes * distanceBetweenTubes;
                    tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
                } else {
                    tubeX[i] -= tubeVelocity;
                }

                batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
                batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);
            }

            if (birdY > 0 || velocity < 0) {

                velocity++;
                birdY -= velocity;

            }

        } else {

            if (Gdx.input.justTouched()) {
                gameState = 1;
            }

        }

        batch.draw(currentFrame, (Gdx.graphics.getWidth() / 2) - (bird1.getRegionWidth() / 2), birdY);
        batch.end();
	}
}
