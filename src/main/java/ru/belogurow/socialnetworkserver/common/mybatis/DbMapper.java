package ru.belogurow.socialnetworkserver.common.mybatis;

import java.util.List;
import java.util.UUID;

/**
 * @author alexbelogurow
 */
public interface DbMapper<T>  {

     /**
      * Insert new object T in the DB
      * @param t - The object
      * @return Result of operation
      */
     boolean insert(T t);

     /**
      * Update existing object T in the DB
      * @param t - The object
      * @return Result of operation
      */
     boolean update(T t);

     /**
      * Find object T by id
      * @param id - The id of object
      * @return Found object
      */
     T findById(UUID id);

     /**
      * Find all objects
      * @return List of objects
      */
     List<T> findAll();

     /**
      * Delete object T from the DB
      * @param t - The object
      * @return Result of operation
      */
     boolean delete(T t);

     /**
      * Delete all object from the DB
      * @return Result of operation
      */
     boolean deleteAll();

}
