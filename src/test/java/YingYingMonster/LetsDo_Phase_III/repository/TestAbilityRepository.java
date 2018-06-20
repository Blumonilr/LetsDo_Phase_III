package YingYingMonster.LetsDo_Phase_III.repository;

import YingYingMonster.LetsDo_Phase_III.entity.Ability;
import YingYingMonster.LetsDo_Phase_III.entity.role.User;
import YingYingMonster.LetsDo_Phase_III.repository.role.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestAbilityRepository {

    @Autowired
    AbilityRepository abilityRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void test_findByUser() {
        User user = userRepository.findById(3);
        List<Ability> abilities = abilityRepository.findByUser(user);
        assertNotNull(abilities);
        for (Ability ability : abilities) {
            System.out.println(ability.getLabel().getName());
        }
    }
}
