package com.example.webDemo3.constant;

import com.example.webDemo3.dto.MessageDTO;

public class Constant {
    /**
     * Fail message
     */
    public static final MessageDTO USER_NOT_EXIT =
            new MessageDTO(1,"Tên đăng nhập không tồn tại.");
    public static final MessageDTO WRONG_PASSWORD =
            new MessageDTO(1,"Mật khẩu không đúng.");

    /**
     * Success message
     */
    public static final MessageDTO SUCCESS =
            new MessageDTO(0,"Thành công.");

    //Success message edit information of user
    public static final MessageDTO EDIT_INFOR_SUCCESS =
            new MessageDTO(0,"Thông tin sửa thành công!");

    //Success message delete account
    public static final MessageDTO DELETE_ACCOUNT_SUCCESS =
            new MessageDTO(0,"Tài khoản đã bị xóa!");
}
