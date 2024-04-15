package com.example.demo.DAO;

import com.example.demo.models.ToasterAttributes;

import java.util.List;

public interface ToasterAttributesDAO extends CommonDAO<ToasterAttributes, Long> {
    List<ToasterAttributes> findByPowerOutputGreaterThanEqual(Integer powerOutput);

    List<ToasterAttributes> findByPowerOutputLessThanEqual(Integer powerOutput);

    List<ToasterAttributes> findByNumberOfSlotsGreaterThanEqual(Integer numberOfSlots);

    List<ToasterAttributes> findByNumberOfSlotsLessThanEqual(Integer numberOfSlots);

    List<ToasterAttributes> findByHasDisplay(Boolean hasDisplay);

    List<ToasterAttributes> findByHasTimer(Boolean hasTimer);

    List<ToasterAttributes> findByHasBreadSensor(Boolean hasBreadSensor);

    List<ToasterAttributes> findByProductBrand(String brand);
}