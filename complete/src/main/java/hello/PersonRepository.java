
package hello;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//@RepositoryRestResource(collectionResourceRel = "people", path = "people")
@Repository
public interface PersonRepository extends MongoRepository<Person, String> {
	
	List<Person> findByLastName(@Param("name") String name);

}
