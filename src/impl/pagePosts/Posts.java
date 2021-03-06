package impl.pagePosts;

import java.lang.reflect.Field;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.Comm;
import common.Logger_;
import common.structures.PagePostsStructure;
import common.structures.SearchStructure;
import pageElements.Page;
import pageElements.PagePosts;

public class Posts {

	public static PagePostsStructure[] GetPagePosts(WebDriver driver_, SearchStructure searchStructure_) {
		try {
			
			if (!GoToPage.Go(driver_, searchStructure_)) {
				return null;
			}
			
			if (!GoToPosts(driver_)) {
				return null;
			}
			
			String[][] listPosts_ = ListPagePosts(driver_, Integer.parseInt(searchStructure_.postsNum));
			
			return AddDataToStructure(listPosts_);
		}
		catch (Exception e) {
			Logger_.Logging_(e.getMessage() + e.getLocalizedMessage(), "severe", e);
			return null;
		}
	}
	
	private static boolean GoToPosts(WebDriver driver_) {
		try {
			
			if (!PostsButtonClick(driver_)) {
				return false;
			}
			
			if (!CheckPostsPage(driver_)) {
				return false;
			}
			
			return true;
		}
		catch (Exception e) {
			Logger_.Logging_(e.getMessage() + e.getLocalizedMessage(), "severe", e);
			return false;
		}
	}
	
	private static boolean PostsButtonClick(WebDriver driver_) {
		try {
			
			if (!Comm.checkElement(Page.ButPostsLeftMenu(driver_), driver_)) {
				Logger_.Logging_(Thread.currentThread().getStackTrace()[1] + " - Page Posts Button IS NOT Present and/or Visible", "info");
				return false;
			}
			
			Logger_.Logging_(Thread.currentThread().getStackTrace()[1] + " - Page Posts Button IS Present and Visible", "info");
			
			Page.ButPostsLeftMenu(driver_).click();
			Logger_.Logging_(Thread.currentThread().getStackTrace()[1] + " - Page Posts Button Click", "info");
			
			return true;
		}
		catch (Exception e) {
			Logger_.Logging_(e.getMessage() + e.getLocalizedMessage(), "severe", e, driver_);
			return false;
		}
	}
	
	private static boolean CheckPostsPage(WebDriver driver_) {
		try {
			
			if (!Page.ButPostsLeftMenuSelected(driver_).getAttribute("class").contains("_2yap")) {
				Logger_.Logging_(Thread.currentThread().getStackTrace()[1] + " - EXIT WRONG Page: Posts", "info");
				return false;
			}
			
			Logger_.Logging_(Thread.currentThread().getStackTrace()[1] + " - Page: Posts", "info");
			return true;
		}
		catch (Exception e) {
			Logger_.Logging_(e.getMessage() + e.getLocalizedMessage(), "severe", e, driver_);
			return false;
		}
	}
	
	private static String[][] ListPagePosts(WebDriver driver_, int postsNum_) {
		try {
			
			Thread.sleep(5000);
			Comm.WaitingUntil(driver_, PagePosts.PagePostsCreatePost(driver_), 10, 1);
			
			return PostsValues(driver_, GetListPagePosts.ListUrl(driver_, postsNum_), GetListPagePosts.ListText(driver_, postsNum_));
		}
		catch (Exception e) {
			Logger_.Logging_(e.getMessage() + e.getLocalizedMessage(), "severe", e);
			return null;
		}
	}
	
	private static String[][] PostsValues(WebDriver driver_, WebElement[] listPostsUrl_, WebElement[] ListPostsText_) {
		try {
			
			String[][] postsValues = new String[listPostsUrl_.length][2];
			
			for (int i = 0; i < listPostsUrl_.length; i++) {
				
				postsValues[i] = getPostValue(driver_, listPostsUrl_[i], ListPostsText_[i]);
			}
			
			return postsValues;
		}
		catch (Exception e) {
			Logger_.Logging_(e.getMessage() + e.getLocalizedMessage(), "severe", e, driver_);
			return null;
		}
	}
	
	private static String[] getPostValue(WebDriver driver_, WebElement postUrl_, WebElement postText_) {
		try {
			
			return new String[] {postUrl_.getAttribute("href"), postText_.getText()};
		}
		catch (Exception e) {
			Logger_.Logging_(e.getMessage() + e.getLocalizedMessage(), "severe", e, driver_);
			return null;
		}
	}
	
	private static PagePostsStructure[] AddDataToStructure(String[][] data_) {
		try {
			
			PagePostsStructure[] pagePostsStructure_ = new PagePostsStructure[data_.length];
			
			for (int i = 0; i < data_.length; i++) {
				
				pagePostsStructure_[i] = new PagePostsStructure();
				
				Field[] structFields = pagePostsStructure_[i].getClass().getDeclaredFields();
				
				structFields[0].set(pagePostsStructure_[i], String.valueOf(i));
				
				structFields[1].set(pagePostsStructure_[i], data_[i][0]);
				structFields[2].set(pagePostsStructure_[i], data_[i][1]);
			}
			
			return pagePostsStructure_;
		}
		catch (Exception e) {
			Logger_.Logging_(e.getMessage() + e.getLocalizedMessage(), "severe", e);
			return null;
		}
	}
}
