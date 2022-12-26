//package template.controller;
//
//import com.redu.common.result.PageResult;
//import com.redu.common.result.ServiceResult;
//import com.redu.fund.param.ChannelAccountBillSearchParam;
//import com.redu.fund.service.ChannelAccountBillService;
//import com.redu.fund.vo.ChannelAccountBillVO;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//
///**
// * @author pengzhong
// * @since 2022/12/14
// */
//@Api(tags = "渠道账户账单模块")
//@Slf4j
//@RestController
//@RequestMapping("web/channel/account/bill")
//public class ChannelAccountBillController {
//
//    @Resource
//    private ChannelAccountBillService channelAccountBillService;
//
//    @ApiOperation(value = "新增")
//    @PostMapping("/insert")
//    public ServiceResult<Boolean> insert(@RequestBody ChannelAccountBillVO channelAccountBill) {
//        return channelAccountBillService.insert(channelAccountBill);
//    }
//
//    @ApiOperation(value = "根据id修改")
//    @PostMapping("/updateById")
//    public ServiceResult<Boolean> updateById(@RequestBody ChannelAccountBillVO channelAccountBill) {
//        return channelAccountBillService.updateById(channelAccountBill);
//    }
//
//    @ApiOperation(value = "根据id查询")
//    @GetMapping("/queryById")
//    public ServiceResult<ChannelAccountBillVO> queryById(@RequestParam("id") Long id) {
//        return channelAccountBillService.queryById(id);
//    }
//
//    @ApiOperation(value = "查询列表")
//    @PostMapping("/queryByParam")
//    public ServiceResult<PageResult<ChannelAccountBillVO>> queryByParam(@RequestBody ChannelAccountBillSearchParam param) {
//        return channelAccountBillService.queryByParam(param);
//    }
//
//}
