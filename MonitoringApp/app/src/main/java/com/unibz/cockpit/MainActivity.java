package com.unibz.cockpit;



import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
import com.stephentuso.welcome.WelcomeHelper;
import com.unibz.cockpit.util.PrefUtil;



public class MainActivity extends AppCompatActivity {
	private static FragmentManager fragmentManager;

	private WelcomeHelper welcomeScreen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		setContentView(R.layout.activity_main);
		fragmentManager = getSupportFragmentManager();

		welcomeScreen = new WelcomeHelper(this, SplashActivity.class);
		welcomeScreen.show(savedInstanceState);

		// If savedinstnacestate is null then replace login fragment
		if (savedInstanceState == null) {
			if(PrefUtil.getUserPreference(this, PrefUtil.PREF_USER) != null){
				replaceProjectFragment();
			}
			else {
				replaceLoginFragment();
			}
		}

		// On close icon click finish activity
		findViewById(R.id.close_activity).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						finish();

					}
				});

	}

	// Replace Login Fragment with animation
	protected void replaceLoginFragment() {
		findViewById(R.id.close_activity).setVisibility(View.VISIBLE);
		fragmentManager
				.beginTransaction()
				.setCustomAnimations(R.anim.left_enter, R.anim.right_out)
				.replace(R.id.frameContainer, new LoginFragment(),
						Utils.Login_Fragment).commit();
	}

	protected void replaceProjectFragment() {
		findViewById(R.id.close_activity).setVisibility(View.GONE);
		fragmentManager
				.beginTransaction()
				.setCustomAnimations(R.anim.left_enter, R.anim.right_out)
				.replace(R.id.frameContainer, new ProjectsFragment(),
						Utils.Project_Fragment).commit();
	}
	@Override
	public void onBackPressed() {

		// Find the tag of signup and forgot password fragment
		Fragment SignUp_Fragment = fragmentManager
				.findFragmentByTag(Utils.SignUp_Fragment);
		Fragment ForgotPassword_Fragment = fragmentManager
				.findFragmentByTag(Utils.ForgotPassword_Fragment);
		Fragment project_Fragment = fragmentManager
				.findFragmentByTag(Utils.Project_Fragment);
		Fragment dashboardFragment = fragmentManager
				.findFragmentByTag(Utils.Dashboard_Fragment);
		// Check if both are null or not
		// If both are not null then replace login fragment else do backpressed
		// task
		if(dashboardFragment != null){
			replaceProjectFragment();
		}
		if(project_Fragment != null){
			closeApplication();
		}
		else if (SignUp_Fragment != null)
			replaceLoginFragment();
		else if (ForgotPassword_Fragment != null)
			replaceLoginFragment();

			//super.onBackPressed();
	}



	void closeApplication(){
		new FancyAlertDialog.Builder(this)
				.setTitle(getString(R.string.app_name))
				.setBackgroundColor(Color.parseColor("#303F9F"))  //Don't pass R.color.colorvalue
				.setMessage(getString(R.string.close_app))
				.setNegativeBtnText(getString(R.string.cancel))
				.setPositiveBtnBackground(Color.parseColor("#FF4000"))  //Don't pass R.color.colorvalue
				.setPositiveBtnText(getString(R.string.ok))
				.setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
				.setAnimation(Animation.SLIDE)
				.isCancellable(true)
				.setIcon(R.drawable.ic_star_border_black_24dp, Icon.Visible)
				.OnPositiveClicked(new FancyAlertDialogListener() {
					@Override
					public void OnClick() {
						MainActivity.this.finish();

					}
				})
				.OnNegativeClicked(new FancyAlertDialogListener() {
					@Override
					public void OnClick() {

					}
				})
				.build();



	}
}
