package com.roseny.logisticscrm.services;

import com.roseny.logisticscrm.dtos.requests.CreateOrderRequest;
import com.roseny.logisticscrm.models.Order;
import com.roseny.logisticscrm.models.Product;
import com.roseny.logisticscrm.models.User;
import com.roseny.logisticscrm.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;

    public void save(Order order) {
        orderRepository.save(order);
    }

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
}
