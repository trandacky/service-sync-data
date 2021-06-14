package com.dacky.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.dacky.constant.Constant;
import com.dacky.entity.Connect;
import com.dacky.service.ConnectService;

@Component
public class RunAndSync {

	@Autowired
	private ConnectService connectService;

	@EventListener(ApplicationReadyEvent.class)
	public void running() throws InterruptedException {
		while (true) {
			List<Connect> connects = connectService.findAll();
			if (!connects.isEmpty()) {
				for (Connect connect : connects) {
					try {

						Class.forName(connect.getDriverStart());
						Connection con = DriverManager.getConnection(connect.getUrlStart(), connect.getUsernameStart(),
								connect.getPasswordStart());
						Statement statement = con.createStatement();
						// it TYPE_SCROLL_SENSITIVE can next and previous result set
						// ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY

						ResultSet resultSet = statement.executeQuery(
								"SELECT * FROM " + connect.getTableNameTemp() + " ORDER BY " + "last_modify" + " ASC");

						syncData(resultSet, connect);
						con.close();
					} catch (Exception e) {
						System.out.println(e.toString());
					}
				}

			}
			Thread.sleep(5000);
		}
	}

	private void syncData(ResultSet resulSetTemp, Connect connect) {
		try {
			while (resulSetTemp.next()) {
				switch (resulSetTemp.getString("query_type")) {
				case Constant.INSERT_QUERY:
					System.out.println("insert *************");
					insert(resulSetTemp, connect);
					deleteTemp(resulSetTemp, connect);
					break;
				case Constant.UPDATE_QUERY:
					System.out.println("update *************");
					update(resulSetTemp, connect);
					deleteTemp(resulSetTemp, connect);
					break;
				case Constant.DELETE_QUERY:
					System.out.println("delete *************");
					deleteEnd(resulSetTemp, connect);
					deleteTemp(resulSetTemp, connect);
					break;

				default:
					System.out.println("invalid *************");
					break;
				}
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private void insert(ResultSet resulSetTemp, Connect connect) {
		try {
			Class.forName(connect.getDriverStart());
			Connection connectStart = DriverManager.getConnection(connect.getUrlStart(), connect.getUsernameStart(),
					connect.getPasswordStart());
			Statement statement = connectStart.createStatement();
			ResultSet resultSetStart = statement.executeQuery("SELECT * FROM " + resulSetTemp.getString("table_name")
					+ " WHERE id = " + resulSetTemp.getString("id_column_value"));
			ResultSetMetaData resulMetaData = resultSetStart.getMetaData();
			resultSetStart.next();
			String field = "";
			String value = "";
			for (int i = 1; i <= resulMetaData.getColumnCount(); i++) {
				field = field + resulMetaData.getColumnName(i) + ", ";
				value = value + "?, ";
			}
			field = field.substring(0, field.length() - 2);
			value = value.substring(0, value.length() - 2);

			// new connect
			Class.forName(connect.getDriverEnd());
			Connection connectEnd = DriverManager.getConnection(connect.getUrlEnd(), connect.getUsernameEnd(),
					connect.getPasswordEnd());
			PreparedStatement statement2 = connectEnd.prepareStatement(
					"INSERT INTO " + resulSetTemp.getString("table_name") + " (" + field + ") VALUES (" + value + ")");
			for (int i = 1; i <= resulMetaData.getColumnCount(); i++) {
				if (resulMetaData.getColumnTypeName(i).equals("bigserial"))
					statement2.setLong(i, resultSetStart.getLong(i));
				else
					statement2.setString(i, resultSetStart.getString(i));
			}
			statement2.execute();
			connectStart.close();
			connectEnd.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private void update(ResultSet resulSetTemp, Connect connect) {
		try {
			Class.forName(connect.getDriverStart());
			Connection connectStart = DriverManager.getConnection(connect.getUrlStart(), connect.getUsernameStart(),
					connect.getPasswordStart());
			Statement statement = connectStart.createStatement();
			ResultSet resultSetStart = statement.executeQuery("SELECT * FROM " + resulSetTemp.getString("table_name")
					+ " WHERE id = " + resulSetTemp.getString("id_column_value"));
			ResultSetMetaData resulMetaData = resultSetStart.getMetaData();
			resultSetStart.next();
			String field = "";
			for (int i = 2; i <= resulMetaData.getColumnCount(); i++) {
				field = field + resulMetaData.getColumnName(i) + "= ?, ";
			}
			field = field.substring(0, field.length() - 2);

			// new connect
			Class.forName(connect.getDriverEnd());
			Connection connectEnd = DriverManager.getConnection(connect.getUrlEnd(), connect.getUsernameEnd(),
					connect.getPasswordEnd());
			PreparedStatement statement2 = connectEnd.prepareStatement("UPDATE " + resulSetTemp.getString("table_name") + " SET "
					+ field + " WHERE id = " + resulSetTemp.getString("id_column_value"));
			for (int i = 2; i <= resulMetaData.getColumnCount(); i++) {
				if (resulMetaData.getColumnTypeName(i).equals("bigserial")||resulMetaData.getColumnTypeName(i).equals("int8"))
					statement2.setLong(i - 1, resultSetStart.getLong(i));
				else
					statement2.setString(i - 1, resultSetStart.getString(i));
			}
			statement2.execute();
			connectStart.close();
			connectEnd.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private void deleteTemp(ResultSet resulSetTemp, Connect connect) {
		try {
			Class.forName(connect.getDriverStart());
			Connection connectStart = DriverManager.getConnection(connect.getUrlStart(), connect.getUsernameStart(),
					connect.getPasswordStart());
			Statement statement = connectStart.createStatement();
			statement.execute(
					"DELETE FROM " + connect.getTableNameTemp() + " WHERE id = " + resulSetTemp.getString("id"));
			connectStart.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private void deleteEnd(ResultSet resulSetTemp, Connect connect) {
		try {
			Class.forName(connect.getDriverEnd());
			Connection connectStart = DriverManager.getConnection(connect.getUrlEnd(), connect.getUsernameEnd(),
					connect.getPasswordEnd());
			Statement statement = connectStart.createStatement();
			statement.execute("DELETE FROM " + resulSetTemp.getString("table_name") + " WHERE id = "
					+ resulSetTemp.getString("id_column_value"));
			connectStart.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
