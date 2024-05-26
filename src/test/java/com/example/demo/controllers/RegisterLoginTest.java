package com.example.demo.controllers;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class RegisterLoginTest extends CommonControllerTest {
    @Test
    public void testRegistrationPage() {
        driver.get("http://localhost:8080/registration");

        // Проверка наличия формы регистрации
        WebElement registrationForm = driver.findElement(By.tagName("form"));

        // Проверка наличия полей ввода userLogin, userPassword и confirmPassword
        WebElement userLoginInput = driver.findElement(By.name("userLogin"));
        WebElement userPasswordInput = driver.findElement(By.name("userPassword"));
        WebElement confirmPasswordInput = driver.findElement(By.name("confirmPassword"));

        // Проверка, что все поля присутствуют на странице
        assertNotNull(registrationForm);
        assertNotNull(userLoginInput);
        assertNotNull(userPasswordInput);
        assertNotNull(confirmPasswordInput);
    }

    @Test
    public void testCreatingAccount() {
        driver.get("http://localhost:8080/registration");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement userLoginInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("userLogin")));
        WebElement userPasswordInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("userPassword")));
        WebElement confirmPasswordInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("confirmPassword")));
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("button")));

        // Попытка создания аккаунта с уже существующим логином
        userLoginInput.sendKeys("6dem");
        userPasswordInput.sendKeys("password");
        confirmPasswordInput.sendKeys("password");

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-danger")));
        assertEquals("Такой логин уже занят", errorMessage.getText());

        // Повторное заполнение полей ввода с несовпадающими паролями
         userLoginInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("userLogin")));
         userPasswordInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("userPassword")));
         confirmPasswordInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("confirmPassword")));
         submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("button")));

        userLoginInput.sendKeys("TestRegister");
        userPasswordInput.sendKeys("password");
        confirmPasswordInput.sendKeys("differentPassword");

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-danger")));
        assertEquals("Пароли не совпадают", errorMessage.getText());

        // Попытка создания аккаунта с уникальным логином и совпадающими паролями
        userLoginInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("userLogin")));
        userPasswordInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("userPassword")));
        confirmPasswordInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("confirmPassword")));
        submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("button")));


        userLoginInput.sendKeys("TestRegister");
        userPasswordInput.sendKeys("1234");
        confirmPasswordInput.sendKeys("1234");

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        wait.until(ExpectedConditions.titleIs("Главная страница"));
        assertEquals("Главная страница", driver.getTitle());

        userDAO.delete(userDAO.findByLogin("TestRegister"));
    }


    @Test
    public void testLogin() {
        driver.get("http://localhost:8080/login");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        login("6dem", "veryeasy");
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Invalid username and password.')]")));
        assertTrue(errorMessage.isDisplayed());

        login("6dem7", "password");
        errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Invalid username and password.')]")));
        assertTrue(errorMessage.isDisplayed());


        login("6dem", "password");
        wait.until(ExpectedConditions.titleIs("Главная страница"));
        assertEquals("Главная страница", driver.getTitle());
    }
}
