package com.example.demo.services;

import com.example.demo.DAO.*;
import com.example.demo.DAO.Impl.*;
import com.example.demo.classes.Attribute;
import com.example.demo.models.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class CustomDBService {
    @Autowired
    UserDAO userDAO = new UserDAOImpl();
    @Autowired
    CartDAO cartDAO = new CartDAOImpl();
    @Autowired
    ProductDAO productDAO = new ProductDAOImpl();
    @Autowired
    OrderStatusDAO statusDAO = new OrderStatusDAOImpl();
    @Autowired
    OrderDAO orderDAO = new OrderDAOImpl();
    @Autowired
    OrderProductDAO orderProductDAO = new OrderProductDAOImpl();
    @Autowired
    PaymentMethodDAO paymentMethodDAO = new PaymentMethodDAOImpl();
    @Autowired
    BlenderAttributesDAO blenderAttributesDAO = new BlenderAttributesDAOImpl();
    @Autowired
    PhoneAttributesDAO phoneAttributesDAO = new PhoneAttributesDAOImpl();
    @Autowired
    ToasterAttributesDAO toasterAttributesDAO = new ToasterAttributesDAOImpl();

    public void confirmOrder(User user, LocalDate deliveryDate, String paymentMethod, List<Cart> carts) {
        // Создание нового заказа
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(Date.valueOf(LocalDate.now()));
        order.setDeliveryDate(Date.valueOf(deliveryDate));
        order.setStatus(statusDAO.getById(1L));
        order.setPaymentMethod(paymentMethodDAO.findByMethod(paymentMethod));

        // Сохранение заказа
        orderDAO.save(order);

        // Сохранение товаров заказа и обновление количества товаров
        List<OrderProduct> orderProductList = new ArrayList<>();
        List<Product> productList = new ArrayList<>();
        for (Cart cart : carts) {
            OrderProduct orderProduct = new OrderProduct();
            OrderProduct.OrderProductPK pk = new OrderProduct.OrderProductPK();
            pk.setOrderId(order.getId());
            pk.setProductId(cart.getProduct().getId());
            orderProduct.setId(pk);
            orderProduct.setOrder(order);
            orderProduct.setProduct(cart.getProduct());
            orderProduct.setQuantity(cart.getQuantity());
            orderProductList.add(orderProduct);

            // Обновление количества товаров
            Product product = cart.getProduct();
            product.setQuantity(product.getQuantity() - cart.getQuantity());
            product.setBought(product.getBought() + cart.getQuantity());
            productList.add(product);
        }

        productDAO.saveCollection(productList);
        orderProductDAO.saveCollection(orderProductList);
        cartDAO.deleteCollection(carts);
    }

    public List<Attribute> getAttributesByCategory(String category) {
        return switch (category) {
            case "phone" -> Arrays.asList(
                    new Attribute("screenDiagonal", "text", "Диагональ экрана"),
                    new Attribute("processor", "text", "Процессор"),
                    new Attribute("memorySize", "text", "Размер памяти"),
                    new Attribute("ramSize", "text", "Размер ОЗУ"),
                    new Attribute("screenType", "text", "Тип экрана")
            );
            case "blender" -> Arrays.asList(
                    new Attribute("capacity", "text", "Емкость"),
                    new Attribute("hasTimer", "checkbox", "Наличие таймера"),
                    new Attribute("powerOutput", "text", "Мощность"),
                    new Attribute("numberOfSpeeds", "text", "Количество скоростей"),
                    new Attribute("hasDisplay", "checkbox", "Наличие дисплея")
            );
            case "toaster" -> Arrays.asList(
                    new Attribute("powerOutput", "text", "Мощность"),
                    new Attribute("numberOfSlots", "text", "Количество слотов"),
                    new Attribute("hasDisplay", "checkbox", "Наличие дисплея"),
                    new Attribute("hasTimer", "checkbox", "Наличие таймера"),
                    new Attribute("hasBreadSensor", "checkbox", "Наличие сенсора хлеба")
            );
            default -> Collections.emptyList();
        };
    }

    public List<String> getBrands() {
        List<String> brands = new ArrayList<String>();
        //TODO
        brands.add("Brand1");
        brands.add("Brand2");
        brands.add("Brand3");
        return brands;
    }

    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        //TODO
        categories.add("phone");
        categories.add("toaster");
        categories.add("blender");
        return categories;
    }

    public List<Attribute> getProductAttributes(Product product) {
        String category = product.getCategory().getCategory();
        List<Attribute> attributes = new ArrayList<>();

        switch (category) {
            case "phone":
                PhoneAttributes phoneAttributes = phoneAttributesDAO.getById(product.getId());
                if (phoneAttributes != null) {
                    attributes.add(new Attribute("screenDiagonal", "text", "Диагональ экрана", String.valueOf(phoneAttributes.getScreenDiagonal())));
                    attributes.add(new Attribute("processor", "text", "Процессор", phoneAttributes.getProcessor()));
                    attributes.add(new Attribute("memorySize", "text", "Размер памяти", String.valueOf(phoneAttributes.getMemorySize())));
                    attributes.add(new Attribute("ramSize", "text", "Размер оперативной памяти", String.valueOf(phoneAttributes.getRamSize())));
                    attributes.add(new Attribute("screenType", "text", "Тип экрана", phoneAttributes.getScreenType()));
                }
                break;
            case "blender":
                BlenderAttributes blenderAttributes = blenderAttributesDAO.getById(product.getId());
                if (blenderAttributes != null) {
                    attributes.add(new Attribute("capacity", "text", "Емкость", String.valueOf(blenderAttributes.getCapacity())));
                    attributes.add(new Attribute("hasTimer", "checkbox", "Наличие таймера", String.valueOf(blenderAttributes.getHasTimer())));
                    attributes.add(new Attribute("powerOutput", "text", "Выходная мощность", String.valueOf(blenderAttributes.getPowerOutput())));
                    attributes.add(new Attribute("numberOfSpeeds", "text", "Количество скоростей", String.valueOf(blenderAttributes.getNumberOfSpeeds())));
                    attributes.add(new Attribute("hasDisplay", "checkbox", "Наличие дисплея", String.valueOf(blenderAttributes.getHasDisplay())));
                }
                break;
            case "toaster":
                ToasterAttributes toasterAttributes = toasterAttributesDAO.getById(product.getId());
                if (toasterAttributes != null) {
                    attributes.add(new Attribute("numberOfSlots", "text", "Количество слотов", String.valueOf(toasterAttributes.getNumberOfSlots())));
                    attributes.add(new Attribute("hasBreadSensor", "checkbox", "Наличие сенсора хлеба", String.valueOf(toasterAttributes.getHasBreadSensor())));
                    attributes.add(new Attribute("powerOutput", "text", "Выходная мощность", String.valueOf(toasterAttributes.getPowerOutput())));
                    attributes.add(new Attribute("hasTimer", "checkbox", "Наличие таймера", String.valueOf(toasterAttributes.getHasTimer())));
                    attributes.add(new Attribute("hasDisplay", "checkbox", "Наличие дисплея", String.valueOf(toasterAttributes.getHasDisplay())));
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported category: " + category);
        }
        return attributes;
    }




    public boolean setProductAttributes(String category, Product product, Map<String, String> attributes) {
        switch (category) {
            case "phone":
                PhoneAttributes phoneAttributes = new PhoneAttributes();
                phoneAttributes.setProduct(product);
                phoneAttributes.setScreenType(attributes.get("screenType"));
                phoneAttributes.setProcessor(attributes.get("processor"));
                phoneAttributes.setMemorySize(parseInteger(attributes.get("memorySize")));
                phoneAttributes.setRamSize(parseInteger(attributes.get("ramSize")));
                phoneAttributesDAO.save(phoneAttributes);
                break;
            case "blender":
                BlenderAttributes blenderAttributes = new BlenderAttributes();
                blenderAttributes.setProduct(product);
                blenderAttributes.setNumberOfSpeeds(parseInteger(attributes.get("numberOfSpeeds")));
                blenderAttributes.setCapacity(parseDouble(attributes.get("capacity")));
                blenderAttributes.setPowerOutput(parseInteger(attributes.get("powerOutput")));
                blenderAttributes.setHasTimer(parseBoolean(attributes.get("hasTimer")));
                blenderAttributes.setHasDisplay(parseBoolean(attributes.get("hasDisplay")));
                blenderAttributesDAO.save(blenderAttributes);
                break;
            case "toaster":
                ToasterAttributes toasterAttributes = new ToasterAttributes();
                toasterAttributes.setProduct(product);
                toasterAttributes.setNumberOfSlots(parseInteger(attributes.get("numberOfSlots")));
                toasterAttributes.setHasBreadSensor(parseBoolean(attributes.get("hasBreadSensor")));
                toasterAttributes.setPowerOutput(parseInteger(attributes.get("powerOutput")));
                toasterAttributes.setHasTimer(parseBoolean(attributes.get("hasTimer")));
                toasterAttributes.setHasDisplay(parseBoolean(attributes.get("hasDisplay")));
                toasterAttributesDAO.save(toasterAttributes);
                break;
            default:
                return false;
        }
        return true;
    }

    private Integer parseInteger(String value) {
        return value != null ? Integer.parseInt(value) : null;
    }

    private Double parseDouble(String value) {
        return value != null ? Double.parseDouble(value) : null;
    }

    private Boolean parseBoolean(String value) {
        return value != null ? Boolean.parseBoolean(value) : null;
    }
}
