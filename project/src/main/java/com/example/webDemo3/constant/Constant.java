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
            new MessageDTO(1,"Tên định danh này đã tồn tại với một lớp đang hoạt động.");
    public static final MessageDTO CLASSNAME_EXIST =
            new MessageDTO(1,"Tên lớp này tồn tại.");
    public static final MessageDTO USERLIST_NULL =
            new MessageDTO(1,"Danh sách tài khoản trống.");
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
    public static final MessageDTO TEACHER_ID_NULL =
            new MessageDTO(1,"Hãy chọn giáo viên.");
    public static final MessageDTO CLASS_ID_NULL =
            new MessageDTO(1,"Hãy chọn lớp");
    public static final MessageDTO TIMETABLE_NULL =
            new MessageDTO(1,"Không có kết quả.");
    public static final MessageDTO CLASSIDENTIFIER_EXIST_BLOCK =
            new MessageDTO(2,"Tên định danh này đã tồn tại nhưng bị khóa.");
    public static final MessageDTO CLASS_EXIST_BLOCK =
            new MessageDTO(2,"Lớp này đã tồn tại nhưng bị khóa.");
    public static final MessageDTO DEL_GIFTEDCLASS_FAIL =
            new MessageDTO(1,"Không thể xóa hệ chuyên này.");
    public static final MessageDTO VIOLATION_TYPE_EMPTY =
            new MessageDTO(1,"Không có kết quả.");
    public static final MessageDTO VIOLATION_ID_NULL =
            new MessageDTO(1,"Hãy chọn lỗi.");
    public static final MessageDTO VIOLATION_EMPTY =
            new MessageDTO(1,"Không có lỗi như vậy.");
    public static final MessageDTO VIOLATION_TYPE_ID_NULL =
            new MessageDTO(1,"Hãy chọn nội quy.");

    public static final MessageDTO VIOLATION_TYPE_NAME_EMPTY =
            new MessageDTO(1,"Hãy điền nội quy.");

    public static final MessageDTO TOTAL_GRAGE_EMPTY =
            new MessageDTO(1,"Hãy nhập điểm.");

    public static final MessageDTO VIOLATION_DESCIPTION_EMPTY =
            new MessageDTO(1,"Hãy điền mô tả.");

    public static final MessageDTO VIOLATION_SUBSTRACT_GRADE_EMPTY =
            new MessageDTO(1,"Hãy nhập điểm trừ.");

    public static final MessageDTO VIOLATION_EXISTS =
            new MessageDTO(1,"Không thể xóa loại lỗi này! Vẫn còn các lỗi đang hoạt động.");
    public static final MessageDTO SCHOOLYEARLIST_EMPTY =
            new MessageDTO(1,"Danh sách năm học trống.");
    public static final MessageDTO SCHOOLYEARID_EMPTY =
            new MessageDTO(1,"Hãy lựa chọn năm học.");
    public static final MessageDTO DELETESCHOOLYEAR_FAIL =
            new MessageDTO(1,"Không thể xóa năm học này.");
    public static final MessageDTO FROMYEAR_EMPTY =
            new MessageDTO(1,"Hãy nhập năm bắt đầu.");
    public static final MessageDTO TOYEAR_EMPTY =
            new MessageDTO(1,"Hãy nhập năm kết thúc.");
    public static final MessageDTO FROMDATE_EMPTY =
            new MessageDTO(1,"Hãy nhập ngày bắt đầu.");
    public static final MessageDTO TODATE_EMPTY =
            new MessageDTO(1,"Hãy nhập ngày kết thúc.");
    public static final MessageDTO FROMDATE_GREATER_TODATE =
            new MessageDTO(1,"Hãy chọn ngày kết thúc xảy ra sau ngày bắt đầu.");
    public static final MessageDTO FROMDATE_EXIST =
            new MessageDTO(1,"Ngày bắt đầu đã nằm trong một năm học khác.");
    public static final MessageDTO TODATE_EXIST =
            new MessageDTO(1,"Ngày kết thúc đã nằm trong một năm học khác.");
    public static final MessageDTO FROMDATE_GREATER_CURRENTDATE =
            new MessageDTO(1,"Ngày bắt đầu của năm học hiện tại phải nhỏ hơn ngày hôm nay.");
    public static final MessageDTO LIST_DAY_EMPTY =
            new MessageDTO(1,"Không có danh sách ngày.");
    public static final MessageDTO VIOLATION_ENTERING_TIME_NULL =
            new MessageDTO(1,"Không có kết quả.");
    public static final MessageDTO DELETE_ENTERING_TIME_EMPTY =
            new MessageDTO(1,"Hãy chọn thời gian thực thi để xóa.");
    public static final MessageDTO ENTERING_TIME_EMPTY =
            new MessageDTO(1,"Thời gian thực thi này không tồn tại.");
    public static final MessageDTO ROLE_ID_NULL =
            new MessageDTO(1,"Hãy chọn role.");
    public static final MessageDTO DAY_NOT_EXIST =
            new MessageDTO(1,"Không có ngày thỏa mãn.");
    public static final MessageDTO START_TIME_EMPTY =
            new MessageDTO(1,"Hãy chọn thời gian bắt đầu.");
    public static final MessageDTO END_TIME_EMPTY =
            new MessageDTO(1,"Hãy chọn thời gian kết thúc.");
    public static final MessageDTO CLASS_RED_STAR_EMPTY =
            new MessageDTO(1,"Không có kết quả.");
    public static final MessageDTO SCHOOLYEAR_EMPTY =
            new MessageDTO(1,"Không tồn tại năm học này.");
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

    //Success edit violation
    public static final MessageDTO EDIT_VIOLATION_SUCCESS =
            new MessageDTO(0,"Thông tin sửa thành công!");

    //Success add violation
    public static final MessageDTO ADD_VIOLATION_SUCCESS =
            new MessageDTO(0,"Thêm lỗi thành công!");

    //Success delete violation
    public static final MessageDTO DELETE_VIOLATION_SUCCESS =
            new MessageDTO(0,"Lỗi đã bị xóa!");

    //Success edit violation type
    public static final MessageDTO EDIT_VIOLATION_TYPE_SUCCESS =
            new MessageDTO(0,"Thông tin sửa thành công!");

    //Success add violation type
    public static final MessageDTO ADD_VIOLATION_TYPE_SUCCESS =
            new MessageDTO(0,"Thêm loại lỗi thành công!");

    //Success delete violation type
    public static final MessageDTO DELETE_VIOLATION_TYPE_SUCCESS =
            new MessageDTO(0,"Loại lỗi đã bị xóa!");

    //Success delete entering time
    public static final MessageDTO DELETE_VIOLATION_ENTERING_TIME_SUCCESS =
            new MessageDTO(0,"Xóa thành công!");

    //Success add violation type
    public static final MessageDTO ADD_VIOLATION_ENTERING_TIME_SUCCESS =
            new MessageDTO(0,"Thêm thời gian trực tuần thành công!");

}
