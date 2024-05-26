package com.example.demo.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductControllerTest extends CommonControllerTest {
    @Test
    public void testProductPage() {
        driver.get("http://localhost:8080/product/1");
        // Открыть страницу контактов
        assertEquals("Описание товара", driver.getTitle());

        // Проверить наличие всех элементов описания товара
        WebElement name = driver.findElement(By.tagName("h1"));
        WebElement price = driver.findElement(By.xpath("//p[contains(text(),'Цена:')]"));
        WebElement brand = driver.findElement(By.xpath("//p[contains(text(),'Бренд:')]"));
        WebElement category = driver.findElement(By.xpath("//p[contains(text(),'Категория:')]"));
        WebElement description = driver.findElement(By.xpath("//p[contains(text(),'Описание:')]"));
        WebElement quantity = driver.findElement(By.xpath("//p[contains(text(),'Количество:')]"));
        WebElement bought = driver.findElement(By.xpath("//p[contains(text(),'Куплено:')]"));
        WebElement hasDisplay = driver.findElement(By.xpath("//p[contains(text(),'hasDisplay:')]"));

        WebElement addToCartForm = driver.findElement(By.id("add-to-cart-form"));

        assertNotNull(name);
        assertNotNull(price);
        assertNotNull(brand);
        assertNotNull(category);
        assertNotNull(description);
        assertNotNull(quantity);
        assertNotNull(bought);
        assertNotNull(addToCartForm);
        assertNotNull(hasDisplay);

    }

    @Test
    public void testProductPageAddToCartUnauthorized() {
        driver.get("http://localhost:8080/product/1");
        // Открыть страницу контактов
        assertEquals("Описание товара", driver.getTitle());

        String expectedTitle = "Login";

        // Установить количество товара в 2
        WebElement quantityInput = driver.findElement(By.name("quantity"));
        quantityInput.clear();
        quantityInput.sendKeys("2");

        // Нажать кнопку "Добавить в корзину"
        WebElement addToCartButton = driver.findElement(By.cssSelector("form#add-to-cart-form button[type='submit']"));
        addToCartButton.click();

        // Проверить, что перенаправлены на страницу входа
        assertEquals(expectedTitle, driver.getTitle());
    }
}
