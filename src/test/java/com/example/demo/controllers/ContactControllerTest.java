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
public class ContactControllerTest extends CommonControllerTest{
    @Test
    public void testContactsPage() {
        // Открыть страницу контактов
        driver.get("http://localhost:8080/contacts");

        // Проверить заголовок страницы
        assertEquals("Контакты", driver.getTitle());

        // Проверить наличие заголовков контактов
        WebElement h1 = driver.findElement(By.tagName("h1"));
        WebElement h2 = driver.findElement(By.tagName("h2"));

        assertEquals("*Здесь должны быть всякие ссылки на соцсети и прочее*", h1.getText());
        assertEquals("Контакт1", h2.getText());
    }
}
