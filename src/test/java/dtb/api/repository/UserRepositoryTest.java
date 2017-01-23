package dtb.api.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import dtb.api.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    @Test
    public void findByUsernameShouldReturnUser() {
        User testUser = new User();
        testUser.setName("User Test Jpa");
        
        this.entityManager.persist(testUser);
        User user = this.repository.findByName("User Test Jpa");
        
        Assert.isTrue(user.getId() == testUser.getId());
        Assert.isTrue(user.getName().equals(testUser.getName()));
    }
}
