package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.firefoxdriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new FirefoxDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TESform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-T. You may modify this test to work with the 
	 * 	 * rest of your code. 
	 * 	 * This test is provided by Udacity to perpage#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertTrue(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertTrue(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));
	}

	@Test
	public void testLoginLogout() {
		doMockSignUp("testLoginlogout","Test","testLoginlogout","123");
		doLogIn("testLoginlogout", "123");

		// Logout
		WebElement logoutButton= driver.findElement(By.id("logout-button"));
		logoutButton.click();

		Assertions.assertFalse(driver.getTitle().equals("Home"));
		Assertions.assertEquals("Login", driver.getTitle());
	}

	public void RedirectToHome() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/home");
	}

	@Test
	public void testAddNote() {

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		doMockSignUp("Loi","PV2","LoiPV2","123");
		doLogIn("LoiPV2", "123");

		WebElement noteTab= driver.findElement(By.id("nav-notes-tab"));
		noteTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-tabContent")));
		Assertions.assertTrue(driver.findElement(By.id("nav-tabContent")).isDisplayed());

		WebElement addNoteButton= driver.findElement(By.id("add-note"));
		addNoteButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement inputNoteTitle = driver.findElement(By.id("note-title"));
		inputNoteTitle.click();
		inputNoteTitle.sendKeys("Input Note Title...");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement inputNoteDescription = driver.findElement(By.id("note-description"));
		inputNoteDescription.click();
		inputNoteDescription.sendKeys("Input Note Descripttion...");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteSubmitButton")));
		WebElement submitNoteButton = driver.findElement(By.id("noteSubmitButton"));
		submitNoteButton.click();

		RedirectToHome();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
		Assertions.assertTrue(driver.findElement(By.id("content-note-title")).getText().contains("Input Note Title..."));
		Assertions.assertTrue(driver.findElement(By.id("content-note-description")).getText().contains("Input Note Descripttion..."));
	}

	@Test
	public void testEditNote() {

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		testAddNote();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-note")));
		WebElement editNote = driver.findElement(By.id("edit-note"));
		editNote.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement inputNoteDescription = driver.findElement(By.id("note-description"));
		inputNoteDescription.click();
		inputNoteDescription.clear();
		inputNoteDescription.sendKeys("Input description_Edited...");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteSubmitButton")));
		WebElement submitNoteButton = driver.findElement(By.id("noteSubmitButton"));
		submitNoteButton.click();

		RedirectToHome();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		driver.findElement(By.id("nav-notes-tab")).click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
		Assertions.assertTrue(driver.findElement(By.id("content-note-description")).getText().contains("Input description_Edited..."));
	}

	@Test
	public void testDeleteNote() {

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		testAddNote();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-note")));
		WebElement deleteNote = driver.findElement(By.id("delete-note"));
		deleteNote.click();
		RedirectToHome();
		WebElement notesTable = driver.findElement(By.id("userTable"));
		List<WebElement> notesList = notesTable.findElements(By.tagName("tbody"));
		Assertions.assertEquals(0, notesList.size());
	}

    @Test
    public void testAddCredential() {

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        doMockSignUp("Loi","PV2","LoiPV2","123");
        doLogIn("LoiPV2", "123");

        WebElement credentialTab= driver.findElement(By.id("nav-credentials-tab"));
        credentialTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-credential")));
        WebElement addCredential= driver.findElement(By.id("add-credential"));
        addCredential.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        WebElement inputURL = driver.findElement(By.id("credential-url"));
        inputURL.click();
        inputURL.sendKeys("https://facebook.com/");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
        WebElement inputUsername = driver.findElement(By.id("credential-username"));
        inputUsername.click();
        inputUsername.sendKeys("LoiPV2");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        WebElement inputPassword = driver.findElement(By.id("credential-password"));
        inputPassword.click();
        inputPassword.sendKeys("123456Aa-");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-change")));
        WebElement submitCredential = driver.findElement(By.id("save-change"));
        submitCredential.click();

        RedirectToHome();
        WebElement credentialTable = driver.findElement(By.id("credentialTable"));
        List<WebElement> listCredentials = credentialTable.findElements(By.tagName("tbody"));

        Assertions.assertEquals(1, listCredentials.size());
		Assertions.assertNotEquals(driver.findElement(By.id("pass-credential")).getText(), "123456a-");
    }

	@Test
	public void testEditCredential() {

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		testAddCredential();

		WebElement credentialTab= driver.findElement(By.id("nav-credentials-tab"));
		credentialTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-credential")));
		WebElement editCredential= driver.findElement(By.id("edit-credential"));
		editCredential.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement inputURL = driver.findElement(By.id("credential-url"));
		inputURL.click();
		inputURL.clear();
		inputURL.sendKeys("https://youtube.com");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-change")));
		WebElement submitCredential = driver.findElement(By.id("save-change"));
		submitCredential.click();

		RedirectToHome();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		driver.findElement(By.id("nav-credentials-tab")).click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		Assertions.assertTrue(driver.findElement(By.id("credential-url-data")).getText().contains("https://youtube.com"));
	}

	@Test
	public void testDeleteCredential() {

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		testAddCredential();

		WebElement credentialTab= driver.findElement(By.id("nav-credentials-tab"));
		credentialTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-credential")));
		WebElement deleteCredential= driver.findElement(By.id("delete-credential"));
		deleteCredential.click();

		RedirectToHome();
		WebElement credentialTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> listCredentials = credentialTable.findElements(By.tagName("tbody"));
		Assertions.assertEquals(0, listCredentials.size());
	}
}
