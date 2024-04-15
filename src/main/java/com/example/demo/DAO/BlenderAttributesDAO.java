package com.example.demo.DAO;

import com.example.demo.models.BlenderAttributes;

import java.util.List;

public interface BlenderAttributesDAO extends CommonDAO<BlenderAttributes, Long>{
    List<BlenderAttributes> findByCapacityGreaterThanEqual(Double capacity);
    List<BlenderAttributes> findByCapacityLessThanEqual(Double capacity);
    List<BlenderAttributes> findByHasTimer(Boolean hasTimer);
    List<BlenderAttributes> findByPowerOutputGreaterThanEqual(Integer powerOutput);
    List<BlenderAttributes> findByPowerOutputLessThanEqual(Integer powerOutput);
    List<BlenderAttributes> findByNumberOfSpeedsGreaterThanEqual(Integer numberOfSpeeds);
    List<BlenderAttributes> findByNumberOfSpeedsLessThanEqual(Integer numberOfSpeeds);
    List<BlenderAttributes> findByHasDisplay(Boolean hasDisplay);
    List<BlenderAttributes> findByProductBrand(String brand);
}
