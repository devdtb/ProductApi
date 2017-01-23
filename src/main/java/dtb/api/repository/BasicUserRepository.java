package dtb.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dtb.api.model.User;

public interface BasicUserRepository extends JpaRepository<User, Long>{
	
	//named as the @NamedQuery(name = "User.findByName", query = "select u from User u where u.name = ?1")
	public User findByName(String name);
	
	@Query("select u from User u where u.name = :name")
	public User findByNameRepositoryQuery(@Param("name") String name);
	
	@Query(nativeQuery=true, value="SELECT * FROM USER WHERE NAME = ?")
	public User nativFindByName(String name);
}