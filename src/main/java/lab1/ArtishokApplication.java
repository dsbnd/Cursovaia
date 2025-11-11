package lab1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ArtishokApplication {

	public static void main(String[] args) {
		System.out.println("Hello suchka");

		System.out.println("Делаю изменения, сучка!");
		// привет всем!
		System.out.println("я первая сучк");

		SpringApplication.run(ArtishokApplication.class, args);
	}

}
