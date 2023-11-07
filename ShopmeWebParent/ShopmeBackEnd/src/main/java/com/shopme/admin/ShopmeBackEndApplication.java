package com.shopme.admin;

import com.shopme.admin.user.UserRepository;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

@SpringBootApplication
@EntityScan({"com.shopme.common.entity","com.shopme.admin.repository"})
public class ShopmeBackEndApplication {

	public static void main(String[] args) {

		SpringApplication.run(ShopmeBackEndApplication.class, args);
	}


}
