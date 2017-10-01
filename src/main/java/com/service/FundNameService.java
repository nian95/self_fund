package com.service;

import java.util.List;

import com.bean.FundNameBean;

public interface FundNameService {

	/**
	 * 从网上查询所有基金名称列表
	 * @param webUrl 请求地址
	 * @return 所有基金名称列表
	 *
	 */
	List<FundNameBean> queryFundNameListFromWeb(String webUrl);

	/**
	 * 将所有基金名称列表保存到数据库
	 * @param fundNameList 请求地址
	 *
	 */
	void saveFundNameListToDB(List<FundNameBean> fundNameList);

	/**
	 * 从数据库查询所有基金名称列表
	 * @return 所有基金名称列表
	 *
	 */
	List<FundNameBean>  queryFundNameListFromDB(int page , int pageSize);

	/**
	 * 从数据库查询基金名称
	 * @param fCode 基金code
	 * @return 基金名称
	 *
	 */
	FundNameBean queryFundNameFromDB(String fCode);

}
