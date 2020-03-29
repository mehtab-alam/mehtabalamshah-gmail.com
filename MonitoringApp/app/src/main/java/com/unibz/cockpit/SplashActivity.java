package com.unibz.cockpit;

import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;

public class SplashActivity extends WelcomeActivity {

    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .defaultTitleTypefacePath("Montserrat-Bold.ttf")
                .defaultHeaderTypefacePath("Montserrat-Bold.ttf")

                .page(new BasicPage(R.drawable.logo_1,
                        getString(R.string.app_name),
                        getString(R.string.first_screen))

                        .headerColor(getResources().getColor(R.color.background_color))
                        .descriptionColor(getResources().getColor(R.color.colorRed_600))
                        .background(R.color.colorBlue_300)
                )

                .page(new BasicPage(R.drawable.logo_2,
                        getString(R.string.projects_title),
                        getString(R.string.second_screen))
                        .headerColor(getResources().getColor(R.color.background_color))
                        .descriptionColor(getResources().getColor(R.color.colorRed_600))
                        .background(R.color.colorBlue_300)
                )



                .page(new BasicPage(R.drawable.logo_3,
                        getString(R.string.dashboard_title),
                        getString(R.string.third_screen))
                        .headerColor(getResources().getColor(R.color.background_color))
                        .descriptionColor(getResources().getColor(R.color.colorRed_600))
                        .background(R.color.colorBlue_300)
                )

                .swipeToDismiss(true)
                .exitAnimation(android.R.anim.fade_out)
                .build();
    }

    public static String welcomeKey() {
        return "WelcomeScreen";
    }

}