package blog;


import javax.faces.bean.ManagedBean;

@ManagedBean
public class Blog {
    private int id;
    private String blogTitle;
    private String blogContent;

    public Blog(){

    }

    public Blog(int id, String blogTitle, String blogContent){
        this.id = id;
        this.blogTitle = blogTitle;
        this.blogContent = blogContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }

    //hmm
    @Override
    public String toString(){
        return "BlogPost [blogId=" + id + ", blogTitle=" + blogTitle + ", blogContent=" + blogContent + "]";
    }
}
