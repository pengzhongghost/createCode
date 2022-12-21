//package com.redu.fund.domain.impl;
//
//import cn.hutool.core.bean.BeanUtil;
//import com.google.common.collect.Lists;
//import com.redu.common.result.PageResult;
//import com.redu.fund.domain.ChannelAccountBillDomain;
//import com.redu.fund.mysql.entity.ChannelAccountBillEntity;
//import com.redu.fund.mysql.mapper.ChannelAccountBillMapper;
//import com.redu.fund.param.ChannelAccountBillSearchParam;
//import com.redu.fund.vo.ChannelAccountBillVO;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeanUtils;
//import org.springframework.stereotype.Repository;
//import org.springframework.util.CollectionUtils;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author pengzhong
// * @since 2022/12/14
// */
//@Slf4j
//@Repository
//public class ChannelAccountBillDomainImpl implements ChannelAccountBillDomain {
//
//    @Resource
//    private ChannelAccountBillMapper channelAccountBillMapper;
//
//    @Override
//    public boolean insert(ChannelAccountBillVO channelAccountBill) {
//        if (null == channelAccountBill) {
//            return true;
//        }
//        ChannelAccountBillEntity entity = new ChannelAccountBillEntity();
//        BeanUtils.copyProperties(channelAccountBill, entity);
//        return channelAccountBillMapper.insert(entity) > 0;
//    }
//
//    @Override
//    public ChannelAccountBillVO selectById(Long id) {
//        ChannelAccountBillEntity entity;
//        if (null == (entity = channelAccountBillMapper.selectById(id))) {
//            return null;
//        }
//        ChannelAccountBillVO channelAccountBill = new ChannelAccountBillVO();
//        BeanUtils.copyProperties(entity, channelAccountBill);
//        return channelAccountBill;
//    }
//
//    @Override
//    public boolean updateById(ChannelAccountBillVO channelAccountBill) {
//        if (null == channelAccountBill) {
//            return true;
//        }
//        ChannelAccountBillEntity entity = new ChannelAccountBillEntity();
//        BeanUtils.copyProperties(channelAccountBill, entity);
//        return channelAccountBillMapper.updateById(entity) > 0;
//    }
//
//    @Override
//    public PageResult<ChannelAccountBillVO> queryByParam(ChannelAccountBillSearchParam param) {
//        //1.总页数
//        int total = channelAccountBillMapper.countByParam(param);
//        //2.返回结果
//        List<ChannelAccountBillVO> channelAccountBills = new ArrayList<>();
//        if (total > 0) {
//            channelAccountBills = BeanUtil.copyToList(channelAccountBillMapper.selectByParam(param), ChannelAccountBillVO.class);
//        }
//        return new PageResult<>(channelAccountBills, param.getStart(), param.getLength(), total);
//    }
//
//    @Override
//    public List<ChannelAccountBillVO> queryByIds(List<Long> ids) {
//        if (CollectionUtils.isEmpty(ids)) {
//            return Lists.newArrayList();
//        }
//        return BeanUtil.copyToList(channelAccountBillMapper.selectByIds(ids), ChannelAccountBillVO.class);
//    }
//
//}
