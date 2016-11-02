package com.jackpan.taiwamrain;

public class ResultData implements Comparable<ResultData>{
 
//	String 講習類別;
//	String 講習開始日期;
//	String 講習結束日期;
//	String 講習時數;
//	String 參加對象;
//	String 招收人數;
//	String 報名方式;
//	String 講習地點;
//	String 報名類別;
//	String 報名開始日期;
//	String 報名結束日期;
//	
//	String 報名地點;
//	String 承辦單位;
//	String 承辦人;
//	String 連絡電話;
//	String 承辦人Email;
//	String 核備單位;
//	String 核備文號;
//	String 核備日期;
//	String 備註說明;
//	
//	String 會員講習費用;
//	String 非會員講習費用;
//	String 課程名稱;
//	String 講師姓名;
//	String 上課時間;
	
	String CHR_ID;
	String CHR_Area;
	String CHR_Name;
	String CHR_Charge;
	String CHR_Address;
	String CHR_Phone;
	String CHR_BuildTime;

//	
//	String AVAILABLECAR;
//	
	int KmList;
	@Override
	public int compareTo(ResultData another) {
		// TODO Auto-generated method stub
		return this.KmList -another.KmList;
	}
//	
//	public boolean Type(){
//		
//		if(this.TYPE.equals("1"))return true;
//		else return false;
//		
//		
//	}

}
