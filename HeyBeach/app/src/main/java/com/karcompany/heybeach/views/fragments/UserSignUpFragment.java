package com.karcompany.heybeach.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.karcompany.heybeach.R;
import com.karcompany.heybeach.logging.DefaultLogger;
import com.karcompany.heybeach.models.RegisterApiResponse;
import com.karcompany.heybeach.presenters.MainLoginPresenter;
import com.karcompany.heybeach.presenters.MainLoginPresenterImpl;
import com.karcompany.heybeach.presenters.SignUpPresenter;
import com.karcompany.heybeach.presenters.SignUpPresenterImpl;
import com.karcompany.heybeach.service.ApiResponse;
import com.karcompany.heybeach.service.ApiResultReceiver;
import com.karcompany.heybeach.service.ServiceHelper;
import com.karcompany.heybeach.utils.CommonUtils;
import com.karcompany.heybeach.views.SignUpView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by pvkarthik on 2017-01-18.
 *
 * User Signup fragment which displays signup option (Account).
 */
public class UserSignUpFragment extends BaseFragment implements SignUpView, ApiResultReceiver.Receiver {

	private static final String TAG = DefaultLogger.makeLogTag(UserSignUpFragment.class);

	public static UserSignUpFragment newInstance() {
		return new UserSignUpFragment();
	}

	private SignUpPresenter mSignUpPresenter;

	private EditText mUserEmailView;
	private EditText mUserPwdView;

	private TextInputLayout mUserEmailLyt;
	private TextInputLayout mUserPwdLyt;

	private Button mSignUpBtn;

	private TextWatcher mTextWatcher = new TextWatcher() {
		@Override
		public void afterTextChanged(Editable s) {
			validate();
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}
	};

	private ApiResultReceiver mResultReceiver;

	private MainLoginPresenter mMainLoginPresenter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_user_signup, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setUpUI();
	}

	@Override
	protected void setUpPresenter() {
		mMainLoginPresenter = MainLoginPresenterImpl.getInstance();
		mSignUpPresenter = SignUpPresenterImpl.getInstance();
		mSignUpPresenter.setView(this);
	}

	// Setup the callback for when data is received from the service
	public void setupServiceReceiver() {
		mResultReceiver = new ApiResultReceiver(new Handler());
	}

	@Override
	protected void bindViews(View view) {
		mUserEmailView = (EditText)view.findViewById(R.id.userEmail);
		mUserPwdView = (EditText)view.findViewById(R.id.userPwd);
		mUserEmailLyt = (TextInputLayout)view.findViewById(R.id.userEmailLyt);
		mUserPwdLyt = (TextInputLayout)view.findViewById(R.id.userPwdLyt);
		mSignUpBtn = (Button)view.findViewById(R.id.signUpBtn);
	}

	@Override
	protected void unBindViews() {
		mUserEmailView = null;
		mUserPwdView = null;
		mUserEmailLyt = null;
		mUserPwdLyt = null;
		mSignUpBtn = null;
	}

	private void setUpUI() {
		mUserPwdView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
				int result = actionId & EditorInfo.IME_MASK_ACTION;
				switch(result) {
					case EditorInfo.IME_ACTION_DONE:
						// done stuff
						onSignUpBtnClicked();
						return true;
				}
				return false;
			}
		});
		mSignUpBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onSignUpBtnClicked();
			}
		});
		mUserEmailView.addTextChangedListener(mTextWatcher);
		mUserPwdView.addTextChangedListener(mTextWatcher);
		setupServiceReceiver();
	}

	@Override
	public void onResume() {
		super.onResume();
		mSignUpPresenter.onResume();
		// This is where we specify what happens when data is received from the service
		mResultReceiver.setReceiver(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		mSignUpPresenter.onStart();
	}

	@Override
	public void onPause() {
		super.onPause();
		mSignUpPresenter.onPause();
		mResultReceiver.setReceiver(null);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mSignUpPresenter.onDestroy();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void onSignUpBtnClicked() {
		if(mSignUpBtn.isEnabled()) {
			mMainLoginPresenter.register(getContext(), mUserEmailView.getText().toString(), mUserPwdView.getText().toString(), mResultReceiver);
		}
	}

	private void validate() {
		checkEmail();
		checkPwd();
		checkLoginBtn();
	}

	private void checkEmail() {
		mUserEmailLyt.setError(null);
		String email = mUserEmailView.getText().toString();
		if(TextUtils.isEmpty(email)) {
			mUserEmailLyt.setError(getString(R.string.field_mandatory));
		} else if(!CommonUtils.isValidEmail(email)) {
			mUserEmailLyt.setError(getString(R.string.email_wrong_fmt));
		}
	}

	private void checkPwd() {
		mUserPwdLyt.setError(null);
		if(TextUtils.isEmpty(mUserPwdView.getText().toString())) {
			mUserPwdLyt.setError(getString(R.string.field_mandatory));
		} else if(mUserPwdView.getText().toString().length() < 6) {
			mUserPwdLyt.setError(getString(R.string.pwd_wrong_fmt));
		}
	}

	private void checkLoginBtn() {
		boolean enablingReq = TextUtils.isEmpty(mUserEmailLyt.getError()) && TextUtils.isEmpty(mUserPwdLyt.getError());
		mSignUpBtn.setEnabled(enablingReq);
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		if (resultCode == RESULT_OK) {
			ApiResponse response = resultData.getParcelable(ServiceHelper.EXTRA_RESPONSE);
			if(response != null) {
				switch (response.getApiType()) {
					case REGISTER: {
						RegisterApiResponse registerApiResponse = (RegisterApiResponse) response.getResponse();
						if (registerApiResponse != null && response.getResponseCode() == ApiResponse.SUCCESS) {
							mMainLoginPresenter.onRegisterSuccess();
						}
					}
					break;
				}
			}
		}
	}
}
