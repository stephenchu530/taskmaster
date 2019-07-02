package com.cf.taskmaster;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TaskmasterApplication.class)
@WebAppConfiguration
@ActiveProfiles("local")
//@TestPropertySource(properties = {
//		"amazon.dynamodb.endpoint=http://localhost:8000/",
//		"amazon.aws.accesskey=test1",
//		"amazon.aws.secretkey=test231" })
public class TaskmasterApplicationTests {

}