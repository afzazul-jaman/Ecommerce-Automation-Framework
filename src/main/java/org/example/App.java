package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class App {
    protected static String url = "https://www.saucedemo.com/";
    WebDriver webDriver;
    WebDriverWait wait;

    public void startChromeBrowser() {
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get(url);
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    }

    public void loginUserPassword(String user, String pass) {
        webDriver.findElement(By.id("user-name")).sendKeys(user);
        webDriver.findElement(By.id("password")).sendKeys(pass);
        webDriver.findElement(By.id("login-button")).click();

        // Wait and verify login
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));
        System.out.println("Login successful for user: " + user);
    }

    public void inventoryList() {
        List<WebElement> inventory = webDriver.findElements(By.className("inventory_item"));
        System.out.println("Total products: " + inventory.size());
        for (WebElement item : inventory) {
            System.out.println("Product: " + item.getText().split("\n")[0]);
        }
    }

    public void clickAllItemsAndAddToCart() {
        List<WebElement> products = webDriver.findElements(By.className("inventory_item_name"));

        for (int i = 0; i < products.size(); i++) {
            products = webDriver.findElements(By.className("inventory_item_name")); // refresh
            WebElement product = wait.until(ExpectedConditions.elementToBeClickable(products.get(i)));
            String productName = product.getText();
            product.click();

            // Add to cart
            WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button.btn_primary.btn_inventory")));
            addToCartBtn.click();
            System.out.println("Added to cart: " + productName);

            // Back to home
            WebElement backToProducts = wait.until(ExpectedConditions.elementToBeClickable(By.id("back-to-products")));
            backToProducts.click();
        }
    }

    public void productSelect() {
        WebElement dropdownElement = webDriver.findElement(By.className("product_sort_container"));
        Select dropdown = new Select(dropdownElement);

        String[] options = {"Name (A to Z)", "Name (Z to A)", "Price (low to high)", "Price (high to low)"};
        for (int i = 0; i < options.length; i++) {
            dropdown.selectByIndex(i);
            System.out.println("Selected sort option: " + options[i]);
            wait.withTimeout(Duration.ofSeconds(2));
        }
    }

    public static void main(String[] args) {
        App app = new App();
        app.startChromeBrowser();
        app.loginUserPassword("standard_user", "secret_sauce");
        app.clickAllItemsAndAddToCart();
        app.inventoryList();
        app.productSelect();
        System.out.println("Automation completed successfully.");
    }
}
