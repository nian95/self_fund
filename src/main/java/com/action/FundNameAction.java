package com.action;

import com.bean.FundNameBean;
import com.service.FundNameService;
import com.service.impl.FundNameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nian on 2017/10/3.
 */
@Controller
//@RequestMapping(value="/fundNameAction")
public class FundNameAction {
    private final static String fundNameUrl = "http://fund.eastmoney.com/js/fundcode_search.js";

    @Resource
    private FundNameService fundNameService;

    public List<FundNameBean> queryFundNameListFromWeb() {
        List<FundNameBean>  fundNameBeanList = new ArrayList<FundNameBean>();
        fundNameService = new FundNameServiceImpl();
        fundNameBeanList = fundNameService.queryFundNameListFromWeb(fundNameUrl);
        return fundNameBeanList;
    }


}
