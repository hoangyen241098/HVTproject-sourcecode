package com.example.webDemo3.constant;

import com.example.webDemo3.dto.MessageDTO;

public class Constant {
    /**
     * Constant value
     */
    public static final Integer PAGE_SIZE = 5;
    /**
     * Fail message
     */
    public static final MessageDTO USER_NOT_EXIT =
            new MessageDTO(1,"Tên đăng nhập không tồn tại.");
    public static final MessageDTO WRONG_PASSWORD =
            new MessageDTO(1,"Mật khẩu không đúng.");
    public static final MessageDTO USERNAME_EXIST =
            new MessageDTO(1,"Tên tài khoản đã tồn tại.");
    public static final MessageDTO USERNAME_EMPTY =
            new MessageDTO(1,"Hãy nhập trên đăng nhập.");
    public static final MessageDTO PASSWORD_EMPTY =
            new MessageDTO(1,"Hãy nhập mật khẩu.");
    public static final MessageDTO CLASSNAME_NOT_EXIT =
            new MessageDTO(1,"Tên lớp không tồn tại.");
    public static final MessageDTO CLASSLIST_NOT_EXIT =
            new MessageDTO(1,"Danh sách lớp trống.");

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

    public static final MessageDTO RESET_PASS_SUCCESS =
            new MessageDTO(0,"Mật khẩu đã được đặt lại.");

}
