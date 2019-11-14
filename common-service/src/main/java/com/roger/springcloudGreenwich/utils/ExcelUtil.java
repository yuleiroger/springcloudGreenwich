package com.roger.springcloudGreenwich.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by admin on 2019/11/13.
 */
public class ExcelUtil {
    public static void testLoad() throws Exception{
        FileInputStream in = new FileInputStream("D:\\project\\ksrsbank\\ip.xlsx");
        //InputStream in = file.getInputStream();
        Workbook workbook= WorkbookFactory.create(in);

        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        int firstRowNum = sheet.getFirstRowNum();
        System.err.println(firstRowNum);
        System.err.println("last row num==" + lastRowNum);
        if(lastRowNum == 0){
            throw new RuntimeException("data is too low");
        }
        Row head = sheet.getRow(firstRowNum);

        List<VmUserPwd> vupList = new ArrayList<>();

        Set<String> set = new HashSet<String>();//ip is not duplicate

        boolean isIp;
        for(int rowNum = 1; rowNum <= lastRowNum; rowNum++){
            Row row = sheet.getRow(rowNum);
            Cell cell_0 = row.getCell(0);
            isIp = StringUtil.isIP((cell_0 + "").trim());
            if(!isIp) {
                throw new RuntimeException("ip is illegal");
            }
            Cell cell_1 = row.getCell(1);
            if(isNull(cell_1)){
                throw new RuntimeException("user name can not be null");
            }
            Cell cell_2 = row.getCell(2);
            if(isNull(cell_2)){
                throw new RuntimeException("password can not be null");
            }
            set.add(cell_0.toString());
        }
        if(set.size() != lastRowNum){
            //TODO
            throw new RuntimeException("ip is duplicate");
        }

        for(int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++){
            VmUserPwd vup = new VmUserPwd();
            Row row = sheet.getRow(rowNum);
            Cell cell_0 = row.getCell(0);
            vup.setIpAddress(cell_0.toString().trim());
            Cell cell_1 = row.getCell(1);
            vup.setUserName(cell_1.toString());
            Cell cell_2 = row.getCell(2);
            cell_2.setCellType(HSSFCell.CELL_TYPE_STRING);//无条件转为文本，迫不得已
            vup.setVmPwd(cell_2.toString());
            vupList.add(vup);
            System.err.println(vup.getIpAddress()+","+vup.getUserName()+","+vup.getVmPwd());
        }
    }

    private static boolean isNull(Cell cell){
        if(cell == null){
           return true;
        }else if("".equals((cell+"").trim())){
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        testLoad();
    }
}
