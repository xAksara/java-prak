package com.example.demo.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CatalogControllerTest extends CommonControllerTest {
    @Test
    public void testChoseCategory() {
        // Открыть главную страницу
        driver.get("http://localhost:8080/catalog/choseCategory")   ;

        assertEquals("Каталог", driver.getTitle());

        WebElement header = driver.findElement(By.tagName("h1"));
        assertEquals("Выберите категорию", header.getText());

        // Проверить наличие формы выбора категории
        WebElement categoryForm = driver.findElement(By.tagName("form"));
        assertNotNull(categoryForm);

        // Проверить наличие элемента выбора категории
        WebElement categorySelect = driver.findElement(By.id("categorySelect"));
        assertNotNull(categorySelect);

        WebElement phoneOption = categorySelect.findElement(By.xpath("//option[text()='phone']"));
        assertNotNull(phoneOption);

        // Проверить наличие кнопки отправки формы
        WebElement submitButton = driver.findElement(By.tagName("button"));
        assertNotNull(submitButton);
    }

    @Test
    public void testCatalogPage() {
        // Открыть главную страницу
        driver.get("http://localhost:8080/catalog?category=phone")   ;

        // Проверить заголовок страницы
        assertEquals("Catalog", driver.getTitle());

        // Проверить наличие заголовка "Catalog"
        WebElement header = driver.findElement(By.tagName("h1"));
        assertEquals("Catalog", header.getText());

        // Проверить наличие формы фильтров
        WebElement filterForm = driver.findElement(By.id("filterForm"));
        assertTrue(filterForm.isDisplayed());

        // Проверить наличие элементов в форме фильтров
        WebElement brandRadioAny = driver.findElement(By.id("all"));
        assertTrue(brandRadioAny.isDisplayed());

        WebElement applyButton = driver.findElement(By.xpath("//button[@type='submit']"));
        assertTrue(applyButton.isDisplayed());

        // Проверить отображение продуктов
        WebElement productsDiv = driver.findElement(By.id("products"));
        assertTrue(productsDiv.isDisplayed());

        WebElement sortSelect = driver.findElement(By.id("sort"));
        sortSelect.sendKeys("price_desc");

        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        List<WebElement> priceElements = driver.findElements(By.xpath("//div[@id='products']//p[contains(text(),'Цена:')]"));

        // Проверяем, что цены упорядочены по убыванию
        double previousPrice = Double.MAX_VALUE;
        for (WebElement priceElement : priceElements) {
            // Извлекаем цену из текста элемента
            String priceText = priceElement.getText().replace("Цена: ", "");
            double currentPrice = Double.parseDouble(priceText);
            // Проверяем, что текущая цена не больше предыдущей
            assertTrue(currentPrice <= previousPrice);
            previousPrice = currentPrice;
        }
    }
}
