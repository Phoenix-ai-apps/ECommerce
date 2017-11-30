package com.demo.ecommerce;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.demo.ecommerce.di.DependencyInjector;
import com.demo.ecommerce.di.modules.AppModule;
import com.demo.ecommerce.di.components.AppComponent;
import com.demo.ecommerce.utils.NetworkUtils;


public class AppContext extends MultiDexApplication {

	private static final String TAG = "AppContext";
	private static AppContext instance;
	private static Context appContext = null;
	private RequestQueue requestQueue;
	private static AppComponent appComponent;

	@Override
	public void onCreate() {
		super.onCreate();
		instance=this;

		initDependencies();

		MultiDex.install(this);

	}

	private void initDependencies() {

		DependencyInjector.initialize(this);
		DependencyInjector.appComponent().inject(this);

	}

	public RequestQueue getRequestQueue() {
		if (requestQueue == null) {
			requestQueue = Volley.newRequestQueue(getInstance(), NetworkUtils.getHurlStack());
		}
		return requestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (requestQueue != null) {
			requestQueue.cancelAll(tag);
		}
	}


	public static AppContext getInstance() {
		return instance;
	}


	public static AppComponent getAppComponent(){
		// App Compent Object
		return appComponent;
	}

}
