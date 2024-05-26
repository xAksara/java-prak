package com.example.demo.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.*;
import com.example.demo.DAO.Impl.UserDAOImpl;
import com.example.demo.DAO.UserDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class AdminControllerTest extends CommonControllerTest {
    @Test
    public void testAdminPanel() {
        driver.get("http://localhost:8080/admin");
        assertEquals("Login", driver.getTitle());
        login("login3", "password3");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("http://localhost:8080/admin");

        // Переход на страницу добавления товара
        WebElement addProductButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/admin/choseCategory']")));
        addProductButton.click();
        assertEquals("Add Product", driver.getTitle());

        // Переход на страницу поиска товара
        driver.get("http://localhost:8080/admin");
        WebElement searchProductButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/admin/searchProduct']")));
        searchProductButton.click();
        assertEquals("Search Product", driver.getTitle());

        // Переход на страницу добавления пользователя
        driver.get("http://localhost:8080/admin");
        WebElement addUserButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/admin/addUser']")));
        addUserButton.click();
        assertEquals("Add User", driver.getTitle());

        // Переход на страницу поиска пользователя
        driver.get("http://localhost:8080/admin");
        WebElement searchUserButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/admin/searchUser']")));
        searchUserButton.click();
        assertEquals("Search User", driver.getTitle());
    }

    @Test
    public void testAddSearchEditDeleteProduct() {
        driver.get("http://localhost:8080/admin");
        assertEquals("Login", driver.getTitle());
        login("login3", "password3");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("http://localhost:8080/admin");

        // Переход на страницу добавления товара
        WebElement addProductButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/admin/choseCategory']")));
        addProductButton.click();

        WebElement categorySelect = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("categorySelect")));
        Select select = new Select(categorySelect);
        select.selectByVisibleText("phone");

        // Нажать кнопку submit
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();


        // Заполнение формы добавления товара
        WebElement productNameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("productName")));
        productNameInput.sendKeys("New Phone");

        WebElement productPriceInput = driver.findElement(By.id("productPrice"));
        productPriceInput.sendKeys("1000");

        WebElement productBrandSelect = driver.findElement(By.id("productBrand"));
        Select brandSelect = new Select(productBrandSelect);
        brandSelect.selectByVisibleText("Brand1");

        WebElement productQuantityInput = driver.findElement(By.id("productQuantity"));
        productQuantityInput.sendKeys("10");

        WebElement productDescriptionInput = driver.findElement(By.id("productDescription"));
        productDescriptionInput.sendKeys("A new generation phone with advanced features.");

        WebElement screenDiagonalInput = driver.findElement(By.id("screenDiagonal"));
        screenDiagonalInput.sendKeys("6.5");

        WebElement processorInput = driver.findElement(By.id("processor"));
        processorInput.sendKeys("Octa-Core");

        WebElement memorySizeInput = driver.findElement(By.id("memorySize"));
        memorySizeInput.sendKeys("128");

        WebElement ramSizeInput = driver.findElement(By.id("ramSize"));
        ramSizeInput.sendKeys("8");

        WebElement screenTypeInput = driver.findElement(By.id("screenType"));
        screenTypeInput.sendKeys("AMOLED");

// Нажать кнопку submit
        addProductButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@type='submit' and contains(text(), 'Add Product')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addProductButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addProductButton);

        driver.get("http://localhost:8080/catalog?category=phone");
        // Проверка наличия добавленного продукта на странице
        WebElement addedProductLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[h2[text()='New Phone']]")));
        assertNotNull(addedProductLink);

        String productUrl = addedProductLink.getAttribute("href");
        String productId = productUrl.substring(productUrl.lastIndexOf("/") + 1);

        driver.get("http://localhost:8080/admin");

        // Переход на страницу добавления товара
        WebElement searchProductButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/admin/searchProduct']")));
        searchProductButton.click();


        WebElement productIdInput = driver.findElement(By.id("productId"));
        productIdInput.sendKeys(productId);

        WebElement searchButton = driver.findElement(By.xpath("//button[text()='Search']"));
        searchButton.click();

        productNameInput = driver.findElement(By.id("productName"));
        productNameInput.clear();

// Введем новое значение в поле названия товара
        productNameInput.sendKeys("New Edited Phone");


// Найдем кнопку "Edit" и кликнем на нее
        WebElement editButton = driver.findElement(By.xpath("//button[text()='Edit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", editButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);

        productIdInput = driver.findElement(By.id("productId"));
        productIdInput.sendKeys(productId);

        searchButton = driver.findElement(By.xpath("//button[text()='Search']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", searchButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchButton);

        WebElement productName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='productName' and @value='New Edited Phone']")));
        assertNotNull(productName);

// Нажатие кнопки "Delete"
        WebElement deleteButton = driver.findElement(By.xpath("//button[text()='Delete']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", deleteButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteButton);

        WebElement successAlert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='alert alert-danger mt-4']")));
        assertNotNull(successAlert);

    }

    @Test
    public void testAddSearchEditDeleteUser() {
        driver.get("http://localhost:8080/admin");
        assertEquals("Login", driver.getTitle());
        login("login3", "password3");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("http://localhost:8080/admin");

        // Переход на страницу добавления товара
        WebElement addProductButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/admin/addUser']")));
        addProductButton.click();


        driver.findElement(By.id("userLogin")).sendKeys("adminTest");
        driver.findElement(By.id("userPassword")).sendKeys("1234");
        driver.findElement(By.id("userLastName")).sendKeys("Doe");
        driver.findElement(By.id("userFirstName")).sendKeys("John");
        driver.findElement(By.id("userSurname")).sendKeys("Smith");
        driver.findElement(By.id("userBirthday")).sendKeys("2000-01-01");
        driver.findElement(By.id("userPhone")).sendKeys("+1234567890");
        driver.findElement(By.id("userEmail")).sendKeys("adminTest@example.com");
        driver.findElement(By.id("userAddress")).sendKeys("123 Main St");
        ((JavascriptExecutor) driver).executeScript("document.getElementById('userIsAdmin').checked = true;");

// Нажать кнопку submit
        WebElement addUserButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@type='submit' and contains(text(), 'Add User')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addUserButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addUserButton);

        driver.get("http://localhost:8080/admin");

        WebElement searchUserButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/admin/searchUser']")));
        searchUserButton.click();

        Long userId = userDAO.findByLogin("adminTest").getId();

        WebElement userIdInput = driver.findElement(By.id("userId"));
        userIdInput.sendKeys(userId.toString());

        WebElement searchButton = driver.findElement(By.xpath("//button[text()='Search']"));
        searchButton.click();

        WebElement userNameInput = driver.findElement(By.id("userLastName"));
        userNameInput.clear();

// Введем новое значение в поле названия товара
        userNameInput.sendKeys("New Edited Last Name");


// Найдем кнопку "Edit" и кликнем на нее
        WebElement editButton = driver.findElement(By.xpath("//button[text()='Edit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", editButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);

        userIdInput = driver.findElement(By.id("userId"));
        userIdInput.sendKeys(userId.toString());

        searchButton = driver.findElement(By.xpath("//button[text()='Search']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", searchButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchButton);

        WebElement userName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='userLastName' and @value='New Edited Last Name']")));
        assertNotNull(userName);

// Нажатие кнопки "Delete"
        WebElement deleteButton = driver.findElement(By.xpath("//button[text()='Delete']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", deleteButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteButton);

        WebElement successAlert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='alert alert-danger mt-4']")));
        assertNotNull(successAlert);

    }
}
