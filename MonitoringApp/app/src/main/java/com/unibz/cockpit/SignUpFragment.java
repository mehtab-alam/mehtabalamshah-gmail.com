package com.unibz.cockpit;

import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.unibz.cockpit.model.User;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import hivatec.ir.easywebservice.Callback;
import hivatec.ir.easywebservice.EasyWebservice;
import hivatec.ir.easywebservice.Method;

public class SignUpFragment extends Fragment implements OnClickListener {
	private static View view;
	private static EditText firstName, lastName, emailId,
			password, confirmPassword;
	private static TextView login;
	private static Button signUpButton;
	private static CheckBox terms_conditions;
	private static Animation shakeAnimation;
	private static LinearLayout signupLayout;
	private static ProgressBar progressBar;
	public SignUpFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.signup_layout, container, false);
		initViews();
		setListeners();
		return view;
	}

	// Initialize all views
	private void initViews() {
		progressBar = getActivity().findViewById(R.id.progressBar);
		firstName = (EditText) view.findViewById(R.id.firstName);
		lastName = (EditText) view.findViewById(R.id.lastName);
		emailId = (EditText) view.findViewById(R.id.userEmailId);
		//mobileNumber = (EditText) view.findViewById(R.id.mobileNumber);
		//location = (EditText) view.findViewById(R.id.location);
		password = (EditText) view.findViewById(R.id.password);
		confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
		signUpButton = (Button) view.findViewById(R.id.signUpBtn);
		login = (TextView) view.findViewById(R.id.already_user);
		terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);
		shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
				R.anim.shake);
		signupLayout = (LinearLayout) view.findViewById(R.id.signup_layout);

		// Setting text selector over textviews
		XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
		try {
			ColorStateList csl = ColorStateList.createFromXml(getResources(),
					xrp);

			login.setTextColor(csl);
			terms_conditions.setTextColor(csl);
		} catch (Exception e) {
		}
	}

	// Set Listeners
	private void setListeners() {
		signUpButton.setOnClickListener(this);
		login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signUpBtn:

			// Call checkValidation method
			checkValidation();
			break;

		case R.id.already_user:

			// Replace login fragment
			getActivity().findViewById(R.id.close_activity).setVisibility(View.VISIBLE);
			getActivity().getSupportFragmentManager()
					.beginTransaction()
					.setCustomAnimations(R.anim.left_enter, R.anim.right_out)
					.replace(R.id.frameContainer, new LoginFragment(),
							Utils.Login_Fragment).commit();
			break;
		}

	}

	// Check Validation Method
	private void checkValidation() {

		// Get all edittext texts
		String getFirstName = firstName.getText().toString();
		String getLastName = lastName.getText().toString();
		String getEmailId = emailId.getText().toString();
		//String getMobileNumber = mobileNumber.getText().toString();
		//String getLocation = location.getText().toString();
		String getPassword = password.getText().toString();
		String getConfirmPassword = confirmPassword.getText().toString();

		// Pattern match for email id
		Pattern p = Pattern.compile(Utils.regEx);
		Matcher m = p.matcher(getEmailId);

		// Check if all strings are null or not
		if (getFirstName.equals("") || getFirstName.length() == 0
				|| getEmailId.equals("") || getEmailId.length() == 0
				|| getLastName.equals("") || getLastName.length() == 0
				|| getPassword.equals("") || getPassword.length() == 0
				|| getConfirmPassword.equals("")
				|| getConfirmPassword.length() == 0) {

            signupLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    getResources().getString(R.string.error_signup));
        }
		// Check if email id valid or not
		else if (!m.find()) {
            signupLayout.startAnimation(shakeAnimation);
		    new CustomToast().Show_Toast(getActivity(), view,
                    getResources().getString(R.string.error_email));
        }
		// Check if both password should be equal
		else if (!getConfirmPassword.equals(getPassword)) {
            signupLayout.startAnimation(shakeAnimation);
		    new CustomToast().Show_Toast(getActivity(), view,
                    getResources().getString(R.string.error_password_match));
        }
		// Make sure user should check Terms and Conditions checkbox
		else if (!terms_conditions.isChecked()) {
            signupLayout.startAnimation(shakeAnimation);
		    new CustomToast().Show_Toast(getActivity(), view,
                    getResources().getString(R.string.error_terms));
        }
		// Else do signup or do your stuff
		else {
//            signupLayout.startAnimation(shakeAnimation);
//		    new CustomToast().Show_Toast(getActivity(), view,
//                    getResources().getString(R.string.error_do_signup));
			User user = new User(getEmailId, getPassword, getFirstName, getLastName, "foreman");
			performSignup(user);
        }
	}

    void performSignup(User user){
		progressBar.setVisibility(View.VISIBLE);
        new EasyWebservice(Utils.BASE_URL+"register")
                .method(Method.POST)
				.addParam("email", user.getEmail())
				.addParam("password", user.getPassword())
				.addParam("firstName", user.getFirstName())
				.addParam("lastName", user.getLastName())
				//.addHeader("token", "your_token_hear")
                .call(new Callback.A<Boolean>("isCreated") { //should map response params

                    @Override
                    public void onSuccess(Boolean isCreated) {
						progressBar.setVisibility(View.GONE);
                        //you can work with res and msg which are in json response
						if(isCreated) {
							clearEditText();
							new SuccessToast().Show_Toast(getActivity(), view,
									getActivity().getString(R.string.signup_successful));
						}
						else{
							signupLayout.startAnimation(shakeAnimation);
							new CustomToast().Show_Toast(getActivity(), view,
									getActivity().getString(R.string.user_exist));
						}
                    }

                    @Override
                    public void onError(String error) {
						progressBar.setVisibility(View.GONE);
                        //if any error encountered
                        signupLayout.startAnimation(shakeAnimation);
						new CustomToast().Show_Toast(getActivity(), view,
								getActivity().getString(R.string.user_exist));
                    }
                });

    }
    void clearEditText(){
		firstName.setText("");
		lastName.setText("");
		emailId.setText("");
		password.setText("");
		confirmPassword.setText("");
		terms_conditions.setChecked(false);
		firstName.setFocusable(true);
	}
}
