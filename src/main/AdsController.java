package itgap.main;

public interface AdsController {

    void showBannerAd();
    void hideBannerAd();
    void showInterstitialAd (Runnable then);
    int isWifiConnected();
}
