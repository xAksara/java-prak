package com.example.demo.controllers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
public class PersonalControllerTest extends CommonControllerTest {
    @Test
    public void testPersonalPage() {
        driver.get("http://localhost:8080/personal");
        assertEquals("Login", driver.getTitle());
        login("6dem", "password");
        driver.get("http://localhost:8080/personal");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loginElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(), 'Логин:')]/span")));        assertEquals("6dem", loginElement.getText());

        WebElement ordersLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/orders' and text()='Мои заказы']")));
        ordersLink.click();

        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/orders"));
        assertEquals("http://localhost:8080/orders", driver.getCurrentUrl());

        driver.get("http://localhost:8080/personal");
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit' and text()='Выйти']")));
        logoutButton.click();

        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/index"));
        assertEquals("http://localhost:8080/index", driver.getCurrentUrl());

        driver.get("http://localhost:8080/personal");
        assertEquals("Login", driver.getTitle());
    }
}
