package blog;

import java.util.*;
import java.sql.Date;
import java.sql.Time;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;

@ManagedBean
public class Blog implements Serializable{
    private int id;
    private String blogTitle;
    private String blogContent;
    private String blogCategory;
    private String blogPoster;
    private Date blogDate;



    List<String> categoryOptions;

    public Blog(){
        categoryOptions = new ArrayList<>();

        categoryOptions.add("Baking");
        categoryOptions.add("Dessert");
        categoryOptions.add("Food Review");
        categoryOptions.add("Healthy Food");
        categoryOptions.add("Restaurant");
        categoryOptions.add("Snacks");


    }

    public Blog(int id, String blogTitle, String blogContent, String blogCategory, String blogPoster, Date blogDate){
        this.id = id;
        this.blogTitle = blogTitle;
        this.blogContent = blogContent;
        this.blogCategory = blogCategory;
        this.blogPoster = blogPoster;
        this.blogDate = blogDate;
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

    public String getBlogCategory() {
        return blogCategory;
    }

    public void setBlogCategory(String blogCategory) {
        this.blogCategory = blogCategory;
    }

    public List<String> getCategoryOptions() {
        return categoryOptions;
    }

    public void setCategoryOptions(List<String> categoryOptions) {
        this.categoryOptions = categoryOptions;
    }

    public String getBlogPoster() {
        return blogPoster;
    }

    public void setBlogPoster(String blogPoster) {
        this.blogPoster = blogPoster;
    }

    public Date getBlogDate() {
        return blogDate;
    }

    public void setBlogDate(Date blogDate) {
        this.blogDate = blogDate;
    }


    //hmm
    @Override
    public String toString(){
        return "BlogPost [blogId=" + id + ", blogTitle=" + blogTitle + ", blogContent=" + blogContent + ", blogCategory=" + blogCategory
                + ", blogPoster=" + blogPoster + ", blogDate=" + blogDate + "]";
    }
}
