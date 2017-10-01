package com.service;

import java.util.List;

import com.bean.FundNameBean;
import com.bean.FundUnitNetValueBean;

public interface FundUnitNetValueService {

	/**
	 * 删除基金单位净值表
	 * @param fCode 基金Code
	 */
	void dropFundUnitNetValueTable(String fCode);

	/**
	 * 创建基金单位净值表
	 * @param fCode 基金Code
	 */
	void createFundUnitNetValueTable(String fCode);

	/**
	 * 创建基金单位净值表
	 * @param fundNameList 基金名称列表
	 */
	void createFundUnitNetValueTables(List<FundNameBean> fundNameList);

	/**
	 * 从网上查询基金单位净值
	 * @param webUrl 网上基金地址
	 * @param fCode 基金Code
	 * @return 单位净值
	 */
	List<FundUnitNetValueBean> queryFundUnitNetValueFromWeb(String webUrl , String fCode);

	/**
	 * 保存单位净值到数据库
	 * @param fundUnitNetValueList 单位净值
	 */
	void saveFundUnitNetValueToDB(List<FundUnitNetValueBean> fundUnitNetValueList);

	/**
	 *
	 * 从数据库查询基金单位净值
	 */
	List<FundUnitNetValueBean> queryFundUnitNetValueFromDB(String webUrl , String fCode);

}
