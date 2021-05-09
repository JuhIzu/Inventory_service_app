package inventory;

public class Item {

    private int _id;
    private String _item;

    public Item(){}

    public Item(int id, String item) {
        _id = id;
        _item = item;
    }

    public int getId() {
        return _id;
    }

    public String getItem(){
		return _item;
	}
}
