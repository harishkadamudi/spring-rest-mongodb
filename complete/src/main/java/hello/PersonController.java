package hello;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

	@Autowired
	private PersonRepository repository;

	@Autowired
	MongoTemplate mongoTemplate;

	@RequestMapping(value = "/people/search", method = RequestMethod.POST)
	public List<Person> getPeople(@Param("name") String name) {
		if (StringUtils.isEmpty(name))
			return repository.findAll();
		else {
			List<Person> findByLastName = repository.findByLastName(name);
			if (findByLastName.size() == 0)
				throw new UserNotFoundException(name);
			return repository.findByLastName(name);
		}
	}

	@RequestMapping(value = "/people/search", method = RequestMethod.GET)
	public List<Person> getAllPeople() {
		return repository.findAll();
	}

	@RequestMapping(value = "/people/save", method = RequestMethod.POST, consumes = "application/*")
	public Person savePeople(@RequestBody Person person) {
		return repository.save(person);
	}

	@RequestMapping(value = "/people/update", method = RequestMethod.GET, params = { "name", "updateName" })
	public Person updatePeople(@RequestParam("name") String oldName, @RequestParam("updateName") String firstName) {
		Person person = mongoTemplate.findOne(Query.query(Criteria.where("firstName").is(oldName)), Person.class);
		person.setFirstName(firstName);
		return repository.save(person);
	}
}
