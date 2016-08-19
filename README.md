# IBM AppStore

The project involves online portal for uploading files and generating reports on the basis of utility nature.

### Softwares and Dependencies

1. IDE : NetBeans 8.1 (Eclipse supported) 

2. Server : apache-tomcat-8.0.35

3. Spring security using embedded LDAP.

   3.1. Spring version : 3.0.5.RELEASE

   3.2. Spring security version : 3.0.5.RELEASE

4. Database : CochDB, Framwork provided by Ektorp(version : 1.2.2)

5. JDK version : 1.8

6. Test Dependencies : mockito-all-1.8.5.jar

7. Maven 3


### Getting Started

1. Host address : 'localhost:8080' , (port can be specified)

2. Login with your username and password (passwords and usernames are defined in an LDIF file with root = mojo , which can 
  be changed to IBM Bluepages.)
   
3. On the homepage the user will see all the utilities and the input files they take as input.

4. Hover over the utility the user want to use and a Choose File button and descripion of the utility will appear.

5. Click Choose File and select files to upload.

6. Uploaded file's name will appear at the top beside Upload button.

7. Click Upload to upload the file or Reset to reset the file, the user get confirmation message accroding to user's          choice.

8. After Report is ready you will get a download link to download, it will be a .zip file containing all the reports.


## File locations

1. All JSP files are in Web Pages/WEB-INF/jsp

2. All CSS and JS files are in Web Pages/resources/static


### How to Run
1. Open the project in IDE (NetBeans or Eclipse).

2. Set server to Apache Tomcat.

3. Build the project.

4. Deploy (or run).


## Authors

* **Saurabh Singh** 
* **Pratik Barule** 

## Acknowledgments

* **Manjit S.Sodhi**(*Project Manager @ IBM*)
* **Sanket Patil** 

