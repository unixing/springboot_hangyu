package org.ldd.ssm.hangyu.mapper;

import org.ldd.ssm.hangyu.domain.TradeNegotiation;

public interface TradeNegotiationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TradeNegotiation record);

    int insertSelective(TradeNegotiation record);

    TradeNegotiation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TradeNegotiation record);

    int updateByPrimaryKey(TradeNegotiation record);
}