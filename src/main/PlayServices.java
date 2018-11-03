package itgap.main;


public interface PlayServices {
    void signIn();
    void signOut();
    void rateGame();
    void visitUntouchable();
    void unlockAchievement();
    void submitScore(int highScore);
    void showAchievement();
    void showScore();
    boolean isSignedIn();
}
