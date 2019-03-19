package net.tnk_toys.ZipCodeLib.entity;

public class ZipEntity {
	
	private static final String REPLACE_WORD_1 = "以下に掲載がない場合";
	private static final String REPLACE_WORD_1_KANA = "ｲｶﾆｹｲｻｲｶﾞﾅｲﾊﾞｱｲ";
	private static final String REPLACE_WORD_2 = "の次に番地がくる場合";
	private static final String REPLACE_WORD_2_KANA = "ﾉﾂｷﾞﾆﾊﾞﾝﾁｶﾞｸﾙﾊﾞｱｲ";
	private static final String REPLACE_WORD_3 = "（次のビルを除く）";
	private static final String REPLACE_WORD_3_KANA = "(ﾂｷﾞﾉﾋﾞﾙｦﾉｿﾞｸ)";
	
	
	// 01 全国地方公共団体コード（JIS X0401、X0402）………　半角数字
	private String codeLocalGovernment;
	// 02 （旧）郵便番号（5桁）………………………………………　半角数字
	private String codeOldZip;
	// 03 郵便番号（7桁）………………………………………　半角数字
	private String codeZip;
	// 04 都道府県名　…………　半角カタカナ（コード順に掲載）　（注1）
	private String namePrefecturesKana;
	// 05 市区町村名　…………　半角カタカナ（コード順に掲載）　（注1）
	private String nameCityKana;
	// 06 町域名　………………　半角カタカナ（五十音順に掲載）　（注1）
	private String nameTownAreaKana;
	// 07 都道府県名　…………　漢字（コード順に掲載）　（注1,2）
	private String namePrefecturesKanji;
	// 08 市区町村名　…………　漢字（コード順に掲載）　（注1,2）
	private String nameCityKanji;
	// 09 町域名　………………　漢字（五十音順に掲載）　（注1,2）
	private String nameTownAreaKanji;
	// 10 一町域が二以上の郵便番号で表される場合の表示　（注3）　（「1」は該当、「0」は該当せず）
	private String flgMultipleZipCode;
	// 11 小字毎に番地が起番されている町域の表示　（注4）　（「1」は該当、「0」は該当せず）
	private String flgSublocality;
	// 12 丁目を有する町域の場合の表示　（「1」は該当、「0」は該当せず）
	private String flgChome;
	// 13 一つの郵便番号で二以上の町域を表す場合の表示　（注5）　（「1」は該当、「0」は該当せず）
	private String flgMultipleTownArea;
	// 14 更新の表示（注6）（「0」は変更なし、「1」は変更あり、「2」廃止（廃止データのみ使用））
	private String flgUpdate;
	// 15 変更理由　（「0」は変更なし、「1」市政・区政・町政・分区・政令指定都市施行、「2」住居表示の実施、「3」区画整理、「4」郵便区調整等、「5」訂正、「6」廃止（廃止データのみ使用））
	private String flgUpdateReason;
	
	
	public String getCodeLocalGovernment() {
		return codeLocalGovernment;
	}
	public void setCodeLocalGovernment(String codeLocalGovernment) {
		this.codeLocalGovernment = codeLocalGovernment;
	}
	public String getCodeOldZip() {
		return codeOldZip;
	}
	public void setCodeOldZip(String codeOldZip) {
		this.codeOldZip = codeOldZip;
	}
	public String getCodeZip() {
		return codeZip;
	}
	public void setCodeZip(String codeZip) {
		this.codeZip = codeZip;
	}
	public String getNamePrefecturesKana() {
		return namePrefecturesKana;
	}
	public void setNamePrefecturesKana(String namePrefecturesKana) {
		this.namePrefecturesKana = namePrefecturesKana;
	}
	public String getNameCityKana() {
		return nameCityKana;
	}
	public void setNameCityKana(String nameCityKana) {
		this.nameCityKana = nameCityKana;
	}
	public String getNameTownAreaKana() {
		if(nameTownAreaKana.contains(REPLACE_WORD_1_KANA)
				|| nameTownAreaKana.contains(REPLACE_WORD_2_KANA)) {
			return "";
		} else if (nameTownAreaKana.contains(REPLACE_WORD_3_KANA)) {
			return nameTownAreaKana.replace(REPLACE_WORD_3_KANA, "");
		} else {
			return nameTownAreaKana;
		}
	}
	public void setNameTownAreaKana(String nameTownAreaKana) {
		this.nameTownAreaKana = nameTownAreaKana;
	}
	public String getNamePrefecturesKanji() {
		return namePrefecturesKanji;
	}
	public void setNamePrefecturesKanji(String namePrefecturesKanji) {
		this.namePrefecturesKanji = namePrefecturesKanji;
	}
	public String getNameCityKanji() {
		return nameCityKanji;
	}
	public void setNameCityKanji(String nameCityKanji) {
		this.nameCityKanji = nameCityKanji;
	}
	public String getNameTownAreaKanji() {
		if(nameTownAreaKanji.contains(REPLACE_WORD_1)
				|| nameTownAreaKanji.contains(REPLACE_WORD_2)) {
			return "";
		} else if (nameTownAreaKanji.contains(REPLACE_WORD_3)) {
			return nameTownAreaKanji.replace(REPLACE_WORD_3, "");
		} else {
			return nameTownAreaKanji;
		}
	}
	public void setNameTownAreaKanji(String nameTownAreaKanji) {
		this.nameTownAreaKanji = nameTownAreaKanji;
	}
	public String getFlgMultipleZipCode() {
		return flgMultipleZipCode;
	}
	public void setFlgMultipleZipCode(String flgMultipleZipCode) {
		this.flgMultipleZipCode = flgMultipleZipCode;
	}
	public String getFlgSublocality() {
		return flgSublocality;
	}
	public void setFlgSublocality(String flgSublocality) {
		this.flgSublocality = flgSublocality;
	}
	public String getFlgChome() {
		return flgChome;
	}
	public void setFlgChome(String flgChome) {
		this.flgChome = flgChome;
	}
	public String getFlgMultipleTownArea() {
		return flgMultipleTownArea;
	}
	public void setFlgMultipleTownArea(String flgMultipleTownArea) {
		this.flgMultipleTownArea = flgMultipleTownArea;
	}
	public String getFlgUpdate() {
		return flgUpdate;
	}
	public void setFlgUpdate(String flgUpdate) {
		this.flgUpdate = flgUpdate;
	}
	public String getFlgUpdateReason() {
		return flgUpdateReason;
	}
	public void setFlgUpdateReason(String flgUpdateReason) {
		this.flgUpdateReason = flgUpdateReason;
	}
}
