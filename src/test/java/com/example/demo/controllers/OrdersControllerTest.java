package com.example.demo.controllers;

import com.example.demo.DAO.Impl.UserDAOImpl;
import com.example.demo.DAO.UserDAO;
import com.example.demo.models.Order;
import com.example.demo.models.Product;
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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class OrdersControllerTest extends  CommonControllerTest {
    @Test
    public void testOrders() {
        driver.get("http://localhost:8080/orders");
        assertEquals("Login", driver.getTitle());
        login("6dem", "password");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("http://localhost:8080/orders");
        WebElement noOrdersMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(), 'У вас небыло заказов')]")));
        assertTrue(noOrdersMessage.isDisplayed());


        driver.get("http://localhost:8080/product/1");
        WebElement quantityInput = driver.findElement(By.name("quantity"));
        quantityInput.clear();
        quantityInput.sendKeys("2");

        // Нажать кнопку "Добавить в корзину"
        WebElement addToCartButton = driver.findElement(By.cssSelector("form#add-to-cart-form button[type='submit']"));
        addToCartButton.click();

        driver.get("http://localhost:8080/cart");
        WebElement makeOrderButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='btn btn-primary' and text()='Оформить заказ']")));
        makeOrderButton.click();

        // Проверка перехода на страницу оформления заказа
        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/makeOrder"));

        // Установка даты доставки (сегодняшняя дата + 7 дней)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String deliveryDate = LocalDate.now().plusDays(7).format(formatter).toString();
        WebElement deliveryDateInput = driver.findElement(By.id("deliveryDate"));
        deliveryDateInput.sendKeys(deliveryDate);

        // Выбор способа оплаты
        WebElement paymentMethodSelect = driver.findElement(By.id("paymentMethod"));
        Select paymentMethodDropdown = new Select(paymentMethodSelect);
        paymentMethodDropdown.selectByVisibleText("Наличными"); // Можно выбрать другой способ оплаты, если требуется

        // Нажатие кнопки "Подтвердить заказ"
        WebElement confirmOrderButton = driver.findElement(By.xpath("//button[@type='submit' and text()='Подтвердить заказ']"));
        confirmOrderButton.click();

        // Проверка перехода на страницу подтверждения заказа
        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/confirmOrder"));

        // Проверка наличия надписи "Спасибо за заказ!"
        WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='container']//p[contains(text(), 'Спасибо за заказ!')]")));
        assertTrue(confirmationMessage.isDisplayed());


        //Смотрим строаницу заказов
        driver.get("http://localhost:8080/orders");
        List<WebElement> orderHeaders = driver.findElements(By.xpath("//h2[contains(text(), 'Заказ №')]"));
        assertFalse(orderHeaders.isEmpty(), "No orders found with 'Заказ №' in the header.");

        for (WebElement orderHeader : orderHeaders) {
            WebElement detailsLink = orderHeader.findElement(By.xpath("following-sibling::a[contains(text(), 'Детали')]"));
            assertTrue(detailsLink.isDisplayed(), "Details link not found for order.");

            // Нажимаем на первую найденную ссылку "Детали"
            detailsLink.click();

            // Проверка изменения заголовка страницы на "Детали заказа"
            wait.until(ExpectedConditions.titleIs("Детали заказа"));

            // Проверка наличия ссылки на продукт с id 1
            WebElement productLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@href, '/product/1')]")));
            assertTrue(productLink.isDisplayed(), "Product link not found.");

            // Проверка, что количество товара в заказе равно двум
            WebElement quantityElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(), 'Количество:')]/span")));
            assertEquals("2", quantityElement.getText(), "Product quantity is not 2.");

            // Выход из цикла после проверки первого заказа
            break;
        }

        // удаляем данные о заказе и возвращаем товар на место
        List<Order> orders = orderDAO.findByUser(userDAO.findByLogin("6dem"));
        for (Order o : orders) {
            orderProductDAO.deleteCollection(orderProductDAO.findByOrder(o));
        }
        orderDAO.deleteCollection(orders);
        Product product = productDAO.getById(1L);
        product.setQuantity(product.getQuantity() + 2);
        product.setBought(product.getBought() - 2);

    }
}
