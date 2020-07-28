package com.example.webDemo3.service.impl.manageSchoolRankImpl;

import com.example.webDemo3.service.manageSchoolRank.AdditionFunctionSchoolRankService;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class AdditionFunctionSchoolRankServiceImpl implements AdditionFunctionSchoolRankService {

    @Override
    public String addHistory(String oldhistory, String userName, Date date) {
        String history = "";

        if(oldhistory.isEmpty()){
            history = "<ul> <li><span class=\"font-500\">" + "Tạo ngày: " + date +  " - " + "bởi: " + userName + ".</span> </li></ul>";
        }else{
            history = oldhistory +
                    "<ul> <li><span class=\"font-500\">" + "Sửa ngày: " + date +  " - " + "bởi: " + userName + ".</span> </li></ul>";
        }

        return history;
    }
}
