package inventory;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
public class InventoryController {
	private static ArrayList<String> itemsIdList = new ArrayList<>();

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
				//itemsIdList.add(rs.getString("ItemId"));
            }

            //******************* Attempt to Connect to Item-Service through Rest API *******************
			// Not working: Connection Refused
//            RestTemplate restTemplate = new RestTemplate();
//
//			String inventory ="";
//			// for each item id, call Items-service and get details
//			for(String id : itemsIdList){
//				Item item = restTemplate.getForObject("http://localhost:9001/item?id=" + id, Item.class);
//				inventory += item.getItem();
//			}			

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
