package controller;

import org.ektorp.*;
import org.ektorp.support.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class BlogPostRepository extends CouchDbRepositorySupport<User>{

    @Autowired
    public BlogPostRepository(@Qualifier("blogPostDatabase") CouchDbConnector db){
	super(User.class, db);
	initStandardDesignDocument();
    }
}
