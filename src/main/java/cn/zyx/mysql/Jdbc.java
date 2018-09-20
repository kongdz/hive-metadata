package cn.zyx.mysql;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Jdbc {
	public static void main(String[] args) throws SQLException {
		Connection conn = null;
		String sql;
		//MySQL连接信息
		String username = "mlsxxxxxx";
		String password = "xxxxxx";
		String url = "jdbc:mysql://10.xxxx/higo_hive?useUnicode=true&characterEncoding=utf-8";// &autoReconnect=true&failOverReadOnly=false
		try {
			Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
			conn = DriverManager.getConnection(url, username, password);
			Statement stmt = conn.createStatement();
			File file = new File("/root/data-platform-executor/TXT/ZhongJianBiao.txt");
			BufferedReader reader = null;
			String temp = null;
			int line = 1;
			String resultTable_id = "";
			HiveData hiveData = new HiveData();
			ArrayList<HiveData> hiveArray = new ArrayList<HiveData>();
			try {
				reader = new BufferedReader(new FileReader(file));
				String table, DB_ID;
				while ((temp = reader.readLine()) != null) {
					String[] arr = null;
					if (temp.indexOf(".") > 0) {
						arr = temp.split("\\.");
					}
					if (temp.indexOf(".") > 0 && arr[0].equals("inf")) {
						DB_ID = "16";
						table = temp.split("\\.")[1];
					} else if (temp.indexOf(".") > 0 && arr[0].equals("risk")) {
						DB_ID = "21";
						table = temp.split("\\.")[1];
					} else if (temp.indexOf(".") > 1 && arr[0].equals("tmp")) {
						DB_ID = "11";
						table = temp.split("\\.")[1];
					} else if (temp.indexOf(".") > 1 && arr[0].equals("udw")) {
						DB_ID = "6";
						table = temp.split("\\.")[1];
						System.out.println("table:" + table);
					} else {
						DB_ID = "1";
						table = temp;
						System.out.println();
					}
					sql = "SELECT TBL_ID,TBL_NAME FROM TBLS WHERE TBL_NAME ='" + table + "' and DB_ID =" + DB_ID;
					String table_id = null;
					ResultSet result = stmt.executeQuery(sql);
					if (result != null) {
						while (result.next()) {
							System.out.println(result.getString(1) + "\t");
							table = result.getString(2);
							table_id = result.getString(1);
						}
					}
					String sql2 = "select PART_NAME from PARTITIONS where TBL_ID =" + table_id
							+ " group by PART_NAME desc limit 1";
					ResultSet result2 = stmt.executeQuery(sql2);
					line++;
					if (result2.next()) {
						while (result2.next()) {
							System.out.println(result2.getString(1) + "\t");
							String PART_NAME = result2.getString(1);
							resultTable_id = table + " " + PART_NAME;
							hiveData.setTable(table);
							hiveData.setPapt_name(PART_NAME);
							String paprm_key = null, paprm_value = "";
							HiveData hive2 = new HiveData(table, PART_NAME, table_id, paprm_key, paprm_value);
							hiveArray.add(hive2);
						}
					} else {
						String sql3 = "SELECT * FROM TABLE_PARAMS WHERE TBL_ID = " + table_id
								+ " AND PARAM_KEY IN ('transient_lastDdlTime', 'totalSize')";
						ResultSet result3 = stmt.executeQuery(sql3);
						if (result3 != null) {
							while (result3.next()) {
								String PARAM_KEY = result3.getString(2);
								table_id = result3.getString(1);
								String PARAM_VALUE = result3.getString(3);
								if (PARAM_KEY.equals("transient_lastDdlTime")) {
									System.out.println("PARAM_VALUE1:"+PARAM_VALUE+"!");
									long l = Long.parseLong(PARAM_VALUE)*1000L;//一定要是long类型
									String res;
									SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									Date date2 = new Date(l);
									res = simpleDateFormat.format(date2);
									PARAM_VALUE = res;
								}
								hiveData.setTable(table);
								hiveData.setTable_id(table_id);
								hiveData.setPaprm_key(PARAM_KEY);
								hiveData.setPaprm_value(PARAM_VALUE);
								String PART_NAME = null;
								HiveData hive3 = new HiveData(table, PART_NAME, table_id, PARAM_KEY, PARAM_VALUE);
								hiveArray.add(hive3);
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			File fout = new File("/root/data-platform-executor/txt/hiveMetadata.txt");
			FileOutputStream fos = new FileOutputStream(fout);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			for (int i = 0; i < hiveArray.size(); i++) {
				bw.write(hiveArray.get(i).getTable() + " " + hiveArray.get(i).getTable_id() + " "
						+ hiveArray.get(i).getPapt_name() + " " + hiveArray.get(i).getPaprm_key() + " "
						+ hiveArray.get(i).getPaprm_value());
				bw.newLine();
			}
			bw.close();
		} catch (SQLException e) {
			System.out.println("MySQL操作错误");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
}