package com.whayer.wx.socket.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.whayer.wx.socket.entity.File;
import com.whayer.wx.socket.util.Util;

public class FileService {
	private Connection conn = null;
	private PreparedStatement ptmt = null;
	private ResultSet rs = null;

	public void savefile(File file) {
		String sql = (" insert into tb_file(fname,focntent) values(?,?)");
		try {
			conn = Util.getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, file.getFname());
			ptmt.setBytes(2, file.getFocntent());
			ptmt.executeUpdate();
		} catch (

		SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(conn, ptmt, rs);
		}
	}

}
