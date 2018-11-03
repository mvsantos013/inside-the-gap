package itgap.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import itgap.assets.Storage;
import itgap.main.ItGap;

public class SplashScreen implements Screen {

    private final ItGap screen;

    private Stage stage;
    private Image logoImage;

    private Runnable changeScreen;

    public SplashScreen(final ItGap screen){
        this.screen = screen;

        this.stage = new Stage(ItGap.viewport);

        logoImage = new Image(Storage.splashLogoTex);
        logoImage.setOrigin(0.5f*logoImage.getWidth(), 0.5f*logoImage.getHeight());
        logoImage.scaleBy(0.1f);
        logoImage.setPosition(0.5f*screen.camera.viewportWidth - 0.5f*logoImage.getWidth(), 0.5f*screen.camera.viewportHeight - 0.5f*logoImage.getHeight());

        changeScreen = new Runnable() {
            @Override
            public void run() {
                screen.setScreen(screen.menuScreen);
                dispose();
            }
        };
    }

    @Override
    public void show() {
        ItGap.backGround.setPosition(0,0);
        logoImage.addAction(Actions.sequence(Actions.alpha(0f), Actions.fadeIn(2f), Actions.delay(2f), Actions.fadeOut(1f), Actions.run(changeScreen)));

        stage.addActor(logoImage);

        ItGap.soundLogo.play(0.5f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        screen.camera.update();
        screen.batch.setProjectionMatrix(screen.camera.combined);

        screen.batch.begin();
        ItGap.backGround.draw(screen.batch);
        screen.batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        ItGap.viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
