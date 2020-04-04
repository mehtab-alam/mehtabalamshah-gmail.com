package it.unibz.coviddashbord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.stephentuso.welcome.WelcomeHelper;

import it.unibz.coviddashbord.adapter.TabAdapter;
import it.unibz.coviddashbord.util.PrefUtil;
import it.unibz.coviddashbord.widget.CustomViewPager;


public class MainActivity extends AppCompatActivity {

    private WelcomeHelper welcomeScreen;
    TabLayout tabLayout;
    CustomViewPager viewPager;
    InterstitialAd mInterstitialAd;
    public static String ADMOB_ID = "ca-app-pub-4757801931755562~8176148271";
    public static String ADUNIT_ID = "ca-app-pub-4757801931755562/6520000416";
    //public static String ADUNIT_ID = "ca-app-pub-3940256099942544/1033173712";
    public FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this); // Initialize Firebase Analytics

        showDisclaimer();
        showWelcomeScreens(savedInstanceState);
        initViews();



    }


    void showWelcomeScreens(Bundle savedInstanceState){
        welcomeScreen = new WelcomeHelper(this, SplashActivity.class);
        welcomeScreen.show(savedInstanceState);
    }

    void showDisclaimer(){
        if(!PrefUtil.getBooleanPreference(this, PrefUtil.IS_DISCLAIMER_SHOWN)) {

            showDisclaimerDialog();
            return ;
        }
    }
    void initViews(){
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        viewPager.setEnableSwipe(false); //disable scrolling
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.dashboard)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.covid_meter)));
        //tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.map)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final TabAdapter adapter = new TabAdapter(this.getSupportFragmentManager(), this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        initInterstitialAd();
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    void initInterstitialAd(){
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(ADUNIT_ID);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdClosed() {
                super.onAdClosed();

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                Log.d("TAG Failedd:"+ errorCode,"The interstitial wasn't loaded yet. Do some other action");

            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

                if (mInterstitialAd.isLoaded()) {
                    Log.d("TAG","The interstitial is loaded successfully");
                    mInterstitialAd.show();
                }
            }

        });
    }

    void showDisclaimerDialog(){
        final FlatDialog flatDialog = new FlatDialog(MainActivity.this);
        flatDialog.setCancelable(false);



        flatDialog.setTitle(getString(R.string.disclaimer_title))
                 .setFirstButtonText("OK")
                .withFirstButtonListner(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PrefUtil.savePreference(MainActivity.this, PrefUtil.IS_DISCLAIMER_SHOWN,
                                true);
                        flatDialog.dismiss();


                    }
                })
                .show();
        TextView description = flatDialog.findViewById(R.id.subtitle);

        description.setText( Html.fromHtml(getString(R.string.disclaimer_text)));
        description.setVisibility(View.VISIBLE);
        description.setMovementMethod(LinkMovementMethod.getInstance());
        description.setLinksClickable(true);
        description.setClickable(true);
    }

}
