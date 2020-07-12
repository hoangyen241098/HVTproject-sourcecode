package com.example.webDemo3.service.impl.manageAccountServiceImpl;

import com.example.webDemo3.constant.Constant;
import com.example.webDemo3.dto.MessageDTO;
import com.example.webDemo3.dto.request.manageAccountRequestDto.DeleteAccountRequestDto;
import com.example.webDemo3.entity.User;
import com.example.webDemo3.repository.UserRepository;
import com.example.webDemo3.service.manageAccountService.DeleteAccountService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * lamnt98 - 27/06
 */
@Service
public class DeleteAccountServicempl implements DeleteAccountService {
    @Autowired
    private UserRepository userRepository;

    /**
     * lamnt98
     * 27/06
     * find and delete account
     * @param deleteAccount
     * @return MessageDTO
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ServiceException.class})
    public MessageDTO deleteAccount(DeleteAccountRequestDto deleteAccount) {
        MessageDTO messageDTO = new MessageDTO();
        try{
            List<String> listUser = deleteAccount.getListUser();
            for(String userName : listUser){
                User user = userRepository.findUserByUsername(userName);
                //check user exists or not
                if(user != null && (user.getStatus() == null || user.getStatus() != 1)){
                    user.setStatus(1);
                    userRepository.save(user);
                }else{
                    messageDTO = Constant.USER_NOT_EXIT;
                    return  messageDTO;
                }
            }
            messageDTO = Constant.DELETE_ACCOUNT_SUCCESS;
        }catch (Exception e){
            messageDTO.setMessageCode(1);
            messageDTO.setMessage(e.toString());
            return messageDTO;
        }
        return messageDTO;
    }
}
