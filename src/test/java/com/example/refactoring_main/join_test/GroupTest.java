package com.example.refactoring_main.join_test;

import com.example.refactoring_main.repository.GroupRepository;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.StopWatch;

@SpringBootTest
@Transactional
@Rollback(false)
public class GroupTest {

    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void testGroup() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        groupRepository.findAll().forEach(System.out::println);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());

    }
}
