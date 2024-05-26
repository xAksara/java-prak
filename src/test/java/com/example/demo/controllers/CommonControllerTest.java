package com.example.demo.controllers;

import com.example.demo.DAO.Impl.OrderDAOImpl;
import com.example.demo.DAO.Impl.OrderProductDAOImpl;
import com.example.demo.DAO.Impl.ProductDAOImpl;
import com.example.demo.DAO.Impl.UserDAOImpl;
import com.example.demo.DAO.OrderDAO;
import com.example.demo.DAO.OrderProductDAO;
import com.example.demo.DAO.ProductDAO;
import com.example.demo.DAO.UserDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public abstract class CommonControllerTest {
    protected WebDriver driver;

    @Autowired
    UserDAO userDAO = new UserDAOImpl();
    @Autowired
    OrderProductDAO orderProductDAO = new OrderProductDAOImpl();
    @Autowired
    OrderDAO orderDAO = new OrderDAOImpl();
    @Autowired
    ProductDAO productDAO = new ProductDAOImpl();

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\pro10\\Учеба\\Javaprak\\demo\\build\\resources\\main\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void login(String username, String password) {
        driver.get("http://localhost:8080/login");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement userLoginInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("username")));
        WebElement userPasswordInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='submit'][value='Sign In']")));

        // Попытка создания аккаунта с уже существующим логином
        userLoginInput.sendKeys(username);
        userPasswordInput.sendKeys(password);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
    }
}