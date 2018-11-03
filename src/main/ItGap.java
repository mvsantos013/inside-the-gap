package itgap.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import itgap.assets.Storage;
import itgap.screens.GameOverScreen;
import itgap.screens.GamesScreen;
import itgap.screens.MenuScreen;
import itgap.screens.PlayScreen;
import itgap.screens.SplashScreen;

public class ItGap extends Game {
	public static final String Title = "Inside the Gap", VERSION = "version 0.3";
	public static final int screenWidth = 480;
	public static int screenHeight = 850;
	public PlayServices playServices;
	public AdsController adsController;

	public SpriteBatch batch;
	public OrthographicCamera camera;
	public static Viewport viewport;

    public SplashScreen splashScreen;
	public MenuScreen menuScreen;
	public PlayScreen playScreen;
	public GameOverScreen gameOverScreen;
	public GamesScreen gamesScreen;

	public static Preferences settings;
	public static int high_score;

	public static Sound soundLogo, soundButtonClick, scoreSound, newRecordSound, loseSound;
	public static BitmapFont textFont, font, font2;
	public static GlyphLayout glyphlayout;
	public static Sprite backGround;
	public static boolean music_on;
	public static int countPressMusic;

	public ItGap(){

	}

	public ItGap(PlayServices playServices, AdsController adsController){
		this.playServices = playServices;
		this.adsController = adsController;
	}

	@Override
	public void create() {
		Storage.loadStorage();
		backGround = new Sprite(Storage.backGroundTex);
		backGround.setSize(screenWidth, screenHeight);

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, screenWidth, screenHeight);
		viewport = new FitViewport(screenWidth, screenHeight, camera);
		viewport.apply();

		settings = Gdx.app.getPreferences("settings");

		soundLogo = Gdx.audio.newSound(Gdx.files.internal("LogoSound.mp3"));
		soundButtonClick = Gdx.audio.newSound(Gdx.files.internal("buttonClick.mp3"));
		scoreSound = Gdx.audio.newSound(Gdx.files.internal("soundBip.mp3"));
		newRecordSound = Gdx.audio.newSound(Gdx.files.internal("newRecordSound.mp3"));
		loseSound = Gdx.audio.newSound(Gdx.files.internal("loseSound.mp3"));
		music_on = true;
		countPressMusic = 0;

		glyphlayout = new GlyphLayout();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("score_font.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
		params.size = 60;
		params.color.set(0.8f, 0.1f, 0.1f, 1f);
		font = generator.generateFont(params);
		font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		FreeTypeFontGenerator.FreeTypeFontParameter params1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		params1.size = 48;
		params1.color.set(0.2f, 0.2f, 0.2f, 1f);
		font2 = generator.generateFont(params1);
		font2.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		generator.dispose();

		FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("score_font.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter params2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		params2.size = 36;
		params2.color.set(0.2f, 0.2f, 0.2f, 1f);
		textFont = generator2.generateFont(params2);
		textFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		generator2.dispose();

        splashScreen = new SplashScreen(this);
		menuScreen = new MenuScreen(this);
		playScreen = new PlayScreen(this);
		gameOverScreen = new GameOverScreen(this);
		gamesScreen = new GamesScreen(this);

		setScreen(splashScreen);
	}

	@Override
	public void dispose() {
		batch.dispose();
		menuScreen.dispose();
		playScreen.dispose();
		gameOverScreen.dispose();
		soundLogo.dispose();
		soundButtonClick.dispose();
		scoreSound.dispose();
		newRecordSound.dispose();
		loseSound.dispose();
		font.dispose();
		font2.dispose();
		textFont.dispose();
		Storage.disposeStorage();
	}
}