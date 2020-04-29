package com.hit.edu;


import com.hit.edu.auth.AuthUserDetail;
import com.hit.edu.dao.UserDetailMaper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityLoginApplicationTests {

	@Resource
	UserDetailMaper userDetailMaper;

	@Test
	public void contextLoads(){
		AuthUserDetail authUserDetail = userDetailMaper.findByUserName("admin");
		System.out.println(authUserDetail);
	}

}
