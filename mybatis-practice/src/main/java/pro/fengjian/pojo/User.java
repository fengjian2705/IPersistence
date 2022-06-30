package pro.fengjian.pojo;

import java.util.List;

public class User {

    private Integer id;
    private String username;

    private List<Order> order_list;
    private List<Role> role_list;

    public List<Role> getRole_list() {
        return role_list;
    }

    public void setRole_list(List<Role> role_list) {
        this.role_list = role_list;
    }

    public List<Order> getOrder_list() {
        return order_list;
    }

    public void setOrder_list(List<Order> order_list) {
        this.order_list = order_list;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", order_list=" + order_list +
                ", role_list=" + role_list +
                '}';
    }
}
