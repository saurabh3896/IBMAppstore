package controller;

import org.codehaus.jackson.annotate.*;
import org.ektorp.support.*;
import org.joda.time.*;

@JsonIgnoreProperties("typeName")
public class User extends CouchDbDocument{

    private static final long serialVersionUID = 1L;
    
    /**
     * @TypeDiscriminator is used to mark properties that makes this class documents unique in the database. 
     */
    @TypeDiscriminator
    private String title;
    //private String body;
    //private List<String> tags;
    private DateTime dateCreated;

    public DateTime getDateCreated(){
        return dateCreated;
    }

    public void setDateCreated(DateTime dateCreated){
        this.dateCreated = dateCreated;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    @Override
    public void setRevision(String s){
        // downstream code does not like revision set to emtpy string, which Spring does when binding
        if(s != null && !s.isEmpty())
            super.setRevision(s);
    }
}
