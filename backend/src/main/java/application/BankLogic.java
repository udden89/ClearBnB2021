package application;

import dtos.AddBankDTO;
import dtos.AddListingDTO;
import entityDO.BankAccount;
import entityDO.Listing;
import entityDO.User;
import mapper.BankService;
import repositories.BankRepository;

public class BankLogic {

    Repositories repositories;
    private BankService bs;

    public BankLogic(Repositories repositories) {
        this.repositories = repositories;
        this.bs = new BankService();
    }

    public BankAccount createNewBank(AddBankDTO dto, User user) {
        BankAccount newBank = bs.convertAddBankDTOToBank(dto, user);
        user.setBankAccount(newBank);
        repositories.userRepository.update(user);
        return newBank;
    }
}
