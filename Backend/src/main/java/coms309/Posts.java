package coms309;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String spotLink;
	private String description;
	private int ownerID;

	public Posts(int ownerID, String description, String spotLink){
		this.spotLink = spotLink;
		this.description = description;
		this.ownerID = ownerID;
	}

	public Posts(){
	}

	public int getId(){
		return this.id;
	}
	public String getDescription(){
		return this.description;
	}
	public String getSpotLink(){
		return this.spotLink;
	}
	public int getownerID(){
		return this.ownerID;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public void setSpotLink(String spotLink){
		this.spotLink = spotLink;
	}
	public void setOwnerID(int ownerID){
		this.ownerID = ownerID;
	}
	public void setId(int id){
		this.id = id;
	}
}
