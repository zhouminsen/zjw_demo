package com.cjlogistics.barcode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2020-05-20.
 */
@RequestMapping("")
@Controller
public class ScanController {


    @GetMapping("")
    public String index() {

        return "index2";
    }

    @Autowired
    private DataSource dataSource;

    @GetMapping("/getData")
    @ResponseBody
    public List<Map<String, Object>> getData(@RequestParam String code) {
        List<Map<String, Object>> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            code = String.format("<SERIALNO>%s</SERIALNO>", code);
            pstmt = conn.prepareCall(
                    "{call dbo.SPWEB_SERIALSTOCK_DATAREAD(?,?,?,?,?,?,?,?,?,?,?,?)}");
            pstmt.setObject(1, "WEBAPP");
            pstmt.setObject(2, "GETSERIALINFO");
            pstmt.setObject(3, "SHA3");
            pstmt.setObject(4, "SHAKGC");
            pstmt.setObject(5, "SHAKGC");
            pstmt.setObject(6, "SHA3");
            pstmt.setObject(7, "");
            pstmt.setObject(8, code);
            pstmt.setObject(9, "WEBUSER");
            pstmt.registerOutParameter(10, Types.INTEGER);
            pstmt.registerOutParameter(11, Types.INTEGER);
            pstmt.registerOutParameter(12, Types.INTEGER);
            boolean execute = pstmt.execute();
            if (execute) {
                pstmt.getObject(10);
                pstmt.getObject(11);
                pstmt.getObject(12);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("CUSTKEY", rs.getObject("CUSTKEY"));
                    map.put("CUST_DESCRIPTION", rs.getObject("CUST_DESCRIPTION"));
                    map.put("BARCODE1", rs.getObject("BARCODE1"));
                    map.put("SKU_DESCRIPTION", rs.getObject("SKU_DESCRIPTION"));
                    map.put("LOTTABLE01", rs.getObject("LOTTABLE01"));
                    map.put("LOTTABLE02", rs.getObject("LOTTABLE02"));
                    map.put("LOTTABLE03", rs.getObject("LOTTABLE03"));
                    map.put("LOTTABLE04", rs.getObject("LOTTABLE04"));
                    map.put("CONFIRMDATE", rs.getObject("CONFIRMDATE"));
                    list.add(map);
                    list.add(map);
                    list.add(map);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return list;
    }
}
