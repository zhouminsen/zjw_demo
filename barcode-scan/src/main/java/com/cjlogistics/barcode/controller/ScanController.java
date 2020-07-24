package com.cjlogistics.barcode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2020-05-20.
 */
@RequestMapping("")
@Controller
public class ScanController {


    @GetMapping("")
    public String index() {

        return "index";
    }

    @Autowired
    private DataSource dataSource;

    @GetMapping("/getData")
    @ResponseBody
    public Map<String, Object> getData(@RequestParam String code) {
        Map<String, Object> map = new HashMap<>();
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
                    map.put("custkey", rs.getObject("CUSTKEY"));
                    map.put("cust_description", rs.getObject("CUST_DESCRIPTION"));
                    map.put("barcode1", rs.getObject("BARCODE1"));
                    map.put("sku_description", rs.getObject("SKU_DESCRIPTION"));
                    map.put("lottable01", rs.getObject("LOTTABLE01"));
                    map.put("lottable02", rs.getObject("LOTTABLE02"));
                    map.put("lottable03", rs.getObject("LOTTABLE03"));
                    map.put("lottable04", rs.getObject("LOTTABLE04"));
                    map.put("confirmdate", rs.getObject("CONFIRMDATE"));
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
        return map;
    }
}
