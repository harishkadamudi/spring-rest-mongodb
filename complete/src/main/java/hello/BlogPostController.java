package hello;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogPostController {
	@Autowired
	private BlogPostRepository blogPostRepsotiry;

	@Autowired
	MongoOperations operations;

	@Autowired
	MongoTemplate mongoTemplate;

	@RequestMapping(value = "/blog/searchInBlogExact", method = RequestMethod.GET, consumes = "application/*")
	public List<BlogPost> searchExact(@RequestParam("search") String searchTerm) {
		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingPhrase(searchTerm);
		List<BlogPost> blogPosts = blogPostRepsotiry.findAllBy(criteria);
		return blogPosts;
	}
	
	@RequestMapping(value = "/blog/searchInBlog", method = RequestMethod.GET, consumes = "application/*")
	public List<BlogPost> search(@RequestParam("search") String searchTerm) {
		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(searchTerm);
		List<BlogPost> blogPosts = blogPostRepsotiry.findAllBy(criteria);
		return blogPosts;
	}

	@RequestMapping(value = "/blog/save", method = RequestMethod.POST, consumes = "application/*")
	public BlogPost saveBlog(@RequestBody BlogPost criteria) {
		return blogPostRepsotiry.save(criteria);
	}
}
