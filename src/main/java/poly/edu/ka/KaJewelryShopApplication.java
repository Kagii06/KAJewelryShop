//package poly.edu.ka;

//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//
//import poly.edu.ka.config.StorageProperties;
//import poly.edu.ka.services.StorageService;
//
//@SpringBootApplication
//@EnableConfigurationProperties(StorageProperties.class)
//public class KaJewelryShopApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(KaJewelryShopApplication.class, args);
//	}
//	
//	@Bean
//	CommandLineRunner init(StorageService storageService)
//	{
//		return (args->{
//			storageService.init();
//		});
//	}
//
//}
package poly.edu.ka;

import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import poly.edu.ka.config.StorageProperties;
import poly.edu.ka.services.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class KaJewelryShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(KaJewelryShopApplication.class, args);
    }
    @Bean
	CommandLineRunner init(StorageService storageService) {
		return (args->{
			storageService.init();
		});
	}
}

