package booking;

import org.json.JSONObject;

import java.io.Serializable;

public class Image implements Serializable {
    private String address;

    public Image() {
    }
    public Image(JSONObject obj){
        this.address = obj.getString("roomImage");

    }
    public Image(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Image{" +
                "address='" + address + '\'' +
                '}';
    }
}
