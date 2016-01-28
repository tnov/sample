package jp.dip.fission;

import java.io.IOException;
import java.sql.SQLException;

import jp.dip.fission.database.DBConnection;
import jp.dip.fission.database.DBException;
import jp.dip.fission.database.DBFactory;

public class DBConnectionTest {

	public static void main(String[] args) {
		// DBManager生成(デフォルト組み込みDB接続)
		DBFactory manager = null;
		try {
			manager = DBFactory.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//
		DBConnection connection = null;
		try {
			connection = manager.getH2DBConnection("test");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DBException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// DB削除(組み込み:ファイル削除、Server:DropDatabase)
		// テーブル生成
		// データロード
		// トランザクション開始
		// CRUD実行
		// トランザクション終了
		// 切断
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//DBの起動
		//コネクションのオープン
		//database作成、削除
		//table作成、削除
		//index作成、削除
		//CRUDクエリの実行
		//コネクションのクローズ
		//DBの終了
	}
}
