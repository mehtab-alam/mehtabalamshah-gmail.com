package it.unibz.coviddashbord;

import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;

public class SplashActivity extends WelcomeActivity {

    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .defaultTitleTypefacePath("fonts/helvetica.ttf")
                .defaultHeaderTypefacePath("fonts/helvetica.ttf")
                .page(new BasicPage(R.drawable.logo_1,
                        getString(R.string.screen_1),
                        getString(R.string.description_1))
                        .headerColor(getResources().getColor(R.color.dark_blue))
                        .descriptionColor(getResources().getColor(R.color.colorPrimaryDark))
                        .background(R.color.white)
                )
                .page(new BasicPage(R.drawable.logo_2,
                        getString(R.string.screen_2),
                        getString(R.string.description_2))
                        .headerColor(getResources().getColor(R.color.dark_blue))
                        .descriptionColor(getResources().getColor(R.color.colorPrimaryDark))
                        .background(R.color.white)
                )
                .page(new BasicPage(R.drawable.logo_3,
                        getString(R.string.screen_3),
                        getString(R.string.description_3))
                        .headerColor(getResources().getColor(R.color.dark_blue))
                        .descriptionColor(getResources().getColor(R.color.colorPrimaryDark))
                        .background(R.color.white)
                )
                .page(new BasicPage(R.drawable.logo_4,
                        getString(R.string.screen_4),
                        getString(R.string.description_4))
                        .headerColor(getResources().getColor(R.color.dark_blue))
                        .descriptionColor(getResources().getColor(R.color.colorPrimaryDark))
                        .background(R.color.white)
                )
                .page(new BasicPage(R.drawable.logo_5,
                        getString(R.string.screen_5),
                        getString(R.string.description_5))
                        .headerColor(getResources().getColor(R.color.dark_blue))
                        .descriptionColor(getResources().getColor(R.color.colorPrimaryDark))
                        .background(R.color.white)
                )
                .swipeToDismiss(true)
                .exitAnimation(android.R.anim.fade_out)
                .build();
    }

    public static String welcomeKey() {
        return "WelcomeScreen";
    }

}