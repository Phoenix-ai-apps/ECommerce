package com.demo.ecommerce;

public interface  BaseView {


	void showProgress();
	void hideProgress();
	void showError(String message);
	void showMessage(String message);
	void finish();
}
