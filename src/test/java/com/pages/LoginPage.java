package com.pages;

import com.base.Baseclass;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage extends Baseclass{
	private final Page page;

	private final Locator usernameInput;
	private final Locator passwordInput;
	private final Locator loginButton;
	private final Locator LogoutButton;

	public LoginPage(Page page) {
		this.page = page;
		this.usernameInput = page.locator("#username");
		this.passwordInput = page.locator("#password");
		this.loginButton = page.locator("#submit");
		this.LogoutButton = page.locator("//a[normalize-space()='Log out']");
	}

	// Page actions

	public void login(String username, String password) {
		fill(usernameInput, username);
		fill(passwordInput, password);
		loginButton.click();
	}

	public void waitForLogoutButton() {
		waitFor(LogoutButton);
	}

	public boolean isLoginSuccessful() {
		return LogoutButton.isVisible();
	}

	public void logout() {
		LogoutButton.click();
	}
}
