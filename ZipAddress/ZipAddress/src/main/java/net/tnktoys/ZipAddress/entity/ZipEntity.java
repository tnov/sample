package net.tnktoys.ZipAddress.entity;

public class ZipEntity {
	// 01 全国地方公共団体コード（JIS X0401、X0402）………　半角数字
	public String codeLocalGovernment;
	// 02 （旧）郵便番号（5桁）………………………………………　半角数字
	public String codeOldZip;
	// 03 郵便番号（7桁）………………………………………　半角数字
	public String codeZip;
	// 04 都道府県名　…………　半角カタカナ（コード順に掲載）　（注1）
	public String namePrefecturesKana;
	// 05 市区町村名　…………　半角カタカナ（コード順に掲載）　（注1）
	public String nameCityKana;
	// 06 町域名　………………　半角カタカナ（五十音順に掲載）　（注1）
	public String nameTownAreaKana;
	// 07 都道府県名　…………　漢字（コード順に掲載）　（注1,2）
	public String namePrefecturesKanji;
	// 08 市区町村名　…………　漢字（コード順に掲載）　（注1,2）
	public String cameCityCodeKanji;
	// 09 町域名　………………　漢字（五十音順に掲載）　（注1,2）
	public String nameTownAreaKanji;
	// 10 一町域が二以上の郵便番号で表される場合の表示　（注3）　（「1」は該当、「0」は該当せず）
	public String flgMultipleZipCode;
	// 11 小字毎に番地が起番されている町域の表示　（注4）　（「1」は該当、「0」は該当せず）
	public String flgSublocality;
	// 12 丁目を有する町域の場合の表示　（「1」は該当、「0」は該当せず）
	public String flgChome;
	// 13 一つの郵便番号で二以上の町域を表す場合の表示　（注5）　（「1」は該当、「0」は該当せず）
	public String flgMultipleTownArea;
	// 14 更新の表示（注6）（「0」は変更なし、「1」は変更あり、「2」廃止（廃止データのみ使用））
	public String flgUpdate;
	// 15 変更理由　（「0」は変更なし、「1」市政・区政・町政・分区・政令指定都市施行、「2」住居表示の実施、「3」区画整理、「4」郵便区調整等、「5」訂正、「6」廃止（廃止データのみ使用））
	public String flgUpdateReason;
}
