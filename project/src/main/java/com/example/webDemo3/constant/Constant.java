package com.example.webDemo3.constant;

import com.example.webDemo3.dto.MessageDTO;

public class Constant {
    /**
     * Constant value
     */
    public static final Integer PAGE_SIZE = 10;
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
            new MessageDTO(1,"Hãy nhập tên đăng nhập.");
    public static final MessageDTO PASSWORD_EMPTY =
            new MessageDTO(1,"Hãy nhập mật khẩu.");
    public static final MessageDTO CLASSNAME_NOT_EXIT =
            new MessageDTO(1,"Tên lớp không tồn tại.");
    public static final MessageDTO CLASSLIST_NOT_EXIT =
            new MessageDTO(1,"Danh sách lớp trống.");
    public static final MessageDTO DELETE_FALSE =
            new MessageDTO(1,"Tài khoản xóa không thành công!");
    public static final MessageDTO USER_INACTIVE =
            new MessageDTO(1,"Tài khoản này đã bị khóa!");
    public static final MessageDTO GIFTEDCLASSLIST_NOT_EXIT =
            new MessageDTO(1,"Danh sách hệ chuyên trống.");
    public static final MessageDTO GRADE_EMPTY =
            new MessageDTO(1,"Hãy lựa chọn khối lớp.");
    public static final MessageDTO GIFTEDCLASSID_EMPTY =
            new MessageDTO(1,"Hãy lựa chọn hệ chuyên.");
    public static final MessageDTO CLASSIDENTIFIER_EMPTY =
            new MessageDTO(1,"Hãy nhập tên định danh cho lớp.");
    public static final MessageDTO CLASSIDENTIFIER_EXIST =
            new MessageDTO(1,"Tên định danh này đã tồn tại.");
    public static final MessageDTO CLASSNAME_EXIST =
            new MessageDTO(1,"Tên lớp này tồn tại.");
    public static final MessageDTO USERLIST_NULL =
            new MessageDTO(1,"Danh sách lớp trống.");
    public static final MessageDTO GIFTEDCLASSID_EXIST =
            new MessageDTO(1,"Tên hệ chuyên đã tồn tại.");

    public static final MessageDTO FULLNAME_EMPTY =
            new MessageDTO(1,"Hãy nhập tên giáo viên.");
    public static final MessageDTO TEACHER_IDENTIFIER_EMPTY =
            new MessageDTO(1,"Hãy nhập định danh.");
    public static final MessageDTO TEACHER_NOT_EXIT =
            new MessageDTO(1,"Giáo viên không tồn tại.");
    public static final MessageDTO TEACHER_EXIT =
            new MessageDTO(1,"Tên định danh của giáo viên đã tồn tại.");
    public static final MessageDTO TEACHER_ID_INVALID =
            new MessageDTO(1,"Thông tin không đúng định dạng.");
    public static final MessageDTO TEACHERLIST_NULL =
            new MessageDTO(1,"Danh sách giáo viên trống.");
    public static final MessageDTO CLASS_NOT_EXIST =
            new MessageDTO(1,"Lớp này không tồn tại.");
    public static final MessageDTO ROLE_EMPTY =
            new MessageDTO(1,"Hãy nhập chức vụ.");
    public static final MessageDTO CLASS_EMPTY =
            new MessageDTO(1,"Hãy nhập lớp.");
    public static final MessageDTO ROLE_NOT_EXIST =
            new MessageDTO(1,"Chức vụ không tồn tại.");
    public static final MessageDTO CLASS_EXIST =
            new MessageDTO(1,"Lớp này đã tồn tại.");
    public static final MessageDTO GIFTEDCLASSNAME_NOT_EXIST =
            new MessageDTO(1,"Tên hệ chuyên không tồn tại.");
    public static final MessageDTO YEAR_ID_NULL =
            new MessageDTO(1,"Không có năm hiện tại.");
    public static final MessageDTO LIST_WEEK_NULL =
            new MessageDTO(1,"Không có danh sách tuần trong năm hiện tại.");
    public static final MessageDTO LIST_YEAR_NULL =
            new MessageDTO(1,"Không có danh sách năm.");
    public static final MessageDTO WEEK_ID_NULL =
            new MessageDTO(1,"Không có tuần trong năm hiện tại.");
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


    //Success message change account password
    public static final MessageDTO CHANGE_PASS_SUCCESS =
            new MessageDTO(0,"Mật khẩu đã được đặt lại!");

    public static final MessageDTO RESET_PASS_SUCCESS =
            new MessageDTO(0,"Mật khẩu đã được đặt lại.");

    //Success message edit information of teacher
    public static final MessageDTO EDIT_TEACHER_INFORMATION_SUCCESS =
            new MessageDTO(0,"Sửa thông tin Giáo viên thành công!");

    //Success message delete account
    public static final MessageDTO DELETE_TEACHER_SUCCESS =
            new MessageDTO(0,"Giáo viên đã bị xóa!");

    //Success add teacher
    public static final MessageDTO ADD_TEACHER_SUCCESS =
            new MessageDTO(0,"Thêm Giáo viên thành công!");

}
