package com.example.webDemo3.controller;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.manageAccountResponseDto.LoginResponseDto;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.manageAccountResponseDto.ViewPerInforResponseDto;
import com.example.webDemo3.dto.request.manageAccountRequestDto.ChangePasswordRequestDto;
import com.example.webDemo3.dto.request.manageAccountRequestDto.EditPerInforRequestDto;
import com.example.webDemo3.dto.request.manageAccountRequestDto.LoginRequestDto;
import com.example.webDemo3.dto.request.manageAccountRequestDto.ViewPerInforRequestDto;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.security.CustomUserDetails;
import com.example.webDemo3.security.JwtTokenProvider;
import com.example.webDemo3.security.LoginResponse;
import com.example.webDemo3.service.manageAccountService.ChangePasswordService;
import com.example.webDemo3.service.manageAccountService.EditPerInforService;
import com.example.webDemo3.service.manageAccountService.LoginService;
import com.example.webDemo3.service.manageAccountService.ViewPerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private ChangePasswordService changePasswordService;

    @Autowired
    private ViewPerInfoService viewPerInfoService;

    @Autowired
    private EditPerInforService editPerInforService;

    @Autowired
    private LoginService loginService;

    /**
     * lamnt98
     * 23/06
     * catch request from client to change password
     * @param model
     * @return MessageDTO
     */
    @PostMapping("/changepassword")
    public MessageDTO login(@RequestBody ChangePasswordRequestDto model)
    {
        return changePasswordService.checkChangePasswordUser(model);
    }


    /**
     * lamnt98
     * 26/06
     * catch request from client to edit information of user
     * @param model
     * @return MessageDTO
     */
    @PostMapping("/editinformation")
    public MessageDTO login(@RequestBody EditPerInforRequestDto model)
    {
        return editPerInforService.editUserInformation(model);
    }


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    /**
     * kimpt142
     * 23/6/2020
     * catch request from client to check login and if success, add username into session
     * @param model is User entity include username and password
     * @return LoginDto with (1,success) if success
     */

    @Autowired
    PasswordEncoder pe;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/get-authentication")
    public LoginResponse getAuthentication(){
        LoginResponse responseDTO = new LoginResponse();
        if(SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken){
            CustomUserDetails userDetails = (CustomUserDetails)SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            User userModel = userRepository.findUserByUsername(userDetails.getUsername());
            //UserDTO userDTO = new UserDTO(userModel);
//            if(userModel instanceof LandlordModel){
//                int countRequest = rentalRequestRepository.getRequestNumber(userModel.getUsername(), 7L);
//                userDTO.setRequestNumber(countRequest);
//            }
//            responseDTO.setUserDTO(userDTO);
            responseDTO.setRoleid(userModel.getRole().getRoleId());
        }
        return responseDTO;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequestDto model, HttpSession session)
    {
        LoginResponseDto responseDto = loginService.checkLoginUser(model);
//        if(responseDto.getMessage().getMessageCode() == 0) {
//            session.setAttribute("username", model.getUsername());
//        }
//        String passWord = pe.encode(model.getPassword());
        LoginResponse output = new LoginResponse(responseDto.getMessage(),
                                responseDto.getRoleid(),
                                responseDto.getCurrentYearId());
        if(responseDto.getMessage().getMessageCode() == 0){
            // Xác thực từ username và password.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            model.getUsername(),
                            model.getPassword()
                    )
            );
            // Nếu không xảy ra exception tức là thông tin hợp lệ
            // Set thông tin authentication vào Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Trả về jwt cho người dùng.
            String jwt = tokenProvider.generateToken(
                    (CustomUserDetails) authentication.getPrincipal());
            output.setAccessToken(jwt);
        }
        return output;
    }

    @PostMapping(value = "/logout")
    public MessageDTO logout(HttpServletRequest request, HttpServletResponse response){
        MessageDTO message = new MessageDTO();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            message = Constant.SUCCESS;
            return message;
        }else {
            message.setMessageCode(1);
            message.setMessage("Authentication is not exist");
            return message;
        }
    }

    /**
     * kimpt142
     * 23/6/2020
     * catch request logout and remove session
     * @param session save username
     * @return message
     */
//    @PostMapping("/logout")
//    public MessageDTO logout(HttpSession session)
//    {
//        MessageDTO message = new MessageDTO();
//        if(session.getAttribute("username") != null){
//            session.removeAttribute("username");
//            message.setMessageCode(0);
//            message.setMessage("Thành công");
//            return message;
//        }
//        return message;
//    }


    /**
     * lamnt98
     * 25/06
     * catch request from client to find information of user
     * @param model
     * @return ViewPerInforResponseDto
     */
    @PostMapping("/viewinformation")
    public ViewPerInforResponseDto viewInformation(@RequestBody ViewPerInforRequestDto model)
    {
        ViewPerInforResponseDto responseDto = viewPerInfoService.getUserInformation(model);
        return responseDto;
    }

}
