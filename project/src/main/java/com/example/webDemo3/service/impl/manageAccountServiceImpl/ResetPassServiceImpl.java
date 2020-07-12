package com.example.webDemo3.service.impl.manageAccountServiceImpl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.service.manageAccountService.ResetPassService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * kimpt142 - 27/6
 */
@Service
public class ResetPassServiceImpl implements ResetPassService {

    @Autowired
    private UserRepository userRepository;

    /**
     * kimpt142
     * 27/6
     * update new password for user list
     * @param userNameList is username list need to update
     * @param password is new password
     * @return message success
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ServiceException.class})
    public MessageDTO resetMultiplePassword(String[] userNameList, String password) {
        MessageDTO message = new MessageDTO();

        try {
            for (String username : userNameList) {
                User user = userRepository.findUserByUsername(username);
                if(user != null) {
                    user.setPassword(password);
                    userRepository.save(user);
                }
                else
                {
                    message = Constant.USER_NOT_EXIT;
                    return message;
                }
            }
        }
        catch (Exception e){
            message.setMessageCode(1);
            message.setMessage(e.toString());
            return message;
        }

        message = Constant.RESET_PASS_SUCCESS;
        return message;
    }
}
