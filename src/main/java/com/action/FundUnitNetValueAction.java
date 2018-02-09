package com.action;

import com.bean.FundUnitNetValueBean;
import com.service.FundUnitNetValueService;
import com.service.impl.FundUnitNetValueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nian on 2017/10/4.
 */
@Controller
public class FundUnitNetValueAction {

    private static final String webUrl = "http://fund.eastmoney.com/pingzhongdata/";

//    @Autowired
//    private FundUnitNetValueService fundUnitNetValueService ;

    public List<FundUnitNetValueBean> queryFundUnitNetValueFromWeb(
            String webUrl, String fCode) {
        List<FundUnitNetValueBean>  netValueList = new ArrayList<FundUnitNetValueBean>();
//        netValueList = fundUnitNetValueService.queryFundUnitNetValueFromWeb(webUrl , fCode);
        FundUnitNetValueServiceImpl fundUnitNetValueServiceImpl = new FundUnitNetValueServiceImpl();
        netValueList = fundUnitNetValueServiceImpl.queryFundUnitNetValueFromWeb(webUrl , fCode);
        return netValueList;
    }


}
