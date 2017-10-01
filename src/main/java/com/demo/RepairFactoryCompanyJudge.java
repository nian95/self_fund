package com.demo;

public class RepairFactoryCompanyJudge {

	/**
	 * @param  companyCode       ��������
	 * @param  repairFactoryId   ����Id
	 * @return �Ƿ����Ե����
	 */
	public boolean isDInterfaceCompany(String companyCode , String repairFactoryId) {
		
		boolean isCompany = false;
		if(companyCode == null ) {
			// todo ����repairFactoryId ����D23�ӿڣ��ӷ��ر����л�ȡcompanyCode��Ȼ����companyCode���Ե㿪�ر��еĻ����������ȶԡ�
		} else {
			// todo ��companyCode���Ե㿪�ر��еĻ����������ȶԡ�
		}
		return isCompany;
	}
	
	/**
	 * @param  isInterface       ���ýӿڱ�־
	 * @return �Ƿ����Ե����
	 */
	public boolean isWInterfaceCompany(String isInterface) {
		
		boolean isCompany = false;
		if(isInterface != null && "1".equals(isInterface) ) {
			isCompany = true;
		}
		return isCompany;
	}
}
