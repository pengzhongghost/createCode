package template.service.impl;//package com.redu.fund.service.impl;
//
//import com.redu.common.result.PageResult;
//import com.redu.common.result.ServiceResult;
//import com.redu.fund.domain.ChannelAccountBillDomain;
//import com.redu.fund.param.ChannelAccountBillSearchParam;
//import com.redu.fund.service.ChannelAccountBillService;
//import com.redu.fund.vo.ChannelAccountBillVO;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//
///**
// * @author pengzhong
// * @since 2022/12/14
// */
//@Service
//public class ChannelAccountBillServiceImpl implements ChannelAccountBillService {
//
//    @Resource
//    private ChannelAccountBillDomain channelAccountBillDomain;
//
//    @Override
//    public ServiceResult<Boolean> insert(ChannelAccountBillVO channelAccountBill) {
//        return ServiceResult.success(channelAccountBillDomain.insert(channelAccountBill));
//    }
//
//    @Override
//    public ServiceResult<ChannelAccountBillVO> queryById(Long id) {
//        return ServiceResult.success(channelAccountBillDomain.selectById(id));
//    }
//
//    @Override
//    public ServiceResult<Boolean> updateById(ChannelAccountBillVO channelAccountBill) {
//        return ServiceResult.success(channelAccountBillDomain.updateById(channelAccountBill));
//    }
//
//    @Override
//    public ServiceResult<PageResult<ChannelAccountBillVO>> queryByParam(ChannelAccountBillSearchParam param) {
//        if (null == param.getStart()) {
//            param.setStart(0);
//        }
//        if (null == param.getLength()) {
//            param.setLength(20);
//        }
//        return ServiceResult.success(channelAccountBillDomain.queryByParam(param));
//    }
//
//}
