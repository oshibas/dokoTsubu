package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Mutter;

public class MuttersDAO {
	//DB接続情報
	private final String JDBC_URL = 
		"jdbc:mysql://localhost/dokoTsubu";
	private final String DB_USER = "root";
	private final String DB_PASS = "";
	
	// すべてのつぶやきを取得
	public List<Mutter> findAll(){
		List<Mutter>mutterList = new ArrayList<>();
		//JDBC driver読み込み
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// JDBCドライバが見つからない場合
				throw new IllegalStateException(
					"JDBCドライバを読み込めませんでした");
			}
		//DB connect
		try(Connection conn = DriverManager.getConnection(
			JDBC_URL, DB_USER, DB_PASS)){
			
			//SELECT文prepare
			String sql =
				"SELECT ID, NAME, TEXT FROM MUTTERS ORDER BY ID DESC";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			//SELECT文を実行
			ResultSet rs = pStmt.executeQuery();
			
			//SELECT文の結果をArrayListに格納
			while(rs.next()) {
				int id = rs.getInt("ID");
				String userName = rs.getString("NAME");
				String text = rs.getString("TEXT");
				Mutter mutter = new Mutter(id, userName, text);
				mutterList.add(mutter);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return mutterList;
	}
	public boolean create(Mutter mutter) {
		//JCBDドライバ読み込み
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException(
				"JDBCドライバを読み込めませんでした");
		}
		//DB接続
		try(Connection conn = DriverManager.getConnection(
			JDBC_URL, DB_USER, DB_PASS)){
			
			//INSERT文の準備（idは自動連番なので指定しなくてOK）
			String sql = "INSERT INTO MUTTERS(NAME, TEXT) VALUES(?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			//INSERT文の「？」に使用する値を設定してSQL文を完成
			pStmt.setString(1, mutter.getUserName());
			pStmt.setString(2, mutter.getText());
			
			//INSERT文を実行（resultには追加された行数が入る）
			int result = pStmt.executeUpdate();
			if(result != 1) {
				return false;
			}
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//★CRUD 削除用追記
	public boolean delete(int muId, String name) {
		//JCBDドライバ読み込み
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException(
				"JDBCドライバを読み込めませんでした");
		}
		//DB接続
		try(Connection conn = DriverManager.getConnection(
			JDBC_URL, DB_USER, DB_PASS)){
			
			//Delete文の準備（idは自動連番なので指定しなくてOK）
			String sql = "delete from mutters where id = ? and name = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			//DELETE文の「？」に使用する値を設定してSQL文を完成
			pStmt.setInt(1, muId);
			pStmt.setString(2, name);
			
			//DELETE文を実行（resultには追加された行数が入る）
			int result = pStmt.executeUpdate();
			if(result != 1) {
				return false;
			}
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
