package ru.belogurow.socialnetworkserver.common.mybatis;

import java.io.Serializable;
import java.util.List;

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
     T findById(Serializable id);

     /**
      * Find all objects
      * @return List of objects
      */
     List<T> findAll();

     /**
      * Delete object T from the DB
      * @param t - The object
      */
     boolean delete(T t);

     /**
      * Delete all object from the DB
      */
     boolean deleteAll();

}
