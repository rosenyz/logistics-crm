package com.roseny.logisticscrm.services;

import com.roseny.logisticscrm.dtos.requests.CreateOrderRequest;
import com.roseny.logisticscrm.models.Order;
import com.roseny.logisticscrm.models.Product;
import com.roseny.logisticscrm.models.User;
import com.roseny.logisticscrm.models.enums.Role;
import com.roseny.logisticscrm.models.enums.StatusOrder;
import com.roseny.logisticscrm.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;

    public ResponseEntity<?> createOrder(CreateOrderRequest createOrderRequest, Principal principal) {
        if (principal == null) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Something went wrong..."); }

        User user = userService.findUserByPrincipal(principal);
        Order order = new Order();

        List<Long> productsId = createOrderRequest.getProductsId();
        if (!productsId.isEmpty()) {
            List<Product> products = new ArrayList<>();
            Double productsPrice = 0.0;

            for (Long id : productsId) {
                Product product = productService.getProductById(id);

                if (product != null) {
                    productsPrice += product.getPrice();
                    products.add(product);
                }
            }

            order.setProductsPrice(productsPrice);
            order.setTotalPrice(productsPrice);
            order.setProducts(products);
        }
        else {
            order.setProducts(null);
            order.setProductsPrice(0.0);
            order.setTotalPrice(0.0);
        }

        order.setCustomerId(user.getId());

        if ( createOrderRequest.getCommentary() == null ) { order.setCommentary("Комментарий отсутствует."); }
        else { order.setCommentary(createOrderRequest.getCommentary()); }

        order.setCustomerContact(createOrderRequest.getCustomerContact());

        if (createOrderRequest.getAddressForDelivery() == null ) { order.setAddressForDelivery(user.getAddress()); }
        else { order.setAddressForDelivery(createOrderRequest.getAddressForDelivery()); }

        user.getOrders().add(order);

        userService.save(user);
        orderRepository.save(order);

        return ResponseEntity.ok(order);
    }

    public ResponseEntity<?> getUserOrders(Principal principal) {
        if (principal == null) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"); }

        User user = userService.findUserByPrincipal(principal);
        List<Order> orders = orderRepository.findOrdersByCustomerId(user.getId());

        if (orders.isEmpty()) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Вы ничего не заказывали."); }

        return ResponseEntity.ok(orders);
    }

    public ResponseEntity<?> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        if (orders.isEmpty()) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body("В данный момент заказов нет."); }

        return ResponseEntity.ok(orders);
    }

    public ResponseEntity<?> getOrderById(Long orderId, Principal principal) {
        if (principal == null) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"); }

        Order order = orderRepository.findById(orderId).orElse(null);
        User user = userService.findUserByPrincipal(principal);

        if (order == null) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Такого заказа не существует."); }

        if (!(order.getCustomerId().equals(user.getId())
                || user.getRoles().contains(Role.ROLE_SUPPORT)
                || user.getRoles().contains(Role.ROLE_ADMIN))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Нет доступа!");
        }

        return ResponseEntity.ok(order);
    }

    public ResponseEntity<?> changeOrderStatus(Integer action, Long orderId, Principal principal) {
        if (principal == null) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"); }

        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Такого заказа не существует!"); }

        Map<Integer, StatusOrder> actions = new HashMap<>();
        actions.put(1, StatusOrder.STATUS_ROUTE_TO_STOCK);
        actions.put(2, StatusOrder.STATUS_WAITING_TO_PAY);
        actions.put(3, StatusOrder.STATUS_PAID);
        actions.put(4, StatusOrder.STATUS_ROUTE_TO_STOCK_IN_COUNTRY);
        actions.put(5, StatusOrder.STATUS_ROUTE_TO_DPOINT);
        actions.put(6, StatusOrder.STATUS_DONE);

        if (order.getStatus() == actions.get(action)) {
            return ResponseEntity.ok(order);
        }

        order.setStatus(actions.get(action));
        orderRepository.save(order);

        return ResponseEntity.ok(order);
    }
}
