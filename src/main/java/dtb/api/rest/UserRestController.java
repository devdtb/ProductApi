package dtb.api.rest;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dtb.api.model.User;
import dtb.api.repository.UserRepository;
import dtb.api.service.UserService;

@RestController
@RequestMapping(value = "/api/v1/")
class UserRestController {
    
	@Autowired
    UserService userService;  //Service which will do all data retrieval/manipulation work
  
	@Autowired
	UserRepository userRepository;
	
	private final Log log = LogFactory.getLog(UserRestController.class);
	
	
    //-------------------Endpoind description--------------------------------------------------------
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> userEndpoint() {
    	
    	if(log.isInfoEnabled()){
    		log.info("api endpoint");
    	}
    	
        return new ResponseEntity<String>("User endpoint", HttpStatus.OK);
    }
      
    //-------------------Retrieve All Users--------------------------------------------------------
    @RequestMapping(value = "/user/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
  
  
    //-------------------Retrieve Single User--------------------------------------------------------
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
    	if(log.isInfoEnabled()){
    		log.info("Fetching User with id " + id);
    	}
    	
        User user = userRepository.findOne(id);
        if (user == null) {
        	if(log.isInfoEnabled()){
        		log.info("User with id " + id + " not found");
        	}

            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/nq/{name}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> getUserByName(@PathVariable("name") String name) {
    	if(log.isInfoEnabled()){
    		log.info("Fetching User with name " + name);
    	}
    	
        User user = userRepository.findByName(name);
        if (user == null) {
        	if(log.isInfoEnabled()){
        		log.info("User with name " + name + " not found");
        	}

            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
  
    @RequestMapping(value = "/user/rq/{name}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> getUserByNameRepository(@PathVariable("name") String name) {
    	if(log.isInfoEnabled()){
    		log.info("Fetching User with name " + name);
    	}
    	
        User user = userRepository.findByNameRepositoryQuery(name);
        if (user == null) {
        	if(log.isInfoEnabled()){
        		log.info("User with name " + name + " not found");
        	}

            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/naq/{name}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> getUserByNameNativ(@PathVariable("name") String name) {
    	if(log.isInfoEnabled()){
    		log.info("Fetching User with name " + name);
    	}
    	
        User user = userRepository.nativFindByName(name);
        if (user == null) {
        	if(log.isInfoEnabled()){
        		log.info("User with name " + name + " not found");
        	}

            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
      
    //-------------------Create a User--------------------------------------------------------
    @RequestMapping(value = "/user/", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
    	if(log.isInfoEnabled()){
    		log.info("Creating User " + user.getName());
    	}
  
        if (userRepository.findOne(user.getId()) != null) {
        	
        	if(log.isInfoEnabled()){
        		log.info("A User with name " + user.getName() + " already exist");
        	}

            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
  
        userRepository.saveAndFlush(user);
  
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
  
      
    //------------------- Update a User --------------------------------------------------------
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
    	if(log.isInfoEnabled()){
    		log.info("Updating User " + id);
    	}
          
        User currentUser = userRepository.findOne(id);
          
        if (currentUser==null) {
        	if(log.isInfoEnabled()){
        		log.info("User with id " + id + " not found");
        	}

            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
  
        currentUser.setName(user.getName());
          
        userRepository.saveAndFlush(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }
  
    //------------------- Delete a User --------------------------------------------------------
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
    	if(log.isInfoEnabled()){
    		log.info("Fetching & Deleting User with id " + id);
    	}
  
        User user = userRepository.findOne(id);
        if (user == null) {
        	if(log.isInfoEnabled()){
        		log.info("Unable to delete. User with id " + id + " not found");
        	}

            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
  
        userRepository.delete(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

}
