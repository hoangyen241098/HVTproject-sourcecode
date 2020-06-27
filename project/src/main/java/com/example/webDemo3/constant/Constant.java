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
    public static final MessageDTO USERNAME_EXIST =
            new MessageDTO(1,"Tên tài khoản đã tồn tại.");

    /**
     * Success message
     */
    public static final MessageDTO SUCCESS =
            new MessageDTO(0,"Thành công.");
}
