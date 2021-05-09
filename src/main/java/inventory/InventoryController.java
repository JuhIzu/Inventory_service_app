package inventory;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
public class InventoryController {

  	private static String getUserInventory(int userid) 
	{
		Connection c = null;
		try 
		{
			// get all items id for the selected user id
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:UserItems.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			String sql = "SELECT Items.Name FROM Items JOIN UserItems ON UserItems.ItemId = Items.Id WHERE UserItems.UserId = ?";
			PreparedStatement pstmt  = c.prepareStatement(sql);

            // set the value
            pstmt.setInt(1,userid);
			
            //
            ResultSet rs  = pstmt.executeQuery();

			String inventory ="";
            
            // loop through the result set
            while (rs.next()) {
                inventory  += rs.getString("Name") + ", ";
            }		

	return inventory;

        }	
	catch ( Exception e ) {
		return e.toString();
	}
   }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping("/inventory")
    public String inventory(@RequestParam(value = "userid", required = false) String idString) {

		int uid = Integer.parseInt(idString);
        String inventory = getUserInventory(uid);
        return inventory;
    }
}
