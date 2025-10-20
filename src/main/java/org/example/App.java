package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class App {
    protected static String url = "https://www.saucedemo.com/";
    WebDriver webDriver;

    public void startChromeBrowser() {
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get(url);
    }

    public void loginUserPassword(String user, String pass) throws InterruptedException {
        webDriver.findElement(By.id("user-name")).sendKeys(user);
        webDriver.findElement(By.id("password")).sendKeys(pass);
        webDriver.findElement(By.id("login-button")).click();
        Thread.sleep(5000); // wait for login
    }

    public void invantoryList() {
        List<WebElement> inventory = webDriver.findElements(By.className("inventory_item"));
        System.out.println("Total products: " + inventory.size());
        for (WebElement item : inventory) {
            System.out.println(item.getText());
        }
    }

    public void clickAllItemsAndAddToCart() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        List<WebElement> products = webDriver.findElements(By.className("inventory_item_name"));

        for (int i = 0; i < products.size(); i++) {
            // Refresh the list every time after going back
            products = webDriver.findElements(By.className("inventory_item_name"));

            WebElement product = wait.until(ExpectedConditions.elementToBeClickable(products.get(i)));
            product.click();

            // Wait 5 seconds after entering the product page
            Thread.sleep(5000);

            // Add to cart
            WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn_primary.btn_inventory")));
            addToCartBtn.click();

            // Wait 5 seconds after adding to cart
            Thread.sleep(5000);

            // Back to home page
            WebElement backToProducts = wait.until(ExpectedConditions.elementToBeClickable(By.id("back-to-products")));
            backToProducts.click();

            // Wait 5 seconds on the home page
            Thread.sleep(5000);
        }
    }
public void productSelect() throws InterruptedException {
         WebElement dropdownElement =  webDriver.findElement(By.className("product_sort_container"));
         Select dropdown = new Select(dropdownElement);

         for (int i=0; i<=4; i++) {
             dropdown.selectByIndex(i);
             Thread.sleep(2000);
         }
}
    public static void main(String[] args) throws InterruptedException {
        App app = new App();
        app.startChromeBrowser();
        app.loginUserPassword("standard_user", "secret_sauce");
        app.clickAllItemsAndAddToCart();
        app.invantoryList();
        app.productSelect();
    }
}
