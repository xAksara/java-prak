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
public class CartControllerTest extends CommonControllerTest{
    @Test
    public void testCartPage() {
        driver.get("http://localhost:8080/cart");
        assertEquals("Login", driver.getTitle());


        login("6dem", "password");
        driver.get("http://localhost:8080/cart");

        assertEquals("Корзина", driver.getTitle());


        // Ожидание загрузки страницы и проверки наличия сообщения "ваша корзина пуста"
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement messageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[text()='Ваша корзина пуста']")));
        assertTrue(messageElement.isDisplayed());

        // Logout
        driver.get("http://localhost:8080/logout");

        // Логин под вторым пользователем
        login("login1", "password1");
        driver.get("http://localhost:8080/product/1");
        WebElement quantityInput = driver.findElement(By.name("quantity"));
        quantityInput.clear();
        quantityInput.sendKeys("2");

        // Нажать кнопку "Добавить в корзину"
        WebElement addToCartButton = driver.findElement(By.cssSelector("form#add-to-cart-form button[type='submit']"));
        addToCartButton.click();

        driver.get("http://localhost:8080/cart");

        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class, 'btn-danger') and text()='Удалить из корзины']")));
        assertTrue(deleteButton.isDisplayed());

        // Нажатие на кнопку удаления из корзины
        deleteButton.click();

        // Проверка, что корзина стала пуста
        WebElement emptyCartMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[text()='Ваша корзина пуста']")));
        assertTrue(emptyCartMessage.isDisplayed());

        driver.get("http://localhost:8080/product/1");
        quantityInput = driver.findElement(By.name("quantity"));
        quantityInput.clear();
        quantityInput.sendKeys("2");

        // Нажать кнопку "Добавить в корзину"
        addToCartButton = driver.findElement(By.cssSelector("form#add-to-cart-form button[type='submit']"));
        addToCartButton.click();

        driver.get("http://localhost:8080/cart");

        WebElement orderLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/makeOrder']")));
        assertTrue(orderLink.isDisplayed());
    }
}
