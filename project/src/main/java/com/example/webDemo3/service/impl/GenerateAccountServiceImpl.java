package com.example.webDemo3.service.impl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.GenerateNameResponseDto;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.GenerateNameRequestDto;
import com.example.webDemo3.entity.Class;
import com.example.webDemo3.entity.Role;
import com.example.webDemo3.repository.ClassRepository;
import com.example.webDemo3.repository.RoleRepository;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.service.GenerateAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;

/*
kimpt142 - 30/6
 */
@Service
public class GenerateAccountServiceImpl implements GenerateAccountService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private UserRepository userRepository;
    /**
     * kimpt142
     * 30/6
     * generate name form roleid, classid
     * @param model
     * @return
     */
    @Override
    public GenerateNameResponseDto generateAccountName(GenerateNameRequestDto model) {
        GenerateNameResponseDto responseDto = new GenerateNameResponseDto();
        MessageDTO message = new MessageDTO();
        String userName = null;
        Integer roleId = model.getRoleId();
        Integer classId = model.getClassId();

        if(roleId == null){
            message = Constant.ROLE_EMPTY;
            responseDto.setMessage(message);
            return responseDto;
        }

        if(classId == null){
            message = Constant.CLASS_EMPTY;
            responseDto.setMessage(message);
            return responseDto;
        }
        Role role = roleRepository.findByRoleId(roleId);
        Class genClass = classRepository.findByClassId(classId);

        if(role == null){
            message = Constant.ROLE_NOT_EXIST;
            responseDto.setMessage(message);
            return responseDto;
        }
        if(genClass == null){
            message = Constant.CLASS_NOT_EXIST;
            responseDto.setMessage(message);
            return responseDto;
        }

        String rollName = role.getRoleName();
        String className = genClass.getGrade().toString().concat(genClass.getGiftedClass().getName());

        if(!rollName.equalsIgnoreCase("") && !className.equalsIgnoreCase("")){
            userName = stripAccents(rollName+className);
        }

        Integer indexAccount = userRepository.findAllByClassSchoolClassId(classId).size() + 1;
        List<String> userNameList = userRepository.findAllUsername();

        while (userNameList.contains(userName.concat(indexAccount.toString()))){
            indexAccount+=1;
        }

        userName += indexAccount.toString();
        responseDto.setUserName(userName);
        message = Constant.SUCCESS;
        responseDto.setMessage(message);
        return responseDto;
    }

    private String formatString(String str){
        str = str.toLowerCase();
        str = str.replace("/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g", "a");
        str = str.replace("/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g", "e");
        str = str.replace("/ì|í|ị|ỉ|ĩ/g", "i");
        str = str.replace("/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g", "o");
        str = str.replace("/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g", "u");
        str = str.replace("/ỳ|ý|ỵ|ỷ|ỹ/g", "y");
        str = str.replace("/đ/g", "d");
        str = str.replace("/\\s+/g", "");
        str = str.trim();
        return str;
    }

    public static String stripAccents(String s)
    {
        s = s.toLowerCase();
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        s = s.replaceAll("\\s", "");
        s = s.replaceAll("đ", "d");
        return s;
    }
}
