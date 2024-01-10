package com.samuelr.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.samuelr.workshopmongo.domain.Post;
import com.samuelr.workshopmongo.domain.User;
import com.samuelr.workshopmongo.dto.AuthorDTO;
import com.samuelr.workshopmongo.dto.CommentDTO;
import com.samuelr.workshopmongo.repositories.PostRepository;
import com.samuelr.workshopmongo.repositories.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public void run(String... arg0) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		Post post1 = new Post(null, sdf.parse("08/01/2024"), "Let's study", "I going to study about Spring Boot", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("09/01/2024"), "Let's play", "I want to play CS", new AuthorDTO(alex));
		
		CommentDTO c1 = new CommentDTO("Boa viagem mano!", sdf.parse("2024/01/08"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Aproveite!", sdf.parse("2024/01/09"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Tenha um ótimo dia!", sdf.parse("2024/01/09"), new AuthorDTO(alex));
		
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().add(c3);
		
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().add(post1);
		alex.getPosts().add(post2);
		
		userRepository.saveAll(Arrays.asList(maria, alex));

		
	}

}
 