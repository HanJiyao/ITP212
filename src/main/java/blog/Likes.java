package blog;

public class Likes {
    private int id;
    private String likeTitle;
    private int blogLikeId;



    public Likes(int id, String likeTitle,int blogLikeId){
        this.id = id;
        this.likeTitle = likeTitle;
        this.blogLikeId = blogLikeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLikeTitle() {
        return likeTitle;
    }

    public void setLikeTitle(String likeTitle) {
        this.likeTitle = likeTitle;
    }

    public int getBlogLikeId() {
        return blogLikeId;
    }

    public void setBlogLikeId(int blogLikeId) {
        this.blogLikeId = blogLikeId;
    }

    @Override
    public String toString(){
        return "LikedPosts [likeId=" + id + ", likeTitle=" + likeTitle +  ", blogLikeId=" + blogLikeId +  "]";
    }
}

