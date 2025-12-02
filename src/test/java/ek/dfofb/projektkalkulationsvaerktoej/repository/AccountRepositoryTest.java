package ek.dfofb.projektkalkulationsvaerktoej.repository;

import ek.dfofb.projektkalkulationsvaerktoej.repository.interfaces.IAccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void getAccountFromID() {
        assertThat(accountRepository.getAccountFromID(1).getName()).isEqualTo("Markus Addington");
    }

    @Test
    void getAllAccounts() {
        assertThat(accountRepository.getAllAccounts().size()).isEqualTo(2);
    }

    @Test
    void getAllAccountsWithRoleID() {
    }

    @Test
    void addAccount() {
    }

    @Test
    void updateAccount() {
    }

    @Test
    void deleteAccount() {
    }
}