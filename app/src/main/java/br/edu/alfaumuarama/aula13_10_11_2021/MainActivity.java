package br.edu.alfaumuarama.aula13_10_11_2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class MainActivity extends AppCompatActivity {

    AdView mAdview;
    InterstitialAd mInterstitialAd;
    RewardedAd mRewardedAd;

    Button btnAdTelaInteira, btnAdRecompensa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdview = findViewById(R.id.adView);
        btnAdTelaInteira = findViewById(R.id.btnAdTelaInteira);
        btnAdRecompensa = findViewById(R.id.btnAdRecompensa);

        //inicializando a central de publicidade do AdMob
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) { }
        });

        //criando uma requisicao para o server do AdMob
        AdRequest request = new AdRequest.Builder().build();

        //exibindo o retorno do request no banner da tela
        mAdview.loadAd(request);

        /*Inicio - Codigo para a versao antiga do AdMOb (19)
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(request);
        //Fim - Codigo para a versao antiga do AdMOb (19)*/

        //Inicio - Codigo para nova versao do AdMOb (20)
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", request,
            new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    mInterstitialAd = interstitialAd;
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    mInterstitialAd = null;
                }
            }
        );
        //Fim - Codigo para nova versao do AdMOb (20)

        /*Inicio - Codigo para a versao antiga do AdMob (19)
        mRewardedAd = new RewardedAd(this, "ca-app-pub-3940256099942544/5224354917");
        mRewardedAd.loadAd(request, new RewardedAdLoadCallback());
        //Fim - Codigo para a versao antiga do AdMob (19)*/

        //Inicio - Codigo para nova versao do AdMOb (20)
        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
            request, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
              mRewardedAd = null;
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                mRewardedAd = rewardedAd;
            }
        });
        //Fim - Codigo para nova versao do AdMOb (20)

        btnAdTelaInteira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MainActivity.this);
                }
            }
        });

        btnAdRecompensa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Inicio - Codigo para versao antiga do AdMOb (19)
                if (mRewardedAd != null)
                    mRewardedAd.show(MainActivity.this,
                        new RewardedAdCallback() {
                            @Override
                            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                int rewardAmount = rewardItem.getAmount();
                                String rewardType = rewardItem.getType();
                            }
                        }
                    );
                //Fim - Codigo para a versao antiga do AdMob (19)*/

                //Inicio - Codigo para nova versao do AdMOb (20)
                if (mRewardedAd != null) {
                    mRewardedAd.show(MainActivity.this, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            // Handle the reward.
                            int rewardAmount = rewardItem.getAmount();
                            String rewardType = rewardItem.getType();
                        }
                    });
                }
                //Fim - Codigo para nova versao do AdMOb (20)
            }
        });
    }
}