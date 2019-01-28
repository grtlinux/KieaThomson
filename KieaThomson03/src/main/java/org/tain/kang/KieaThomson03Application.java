package org.tain.kang;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tain.kang.thomson.KieaThomsonMain;

@SpringBootApplication
public class KieaThomson03Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KieaThomson03Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("#################### START ###################");

		System.out.println(">>>>> KANG: ");
		
		KieaThomsonMain.test02(new String[] {});
		
		System.out.println("#################### START ###################");
	}
}


