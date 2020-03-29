package com.unibz.cockpit;

import com.unibz.cockpit.model.User;
import com.unibz.cockpit.util.PrefUtil;

import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hivatec.ir.easywebservice.Callback;
import hivatec.ir.easywebservice.EasyWebservice;
import hivatec.ir.easywebservice.Method;

public class LoginFragment extends Fragment implements OnClickListener {
	private static View view;

	private static EditText emailid, password;
	private static Button loginButton;
	private static TextView forgotPassword, signUp;
	private static CheckBox show_hide_password;
	private static LinearLayout loginLayout;
	private static Animation shakeAnimation;
	private static FragmentManager fragmentManager;
	private static ProgressBar progressBar;
	public LoginFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.login_layout, container, false);
		initViews();
		setListeners();
		return view;
	}

	// Initiate Views
	private void initViews() {

		fragmentManager = getActivity().getSupportFragmentManager();

		progressBar = getActivity().findViewById(R.id.progressBar);
		emailid = (EditText) view.findViewById(R.id.login_emailid);
		password = (EditText) view.findViewById(R.id.login_password);
		loginButton = (Button) view.findViewById(R.id.loginBtn);
		forgotPassword = (TextView) view.findViewById(R.id.forgot_password);
		signUp = (TextView) view.findViewById(R.id.createAccount);
		show_hide_password = (CheckBox) view
				.findViewById(R.id.show_hide_password);
		loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);

		// Load ShakeAnimation
		shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
				R.anim.shake);

		// Setting text selector over textviews
		XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
		try {
			ColorStateList csl = ColorStateList.createFromXml(getResources(),
					xrp);

			forgotPassword.setTextColor(csl);
			show_hide_password.setTextColor(csl);
			signUp.setTextColor(csl);
		} catch (Exception e) {
		}
	}

	// Set Listeners
	private void setListeners() {
		loginButton.setOnClickListener(this);
		forgotPassword.setOnClickListener(this);
		signUp.setOnClickListener(this);

		// Set check listener over checkbox for showing and hiding password
		show_hide_password
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton button,
							boolean isChecked) {

						// If it is checkec then show password else hide
						// password
						if (isChecked) {

							show_hide_password.setText(R.string.hide_pwd);// change
																			// checkbox
																			// text

							password.setInputType(InputType.TYPE_CLASS_TEXT);
							password.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());// show password
						} else {
							show_hide_password.setText(R.string.show_pwd);// change
																			// checkbox
																			// text

							password.setInputType(InputType.TYPE_CLASS_TEXT
									| InputType.TYPE_TEXT_VARIATION_PASSWORD);
							password.setTransformationMethod(PasswordTransformationMethod
									.getInstance());// hide password

						}

					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loginBtn:
			checkValidation();
			break;

		case R.id.forgot_password:

			// Replace forgot password fragment with animation
			fragmentManager
					.beginTransaction()
					.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
					.replace(R.id.frameContainer,
							new ForgotPasswordFragment(),
							Utils.ForgotPassword_Fragment).commit();
			break;
		case R.id.createAccount:

			// Replace signup frgament with animation
			fragmentManager
					.beginTransaction()
					.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
					.replace(R.id.frameContainer, new SignUpFragment(),
							Utils.SignUp_Fragment).commit();
			break;
		}

	}

	// Check Validation before login
	private void checkValidation() {
		// Get email id and password
		String getEmailId = emailid.getText().toString();
		String getPassword = password.getText().toString();

		// Check patter for email id
		Pattern p = Pattern.compile(Utils.regEx);

		Matcher m = p.matcher(getEmailId);

		// Check for both field is empty or not
		if (getEmailId.equals("") || getEmailId.length() == 0
				|| getPassword.equals("") || getPassword.length() == 0) {
			loginLayout.startAnimation(shakeAnimation);
			new CustomToast().Show_Toast(getActivity(), view,
					getResources().getString(R.string.error_login));

		}
		// Check if email id is valid or not
		else if (!m.find()) {
			loginLayout.startAnimation(shakeAnimation);
			new CustomToast().Show_Toast(getActivity(), view,
					getResources().getString(R.string.error_email));
		}
		// Else do login and do your stuff
		else {
//			loginLayout.startAnimation(shakeAnimation);
//			new CustomToast().Show_Toast(getActivity(), view,
//					getResources().getString(R.string.error_do_login));


			performLogin(getEmailId, getPassword);
		}
	}

	void performLogin(String email, String password){
		progressBar.setVisibility(View.VISIBLE);
		new EasyWebservice(Utils.BASE_URL+"login")
				.method(Method.POST) //default
				.addParam("email", email)
				.addParam("password", password)//adding params to body
				.call(new Callback.A<User>("user") { //should map response params

					@Override
					public void onSuccess(User user) {
						progressBar.setVisibility(View.GONE);


						if(user.getEmail() != null) {
							clearEditText();
							new SuccessToast().Show_Toast(getActivity(), view,
									getActivity().getString(R.string.login_success));
							PrefUtil.savePreferenceObject(getContext(), PrefUtil.PREF_USER, user);
							getActivity().findViewById(R.id.close_activity).setVisibility(View.GONE);
							fragmentManager
									.beginTransaction()
									.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
									.replace(R.id.frameContainer, new ProjectsFragment(),
											Utils.Project_Fragment).commit();
						}
						else{
							progressBar.setVisibility(View.GONE);
							loginLayout.startAnimation(shakeAnimation);
							new CustomToast().Show_Toast(getActivity(), view,
									getActivity().getString(R.string.credential_mismatch));
						}
					}

					@Override
					public void onError(String error) {
						progressBar.setVisibility(View.GONE);
						loginLayout.startAnimation(shakeAnimation);
						new CustomToast().Show_Toast(getActivity(), view,
								getActivity().getString(R.string.credential_mismatch));
					}
				});

	}

	void clearEditText(){

		emailid.setText("");
		password.setText("");
		emailid.setFocusable(true);
		show_hide_password.setChecked(false);
	}
}
