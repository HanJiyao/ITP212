package jiyao.items;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;

@ManagedBean
public class Item {
    private int id;
    private String name;
    private String desc;
    private String type;
    private float price;
    private int quantity;
    private float discount;
    private String user;
    private Date created;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    };

    public ArrayList itemList;
    private Part uploadedFile;
    private String folder = "/home/han/Project/ITP212/web/resources/food/";

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }


    @PostConstruct
    public void init(){
        itemList = ItemsDbUtil.getItemsList();
    }
    public ArrayList getItemList(){
        return itemList;
    }
    public ArrayList getUserItemList(String userEmail){
        return ItemsDbUtil.getUserItemsList(userEmail);
    }
    public String createItem(Item newItemObj,String userEmail){
        System.out.print("Creating item");
        return ItemsDbUtil.createItem(newItemObj,userEmail);
    }
    public String getItem(int itemId){
        return ItemsDbUtil.getItem(itemId);
    }
    public String editItem(int itemId){
        return ItemsDbUtil.editItem(itemId);
    }
    public String updateItem(Item updateItemObj, String imageName){
        return ItemsDbUtil.updateItem(updateItemObj, imageName);
    }
    public String deleteItem(int itemId, String imageName){
        return ItemsDbUtil.deleteItem(itemId, imageName);
    }
    public String UpdateImage(){
        String newImageName = null;
        try (InputStream input = uploadedFile.getInputStream()) {
            String fileName = uploadedFile.getSubmittedFileName();
            newImageName = fileName;
            Files.copy(input, new File(folder, fileName).toPath());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return newImageName;
    }

    public ActionListener saveFile() {
        return new ActionListener() {
            @Override
            public void processAction(ActionEvent actionEvent) throws AbortProcessingException {
                try (InputStream input = uploadedFile.getInputStream()) {
                    String fileName = uploadedFile.getSubmittedFileName();
                    System.out.print(fileName);
                    image = fileName;
                    Files.copy(input, new File(folder, fileName).toPath());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
