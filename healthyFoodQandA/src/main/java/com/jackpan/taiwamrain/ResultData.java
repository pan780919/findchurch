package com.jackpan.taiwamrain;
//
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

	String 縣市 ;// "新竹市",
	String 字號;// "竹市府產農字第980078687號",
	String 執照類別;// "獸醫師",
	String 狀態;// "開業",
	String 機構名稱;// "全美忠孝動物醫院",
	String 負責獸醫;// "林孟潔",
	String 機構電話;// "03-5620869",
	String 發照日期;// "2015/09/10",
	String 機構地址;//"新竹市東光路177號"
//	String CHR_ID;
//	String CHR_Area;
//	String CHR_Name;
//	String CHR_Charge;
//	String CHR_Address;
//	String CHR_Phone;
//	String CHR_BuildTime;
//	String reason;//標題
//	String range; //停水範圍
//	String stop_s; //停水期間
//	String restore_s; //恢復供水
//	String bulletin_on; //發佈期間
//	String bulletin_off;
//	String openarea;//發佈區域

//	String	CB_NAME;// "祥光寺",
//	String CB_SNAME;// "祥光寺",
//	String PTNAME1;// "文山區",
//	String V_NAME;// "政大里",
//	String CB_REG_TITLE;// "佛教",
//	String PRESIDENT_NAME;//"高榮華",
//	String CB_TEL;// "29390543",
//	String TOTAL_ADDR;//萬壽路63號",
//	String R_STREET;//萬壽路",
//	String R_LANE;//
//	String R_ALLEY;//
//	String R_NO ;// "63號",

	String TM97_X;// "309453.733",
	String TM97_Y;// "2764617.394",
	String DATA_STR;//"農曆2月19日(觀世音菩薩誕辰)|農曆6月19日|農曆9月18日|農曆9月19日|農曆9月20日|",

//	
//	String AVAILABLECAR;
////
	int KmList;
	@Override
	public int compareTo(ResultData another) {
		// TODO Auto-generated method stub
		return this.KmList -another.KmList;
	}
////
//	public boolean Type(){
//		
//		if(this.TYPE.equals("1"))return true;
//		else return false;
//		
//		
//	}

}
