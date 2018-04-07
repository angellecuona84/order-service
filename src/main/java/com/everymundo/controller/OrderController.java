package com.everymundo.controller;

import com.everymundo.domain.Order;
import com.everymundo.service.OrderService;
import com.everymundo.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * {@link OrderController} class
 *
 * @author Angel Lecuona
 *
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Save the Order
     *
     * @param request {@link HttpServletRequest}
     * @param order to save
     * @param customerId related with the Order
     * @return a wrapper to {@link ResponseEntity}
     */
    @PostMapping("/customers/{customerId}/orders")
    public ResponseEntity<RestResponse> save(HttpServletRequest request, @RequestBody Order order,
                                             @PathVariable String customerId) {
        try{
            orderService.save(order, customerId);
        }
        catch(Exception e){
            return new ResponseEntity<>(
                    new RestResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        HttpHeaders header = new HttpHeaders();
        header.set(HttpHeaders.LOCATION, request.getRequestURL().toString());

        ResponseEntity<RestResponse> responseEntity = new ResponseEntity<>(
                new RestResponse(HttpStatus.CREATED.value(), "Order Resource Created"),header,
                HttpStatus.CREATED);


        return responseEntity;
    }

    /**
     * Update an specific Order
     *
     * @param request {@link HttpServletRequest}
     * @param order to update
     * @param customerId
     * @param orderId
     * @return a wrapper to {@link ResponseEntity}
     */
    @PutMapping("/customers/{customerId}/orders/{orderId}")
    public ResponseEntity<RestResponse> update(HttpServletRequest request,
                                               @RequestBody Order order, @PathVariable String customerId,
                                               @PathVariable String orderId) {
        try{
            orderService.update(order, customerId, orderId);
        }
        catch(Exception e){
            return new ResponseEntity<>(
                    new RestResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        HttpHeaders header = new HttpHeaders();
        header.set(HttpHeaders.LOCATION, request.getRequestURL().toString());

        ResponseEntity<RestResponse> responseEntity = new ResponseEntity<>(
                new RestResponse(HttpStatus.OK.value(), "Order Resource Updated"),header,
                HttpStatus.OK
        );

        return responseEntity;
    }

    /**
     * Remove an Order by Id
     *
     * @param id of Order
     */
    @DeleteMapping("/customers/{customerId}/orders/{id}")
    public void delete(@PathVariable String id) {
        orderService.delete(id);
    }

    /**
     * Get all the Orders related with a Costumer by customerId
     *
     * @param costumerId
     * @return list of Orders related with a Costumer by customerId
     */
    @GetMapping("/customers/{costumerId}/orders")
    public List<Order> findAllOrder(@PathVariable String costumerId) {
        return orderService.findAllByCustomerId(costumerId);
    }

    /**
     * Get Order by specific Id and related wit a Costumer by id
     *
     * @param id
     * @param costumerId
     * @return and Order by specific Id and related wit a Costumer by id
     */
    @GetMapping("/customers/{costumerId}/orders/{id}")
    public Order findByOrderId(@PathVariable String id, @PathVariable String costumerId) {
        return orderService.findById(id);
    }

    @PostMapping("/customers/orders")
    public List<Order> findAllByCustomerIds(@RequestBody List<String> ids){
        return orderService.findAllByCustomerIds(ids);
    }


}
