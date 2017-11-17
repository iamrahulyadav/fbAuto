package main;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import common.Comm;
import common.Logger_;
import pageElements.Notifications;

public class CheckNotifications {

	public static boolean Notification(WebDriver driver_) {
		try {
			if (!CheckWindow(driver_)) {
				return false;
			}
			
			if (!Notifications.TitleNotification(driver_).getText().contains("Facebook Notifications")) {
				return false;
			}
			
			if (!TurnOff(driver_)) {
				return false;
			}
			
			return true;
		}
		catch (Exception e) {
			Logger_.Logging_(e.getMessage() + e.getLocalizedMessage(), "severe", e, driver_);
			return false;
		}
	}
	
	private static boolean CheckWindow(WebDriver driver_) {
		try {
			if (!Comm.checkElement(Notifications.WindowNotification(driver_), driver_)) {
				return false;
			}
			
			return true;
		}
		catch (NoSuchElementException e) {
			return false;
		}
		catch (Exception e) {
			Logger_.Logging_(e.getMessage() + e.getLocalizedMessage(), "severe", e, driver_);
			return false;
		}
	}
	
	private static boolean TurnOff(WebDriver driver_) {
		try {
			if (!Comm.checkElement(Notifications.ButNotNow(driver_), driver_)) {
				return false;
			}
			
			Notifications.ButNotNow(driver_).click();
			Logger_.Logging_(Thread.currentThread().getStackTrace()[1] + " - Not Now Button Click", "info");
			
			return true;
		}
		catch (Exception e) {
			Logger_.Logging_(e.getMessage() + e.getLocalizedMessage(), "severe", e, driver_);
			return false;
		}
	}
}