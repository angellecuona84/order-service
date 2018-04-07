package com.everymundo.repository;

import com.everymundo.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * {@link OrderRepository} class
 *  DAO Representation to access MongoDB
 *
 * @author Angel Lecuona
 *
 */
@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

}
