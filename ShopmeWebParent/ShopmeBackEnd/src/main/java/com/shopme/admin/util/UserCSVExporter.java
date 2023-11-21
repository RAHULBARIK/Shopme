package com.shopme.admin.util;

import com.shopme.common.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserCSVExporter {
    public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timeStamp = dateFormatter.format(new Date());
        String fileName = "users_"+ timeStamp +".csv";

        response.setContentType("text/csv");

        String headerKey = "content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey,headerValue);

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"User Id","E-mail","First Name","Last Name","Roles","Enabled"};
        String[] fieldMapping = {"id","email","firstName","lastName","roles","enabled"};



        csvBeanWriter.writeHeader(csvHeader);

        for(User listUser :listUsers){
            csvBeanWriter.write(listUser,fieldMapping);
        }

        csvBeanWriter.close();
    }
}
