package com.example.demo.services;

import com.example.demo.DAO.*;
import com.example.demo.DAO.Impl.*;
import com.example.demo.classes.Attribute;
import com.example.demo.controllers.CartController;
import com.example.demo.models.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;

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
    @Autowired
    CategoryDAO categoryDAO = new CategoryDAOImpl();



    public static final Logger logger = Logger.getLogger(CustomDBService.class.getName());
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userDAO.findByLogin(authentication.getName());
    }

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

    public List<Attribute> getEmptyAttributesByCategory(String category) {
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

    public List<Attribute> getAllProductAttributes(Product product) {
        List<Attribute> attributes = new ArrayList<>();
//        attributes.add(new Attribute("productId", "text", "Product ID", String.valueOf(product.getId())));
        attributes.add(new Attribute("productName", "text", "Product Name", parseString(product.getName())));
        attributes.add(new Attribute("productPrice", "text", "Product Price", parseString(product.getPrice())));
        attributes.add(new Attribute("productBrand", "text", "Product Brand", parseString(product.getBrand())));
        attributes.add(new Attribute("productQuantity", "text", "Product Quantity", parseString(product.getQuantity())));
        attributes.add(new Attribute("productDescription", "text", "Product Description", parseString(product.getDescription())));
        attributes.addAll(getProductCategoryAttributes(product));
        return attributes;
    }


    public List<Attribute> getEmptyProductAttributes(String category) {
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("productName", "text", "Product Name"));
        attributes.add(new Attribute("productPrice", "text", "Product Price"));
        attributes.add(new Attribute("productBrand", "text", "Product Brand"));
        attributes.add(new Attribute("productQuantity", "text", "Product Quantity"));
        attributes.add(new Attribute("productDescription", "text", "Product Description"));
        attributes.addAll(getEmptyAttributesByCategory(category));
        return attributes;
    }

    public List<Attribute> getProductCategoryAttributes(Product product) {
        String category = product.getCategory().getCategory();
        List<Attribute> attributes = new ArrayList<>();

        switch (category) {
            case "phone":
                PhoneAttributes phoneAttributes = phoneAttributesDAO.getById(product.getId());
                if (phoneAttributes != null) {
                    attributes.add(new Attribute("screenDiagonal", "text", "Диагональ экрана", parseString(phoneAttributes.getScreenDiagonal())));
                    attributes.add(new Attribute("processor", "text", "Процессор", parseString(phoneAttributes.getProcessor())));
                    attributes.add(new Attribute("memorySize", "text", "Размер памяти", parseString(phoneAttributes.getMemorySize())));
                    attributes.add(new Attribute("ramSize", "text", "Размер оперативной памяти", parseString(phoneAttributes.getRamSize())));
                    attributes.add(new Attribute("screenType", "text", "Тип экрана", parseString(phoneAttributes.getScreenType())));
                }
                else attributes.addAll(getEmptyAttributesByCategory(category));
                break;
            case "blender":
                BlenderAttributes blenderAttributes = blenderAttributesDAO.getById(product.getId());
                if (blenderAttributes != null) {
                    attributes.add(new Attribute("capacity", "text", "Емкость", parseString(blenderAttributes.getCapacity())));
                    attributes.add(new Attribute("hasTimer", "checkbox", "Наличие таймера", parseString(blenderAttributes.getHasTimer())));
                    attributes.add(new Attribute("powerOutput", "text", "Выходная мощность", parseString(blenderAttributes.getPowerOutput())));
                    attributes.add(new Attribute("numberOfSpeeds", "text", "Количество скоростей", parseString(blenderAttributes.getNumberOfSpeeds())));
                    attributes.add(new Attribute("hasDisplay", "checkbox", "Наличие дисплея", parseString(blenderAttributes.getHasDisplay())));
                }
                else attributes.addAll(getEmptyAttributesByCategory(category));

                break;
            case "toaster":
                ToasterAttributes toasterAttributes = toasterAttributesDAO.getById(product.getId());
                if (toasterAttributes != null) {
                    attributes.add(new Attribute("numberOfSlots", "text", "Количество слотов",parseString(toasterAttributes.getNumberOfSlots())));
                    attributes.add(new Attribute("hasBreadSensor", "checkbox", "Наличие сенсора хлеба", parseString(toasterAttributes.getHasBreadSensor())));
                    attributes.add(new Attribute("powerOutput", "text", "Выходная мощность", parseString(toasterAttributes.getPowerOutput())));
                    attributes.add(new Attribute("hasTimer", "checkbox", "Наличие таймера", parseString(toasterAttributes.getHasTimer())));
                    attributes.add(new Attribute("hasDisplay", "checkbox", "Наличие дисплея", parseString(toasterAttributes.getHasDisplay())));
                }
                else attributes.addAll(getEmptyAttributesByCategory(category));

                break;
            default:
                throw new IllegalArgumentException("Unsupported category: " + category);
        }
        return attributes;
    }

    @Transactional
    public boolean saveProductWithAttributes(String category, Product product, Map<String, String> attributes) {
        product.setName(attributes.get("productName"));
            product.setBought(0);
            product.setBrand(attributes.get("productBrand"));
            product.setPrice(parseLong(attributes.get("productPrice")));
            product.setCategory(categoryDAO.getById(categoryDAO.getIdByName(category)));
            product.setQuantity(parseInteger(attributes.get("productQuantity")));
            product.setDescription(attributes.get("productDescription"));
            product.setImgPath("path/to/img");
            productDAO.save(product);
        switch (category) {
            case "phone":
                PhoneAttributes phoneAttributes = new PhoneAttributes();
                phoneAttributes.setProduct(product);
//                phoneAttributes.setId(product.getId());
                phoneAttributes.setScreenType(attributes.get("screenType"));
                phoneAttributes.setProcessor(attributes.get("processor"));
                phoneAttributes.setScreenDiagonal(parseDouble(attributes.get("screenDiagonal")));

                phoneAttributes.setMemorySize(parseInteger(attributes.get("memorySize")));
                phoneAttributes.setRamSize(parseInteger(attributes.get("ramSize")));
                if (phoneAttributesDAO.getById(product.getId()) != null) {
                    phoneAttributes.setId(product.getId());
                    phoneAttributesDAO.update(phoneAttributes);
                }
                else {
                    phoneAttributesDAO.save(phoneAttributes);
                }
                break;
            case "blender":
                BlenderAttributes blenderAttributes = new BlenderAttributes();
                blenderAttributes.setProduct(product);
//                blenderAttributes.setId(product.getId());
                blenderAttributes.setNumberOfSpeeds(parseInteger(attributes.get("numberOfSpeeds")));
                blenderAttributes.setCapacity(parseDouble(attributes.get("capacity")));
                blenderAttributes.setPowerOutput(parseInteger(attributes.get("powerOutput")));
                blenderAttributes.setHasTimer(parseBoolean(attributes.get("hasTimer")));
                blenderAttributes.setHasDisplay(parseBoolean(attributes.get("hasDisplay")));
                if (blenderAttributesDAO.getById(product.getId()) != null) {
                    blenderAttributes.setId(product.getId());
                    blenderAttributesDAO.update(blenderAttributes);
                }
                else {
                    blenderAttributesDAO.save(blenderAttributes);
                }
                break;
            case "toaster":
                ToasterAttributes toasterAttributes = new ToasterAttributes();
                toasterAttributes.setProduct(product);
//                toasterAttributes.setId(product.getId());
                toasterAttributes.setNumberOfSlots(parseInteger(attributes.get("numberOfSlots")));
                toasterAttributes.setHasBreadSensor(parseBoolean(attributes.get("hasBreadSensor")));
                toasterAttributes.setPowerOutput(parseInteger(attributes.get("powerOutput")));
                toasterAttributes.setHasTimer(parseBoolean(attributes.get("hasTimer")));
                toasterAttributes.setHasDisplay(parseBoolean(attributes.get("hasDisplay")));
                if (toasterAttributesDAO.getById(product.getId()) != null) {
                    toasterAttributes.setId(product.getId());
                    toasterAttributesDAO.update(toasterAttributes);
                }
                else {
                    toasterAttributesDAO.save(toasterAttributes);
                }
                break;
            default:
                return false;
        }
        return true;
    }

    private String parseString(Object value) {
        String res = String.valueOf(value);
        return Objects.equals(res, "null") ? "" : res;
    }

    private Integer parseInteger(String value) {
        return value != null && !value.isEmpty() ? Integer.parseInt(value) : null;
    }

    private Long parseLong(String value) {
        return value != null && !value.isEmpty() ? Long.parseLong(value) : null;
    }

    private Double parseDouble(String value) {
        return value != null && !value.isEmpty() ? Double.parseDouble(value) : null;
    }

    private Boolean parseBoolean(String value) {
        return value != null && !value.isEmpty() ? Boolean.parseBoolean(value) : null;
    }


    public List<Attribute> getUserAttributes(User user) {
        List<Attribute> attributes = new ArrayList<>();
//        attributes.add(new Attribute("userId", "text", "User ID", String.valueOf(user.getId())));
        attributes.add(new Attribute("userLogin", "text", "Login", user.getLogin()));
        attributes.add(new Attribute("userPassword", "Text", "Password", user.getPassword()));
        attributes.add(new Attribute("userLastName", "text", "Last Name", user.getLastName()));
        attributes.add(new Attribute("userFirstName", "text", "First Name", user.getFirstName()));
        attributes.add(new Attribute("userSurname", "text", "Surname", user.getSurname()));
        attributes.add(new Attribute("userBirthday", "date", "Birthday", user.getBirthday() != null ? user.getBirthday().toString() : ""));
        attributes.add(new Attribute("userPhone", "text", "Phone", user.getPhone()));
        attributes.add(new Attribute("userEmail", "email", "Email", user.getEmail()));
        attributes.add(new Attribute("userAddress", "text", "Address", user.getAddress()));
        attributes.add(new Attribute("userIsAdmin", "checkbox", "Is Admin", String.valueOf(user.isAdmin())));
        return attributes;
    }

    public List<Attribute> getEmptyUserAttributes() {
        List<Attribute> attributes = new ArrayList<>();
//        attributes.add(new Attribute("userId", "text", "User ID"));
        attributes.add(new Attribute("userLogin", "text", "Login"));
        attributes.add(new Attribute("userPassword", "password", "Password"));
        attributes.add(new Attribute("userLastName", "text", "Last Name"));
        attributes.add(new Attribute("userFirstName", "text", "First Name"));
        attributes.add(new Attribute("userSurname", "text", "Surname"));
        attributes.add(new Attribute("userBirthday", "date", "Birthday"));
        attributes.add(new Attribute("userPhone", "text", "Phone"));
        attributes.add(new Attribute("userEmail", "email", "Email"));
        attributes.add(new Attribute("userAddress", "text", "Address"));
        attributes.add(new Attribute("userIsAdmin", "checkbox", "Is Admin"));
        return attributes;
    }

    public List<Attribute> getUserAttributesForRegistration() {
        List<Attribute> attributes = new ArrayList<>();
//        attributes.add(new Attribute("userId", "text", "User ID"));
        attributes.add(new Attribute("userLogin", "text", "Login"));
        attributes.add(new Attribute("userLastName", "text", "Last Name"));
        attributes.add(new Attribute("userFirstName", "text", "First Name"));
        attributes.add(new Attribute("userSurname", "text", "Surname"));
        attributes.add(new Attribute("userBirthday", "date", "Birthday"));
        attributes.add(new Attribute("userPhone", "text", "Phone"));
        attributes.add(new Attribute("userEmail", "email", "Email"));
        attributes.add(new Attribute("userAddress", "text", "Address"));
        attributes.add(new Attribute("userIsAdmin", "checkbox", "Is Admin"));
        return attributes;
    }

    @Transactional
    public boolean saveUserWithAttributes(User user, Map<String, String> attributes) {
        if (user.getLogin() == null) {
            user.setLogin(attributes.get("userLogin"));
        }
            user.setPassword(attributes.get("userPassword"));
            user.setLastName(attributes.get("userLastName"));
            user.setFirstName(attributes.get("userFirstName"));
            user.setSurname(attributes.get("userSurname"));
            if (attributes.get("userBirthday") != null && !attributes.get("userBirthday").isEmpty()) {
                user.setBirthday(LocalDate.parse(attributes.get("userBirthday")));
            }
            user.setPhone(attributes.get("userPhone"));
            user.setEmail(attributes.get("userEmail"));
            user.setAddress(attributes.get("userAddress"));
            user.setAdmin(parseBoolean(attributes.get("userIsAdmin")));
            userDAO.save(user);
//        }
        return true;
    }

}
