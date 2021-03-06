package mapper;

import dtos.LoginUserDTO;
import dtos.NoPwUserDTO;
import dtos.RegisterUserDTO;
import entityDO.User;


public class UserMapper {

    public User convertLoginUserToUser(LoginUserDTO userDTO){
        return new User(userDTO.getEmail(), userDTO.getPw());
    }

    public NoPwUserDTO noPwUser (User user) {
        if (user != null) {
            return new NoPwUserDTO(user.getId(), user.getFirstName(), user.getSurName(), user.getEmail());
        }
        return null;
    }

    public User convertRegisterUser(RegisterUserDTO userDTO){
        return new User(userDTO.getFirstName(), userDTO.getSurName(), userDTO.getEmail(), userDTO.getPw());
    }
}
