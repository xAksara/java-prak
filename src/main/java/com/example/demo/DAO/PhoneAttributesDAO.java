package com.example.demo.DAO;

import com.example.demo.models.PhoneAttributes;

import java.util.List;

public interface PhoneAttributesDAO extends CommonDAO<PhoneAttributes, Long> {
    List<PhoneAttributes> findByScreenDiagonalGreaterThanEqual(Double screenDiagonal);
    List<PhoneAttributes> findByScreenDiagonalLessThanEqual(Double screenDiagonal);
    List<PhoneAttributes> findByProcessor(String processor);
    List<PhoneAttributes> findByMemorySizeGreaterThanEqual(Integer memorySize);
    List<PhoneAttributes> findByMemorySizeLessThanEqual(Integer memorySize);
    List<PhoneAttributes> findByRamSizeGreaterThanEqual(Integer ramSize);
    List<PhoneAttributes> findByRamSizeLessThanEqual(Integer ramSize);
    List<PhoneAttributes> findByScreenType(String screenType);
    List<PhoneAttributes> findByProductBrand(String brand);
}