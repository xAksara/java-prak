package com.example.demo.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class IndexControllerTest extends CommonControllerTest {

    @Test
    public void testHomePage() {
        // Открыть главную страницу
        driver.get("http://localhost:8080/index")   ;

        // Проверить заголовок страницы
        assertEquals("Главная страница", driver.getTitle());

        // Проверить наличие заголовка "Интернет магазин бытовой техники"
        WebElement header = driver.findElement(By.tagName("h3"));
        assertEquals("Интернет магазин бытовой техники", header.getText());

        // Проверить наличие и текст кнопок
        WebElement phonesButton = driver.findElement(By.linkText("Телефоны"));
        assertNotNull(phonesButton);
        assertEquals("Телефоны", phonesButton.getText());

        WebElement blendersButton = driver.findElement(By.linkText("Блендеры"));
        assertNotNull(blendersButton);
        assertEquals("Блендеры", blendersButton.getText());

        WebElement toastersButton = driver.findElement(By.linkText("Тостеры"));
        assertNotNull(toastersButton);
        assertEquals("Тостеры", toastersButton.getText());

        // Проверить ссылки кнопок
        assertRelativeLink("/catalog?category=phone", phonesButton.getAttribute("href"));
        assertRelativeLink("/catalog?category=blender", blendersButton.getAttribute("href"));
        assertRelativeLink("/catalog?category=toaster", toastersButton.getAttribute("href"));

    }
    private void assertRelativeLink(String expectedPath, String actualHref) {
        try {
            URI baseUri = new URI("http://localhost:8080/");
            URI actualUri = baseUri.resolve(actualHref);
            URI expectedUri = baseUri.resolve(expectedPath);
            assertEquals(expectedUri, actualUri);
        } catch (URISyntaxException e) {
            fail("Ошибка при анализе ссылки: " + e.getMessage());
        }
    }
    @Test
    public void testHeader() {
        driver.get("http://localhost:8080/index")   ;

        // Проверить заголовок страницы
        assertEquals("Главная страница", driver.getTitle());
        // Проверка наличия и текста ссылки "Главная"
        WebElement rootLink = driver.findElement(By.id("rootLink"));
        assertNotNull(rootLink);
        assertEquals("Главная", rootLink.getText());

        // Проверка наличия и текста ссылки "Каталог"
        WebElement catalogLink = driver.findElement(By.id("catalogLink"));
        assertNotNull(catalogLink);
        assertEquals("Каталог", catalogLink.getText());

        // Проверка наличия и текста ссылки "Корзина"
        WebElement cartLink = driver.findElement(By.id("cartLink"));
        assertNotNull(cartLink);
        assertEquals("Корзина", cartLink.getText());

        // Проверка наличия и текста ссылки "Заказы"
        WebElement ordersLink = driver.findElement(By.id("ordersLink"));
        assertNotNull(ordersLink);
        assertEquals("Заказы", ordersLink.getText());

        // Проверка наличия и текста ссылки "Личный кабинет"
        WebElement personalLink = driver.findElement(By.id("personalLink"));
        assertNotNull(personalLink);
        assertEquals("Личный кабинет", personalLink.getText());

        // Проверка наличия и текста ссылки "Контакты"
        WebElement contactLink = driver.findElement(By.id("contactLink"));
        assertNotNull(contactLink);
        assertEquals("Контакты", contactLink.getText());

        // Проверка наличия и текста ссылки "Админ панель" (если пользователь админ)
        List<WebElement> adminLinks = driver.findElements(By.id("adminLink"));
        if (!adminLinks.isEmpty()) {
            WebElement adminLink = adminLinks.get(0);
            assertNotNull(adminLink);
            assertEquals("Админ панель", adminLink.getText());
        }
    }

    @Test
    public void testFooter() {
        driver.get("http://localhost:8080/index")   ;
        assertEquals("Главная страница", driver.getTitle());
        WebElement footer = driver.findElement(By.tagName("footer"));
        assertNotNull(footer);
        String footerText = footer.getText();
        assertTrue(footerText.contains("Магазин бытовой техники"));
        assertTrue(footerText.contains("Шевцов Алексей, 328"));
    }
}
