package coms309.Posts;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

    private int user_id;

    @ManyToOne
	@JoinColumn(name = "posts_id")
	@JsonIgnore
	private Posts post;

    public Likes(int user_id){
        this.user_id = user_id;
    }
	public Likes(){
	}

	public int getId(){
		return this.id;
	}
    public int getUser_id(){
        return this.user_id;
    }
    public int getPostID(){
        return this.post.getId();
    }
		public void setId(int id){
		this.id = id;
	}
    public void setUser_id(int user_id){
        this.user_id = user_id;
    }
    public void setPost(Posts p){
        this.post = p;
    }
}
