/* 
 * Copyright (C) 2008 Torgny Bjers
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xorcode.andtweet;

import com.xorcode.andtweet.TwitterUser.CredentialsVerified;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ViewFlipper;

/**
 * @author Torgny
 *
 */
public class SplashMoreActivity extends Activity {

	// Constants
	public static final String TAG = "SplashMoreActivity";

	// Local parameters
	boolean mSkipPreferences = false;

	// Local objects
	private ViewFlipper mFlipper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.splash_more);

		mFlipper = ((ViewFlipper) this.findViewById(R.id.splash_more_flipper));

		AlphaAnimation anim = (AlphaAnimation) AnimationUtils.loadAnimation(SplashMoreActivity.this, R.anim.fade_in);
		findViewById(R.id.splash_container).startAnimation(anim);

		final Button getstarted = (Button) findViewById(R.id.button_splash_get_started);
		getstarted.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(SplashMoreActivity.this, PreferencesActivity.class));
				finish();
			}
		});

		final Button learn_more = (Button) findViewById(R.id.button_splash_learn_more);
		learn_more.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mFlipper.showNext();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
        if (TwitterUser.getTwitterUser(this).getCredentialsVerified() == CredentialsVerified.SUCCEEDED) {
            mSkipPreferences = true;
        }
		if (mSkipPreferences) {
			Intent intent = new Intent(this, TweetListActivity.class);
			intent.setAction("com.xorcode.andtweet.INITIALIZE");
			startActivity(intent);
			finish();
		}
	}
}
