package example;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@ApplicationPath("/api")
@Path("/restServices")
public class MyRestfulService extends Application {

    // http://localhost:8080/MyWebServices/index.html
    // http://localhost:8080/MyWebServices/api/restServices/listStudents
    // @Produces = MIME media types or representations a resource can produce and send back to the client
    // @Consumes = MIME media types of representations a resource can accept, or consume, from the client
    @GET
    @Path("/plain") //http://localhost:8080/MyWebServices/api/restServices/plain
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "Hello Jersey Plain";
    }

    // This method is called if XML is requested
    @GET
    @Path("/xml") //http://localhost:8080/MyWebServices/api/restServices/xml
    @Produces(MediaType.TEXT_XML)
    public String sayXMLHello() {
        return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
    }

    // This method is called if HTML is requested
    @GET
    @Path("/html") //http://localhost:8080/MyWebServices/api/restServices/html
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "<html> " + "<title>" + "Hello Jersey" + "</title>"
                + "<body><h1>" + "Hello Jersey HTML" + "</h1></body>" + "</html> ";
    }

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createStudent(@FormParam("adminNum") String adminNum,
							 @FormParam("name") String name) {
    	return Response.status(200).entity("Admin Num: " + adminNum + " name: " + name).build();
    	// return a response object 200 = ok success, and the msg of the admin num + name
	}

    @GET
    @Path("/listStudents") //http://localhost:8080/MyWebServices/api/restServices/listStudents
    @Produces(MediaType.APPLICATION_JSON) //text_xml for returning xml
    public List<Student> getAllStudents(){
        List<Student> students = new ArrayList<Student>();
        students.add(new Student("Peter Lim", "123456X"));
        students.add(new Student("Tan Ling Chong", "123456S"));
        students.add(new Student("Wong Peng San", "4554451R"));
        students.add(new Student("Ng Hui Ling", "178988R"));
        students.add(new Student("Johnson Phua", "5466661R"));
        students.add(new Student("John Doe", "8787343R"));
        return students;
    }

    @POST
	@Path("/delete/{deleteId}")
	@Produces(MediaType.APPLICATION_JSON) //return json
	public ArrayList<Student> deleteStudent(@PathParam("deleteId") String deleteId) {
    	System.out.println("admin num print " + deleteId);
		ArrayList<Student> listOfStudentsToDelete = (ArrayList<Student>) getAllStudents();
		Iterator<Student> it = listOfStudentsToDelete.iterator();
		while (it.hasNext()) {
			Student student = it.next();
			if (student.getAdminNum().equals(deleteId)) {
				it.remove();
			}
		}
        return listOfStudentsToDelete;
	}

}
