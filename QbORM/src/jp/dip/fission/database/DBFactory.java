package jp.dip.fission.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import jp.dip.fission.Env;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

// 残：自動環境構築
public class DBFactory {

	private static final String DB_TYPE_H2          = "H2";
	private static final String DB_TYPE_POSTGRESQL = "POSTGRESQL";
	private static final String DB_TYPE_MYSQL       = "MYSQL";
	private static final String DB_TYPE_ORACLE      = "ORACLE";


//	private static final String DB_DRIVER_CLASS = "DB_DRIVER_CLASS";
//	private static final String DB_SERVER_URL   = "DB_SERVER_URL";
//	private static final String DB_USER         = "DB_USER";
//	private static final String DB_PASSWORD     = "DB_PASSWORD";

	private static DBFactory _instance;
	// dbtype dbname datasource
	private static Map<String,Map<String,DataSource>> _dataSourceMap;

	private DBFactory() {
	}

	public boolean addOracleDataSource(String dbName) {
		return addDataSource(dbName,DB_TYPE_ORACLE,null);
	}

	public boolean addOracleDataSource(String dbName,Properties dbProperties) {
		return addDataSource(dbName,DB_TYPE_ORACLE,dbProperties);
	}

	public boolean addMysqlDataSource(String dbName) {
		return addDataSource(dbName,DB_TYPE_MYSQL,null);
	}

	public boolean addMysqlDataSource(String dbName,Properties dbProperties) {
		return addDataSource(dbName,DB_TYPE_MYSQL,dbProperties);
	}

	public boolean addPostgresqlDataSource(String dbName) {
		return addDataSource(dbName,DB_TYPE_POSTGRESQL,null);
	}

	public boolean addPostgresqlDataSource(String dbName,Properties dbProperties) {
		return addDataSource(dbName,DB_TYPE_POSTGRESQL,dbProperties);
	}

	public boolean addH2DataSource(String dbName) {
		return addDataSource(dbName,DB_TYPE_H2,null);
	}

	public boolean addH2DataSource(String dbName,Properties dbProperties) {
		return addDataSource(dbName,DB_TYPE_H2,dbProperties);
	}

	private boolean addDataSource(String dbName , String dbType,Properties dbProperties) {
		boolean result = false;
		try {
			if (dbProperties == null) {
				String filePath = Env.USER_DIR + Env.FILE_SEPARATOR + dbName + dbType + "_defaultdb.properties";
				File file = new File(filePath);
				dbProperties = new Properties();
				// ファイルが存在する場合、プロパティを読み込む
				if (file.exists()) {
					dbProperties.load(new FileReader(file));
				}
				// ファイルが存在しない場合、デフォルト値でプロパティ生成
				else {
					/* basic properties */
					// username
					dbProperties.setProperty("username", "");
					// password
					dbProperties.setProperty("password", "");
					// url
					dbProperties.setProperty("url", "jdbc:h2:" + file.getPath());
					// connection properties(param1=A,param2=b)
					dbProperties.setProperty("connectionProperties", "");

					/* connection properties */
					// auto commit true
					dbProperties.setProperty("defaultAutoCommit", "true");
					// read only (false)
//					dbProperties.setProperty("defaultReadOnly", "false");
					// transaction level(TRANSACTION_NONE, READ_COMMITTED, READ_UNCOMMITTED, REPEATABLE_READ, SERIALIZABLE)
//					dbProperties.setProperty("defaultTransactionIsolation", "TRANSACTION_NONE");
					// catalog
//					dbProperties.setProperty("defaultCatalog", "defaultCatalog");

					/* pool setting */
					// inital size (0)
					dbProperties.setProperty("initialSize", "0");
					// max active (8)
					dbProperties.setProperty("maxActive", "8");
					// max idol (8)
					dbProperties.setProperty("maxIdle", "8");
					// max idol (-1)
					dbProperties.setProperty("maxWait", "-1");

					/* validation */
					// validation query(接続確認)
					dbProperties.setProperty("validationQuery", "SELECT 1");
					// test on borrow(接続の有効性確認) (true)
//					dbProperties.setProperty("testOnBorrow", "true");
					// test on borrow(接続の有効性確認) (false)
//					dbProperties.setProperty("testOnReturn", "false");

					/* observer */
					// time between eviction runs millis (-1(0以下:無効))
//					dbProperties.setProperty("timeBetweenEvictionRunsMillis", "-1");
					// min idle (0)
//					dbProperties.setProperty("minIdle", "0");
					// test while idle (0)
//					dbProperties.setProperty("testWhileIdle", "false");
					// min evictable idle time millis (1000*60*30->30minute)
//					dbProperties.setProperty("minEvictableIdleTimeMillis", "1800000");
					// num tests per eviction run (3)
//					dbProperties.setProperty("numTestsPerEvictionRun", "3");
					/* prepare statement */
					// pool prepared statements (false)
//					dbProperties.setProperty("poolPreparedStatements", "false");
					// max open prepared statements (0)
//					dbProperties.setProperty("maxOpenPreparedStatements", "0");
					// プロパティが存在しない場合、デフォルトのパラメータを作成(自動生成)する。
					try {
						dbProperties.store(new FileWriter(file), "initialize parameter");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			Map<String,DataSource> datasourceMap = new HashMap<String,DataSource>();
			datasourceMap.put(dbName, BasicDataSourceFactory.createDataSource(dbProperties));
			_dataSourceMap.put(dbType, datasourceMap);
			result = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// インスタンス生成(デフォルト)
	public static DBFactory getInstance() throws Exception {
		if (_instance == null) {
			_instance = new DBFactory();
			_dataSourceMap = new HashMap<String,Map<String,DataSource>>();
		}
		return _instance;
	}

	public DBConnection getOracleDBConnection(String dbName) throws ClassNotFoundException, IOException, DBException, SQLException {
		return new H2DBConnection(_dataSourceMap.get(DB_TYPE_ORACLE).get(dbName).getConnection());
	}

	public DBConnection getPostgresqlDBConnection(String dbName) throws ClassNotFoundException, IOException, DBException, SQLException {
		return new H2DBConnection(_dataSourceMap.get(DB_TYPE_POSTGRESQL).get(dbName).getConnection());
	}

	public DBConnection getMysqlDBConnection(String dbName) throws ClassNotFoundException, IOException, DBException, SQLException {
		return new H2DBConnection(_dataSourceMap.get(DB_TYPE_MYSQL).get(dbName).getConnection());
	}

	public DBConnection getH2DBConnection(String dbName) throws ClassNotFoundException, IOException, DBException, SQLException {
		return new H2DBConnection(_dataSourceMap.get(DB_TYPE_H2).get(dbName).getConnection());
	}

	@Override
	protected void finalize() throws Throwable {
		Set<String> dbTypeSet = _dataSourceMap.keySet();
		if (dbTypeSet != null && !dbTypeSet.isEmpty()) {
			for (String dbType:dbTypeSet) {
				Map<String,DataSource> datasources = _dataSourceMap.get(dbType);
				Set<String> dbNameSet = datasources.keySet();
				if (dbNameSet != null && !dbNameSet.isEmpty()) {
					for (String dbName:dbNameSet) {
						Driver driver = DriverManager.getDriver(_dataSourceMap.get(dbTypeSet).get(dbType).getConnection().getMetaData().getDriverName());
						DriverManager.deregisterDriver(driver);
						driver = null;
						_dataSourceMap.get(dbType).remove(dbName);
					}
				}
				_dataSourceMap.remove(dbType);
			}
		}
		_dataSourceMap = null;
		super.finalize();
	}
}
