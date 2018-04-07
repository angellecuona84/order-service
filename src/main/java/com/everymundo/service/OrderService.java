package com.everymundo.service;

import com.everymundo.domain.Order;
import com.everymundo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@link OrderService} class
 *
 * @author Angel Lecuona
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * Save an order related with customerId
     *
     * @param order      to save
     * @param customerId to represent the Customer related with the order
     */
    @CacheEvict(value = "allOrder-cache", key = "#customerId")
    public void save(Order order, String customerId) {
        order.setCustomerId(customerId);
        orderRepository.save(order);
    }

    /**
     * Remove and order by Id
     *
     * @param id of of Order
     */
    @Caching(evict = {
            @CacheEvict(value = "order-single", key = "#id"),
            @CacheEvict(value = "allOrder-cache")
    })
    public void delete(String id) {
        orderRepository.delete(id);
    }

    /**
     * Remove all the order related with a costumer Id
     *
     * @param customerId
     */

    @Caching(evict = {
            @CacheEvict("order-single"),
            @CacheEvict(value = "allOrder-cache", key = "#customerId")
    })
    public void deleteByCustomerId(String customerId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("customerId").is(customerId));
        mongoTemplate.findAllAndRemove(query, Order.class, "order");
    }

    /**
     * Get an Order by Id
     *
     * @param id of Order
     * @return and Order with specific Id
     */
    @Cacheable(value = "order-single", key = "#id")
    public Order findById(String id) {
        return orderRepository.findOne(id);
    }

    /**
     * Get all the Orders related with specific Id
     *
     * @param customerId
     * @return all the Orders related with specific Id
     */
    @Cacheable(value = "allOrder-cache", key = "#customerId")
    public List<Order> findAllByCustomerId(String customerId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("customerId").is(customerId));
        List<Order> result = mongoTemplate.find(query, Order.class, "order");
        return result;
    }

    /**
     * Update an Order with specific Id and related with a Customer with specific Id
     *
     * @param order      to update
     * @param customerId related to Order
     * @param orderId    to update the Order
     */
    @CachePut(value = "order-single", key = "#order.orderId")
    public void update(Order order, String customerId, String orderId) {
        if (orderId != null && customerId != null && customerId.equals(order.getCustomerId())) {
            order.setCustomerId(customerId);
        }
        orderRepository.save(order);
    }

    public List<Order> findAllByCustomerIds(List<String> ids) {
        Query query = new Query();
        query.addCriteria(Criteria.where("customerId").in(ids))
                .with(new Sort(Sort.Direction.DESC, "createAt"));
        List<Order> result = mongoTemplate.find(query, Order.class, "order");
        return result;
    }
}
