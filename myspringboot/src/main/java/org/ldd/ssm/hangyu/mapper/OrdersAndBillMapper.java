package org.ldd.ssm.hangyu.mapper;

import org.ldd.ssm.hangyu.domain.OrdersAndBill;

public interface OrdersAndBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrdersAndBill record);

    int insertSelective(OrdersAndBill record);

    OrdersAndBill selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrdersAndBill record);

    int updateByPrimaryKey(OrdersAndBill record);
}