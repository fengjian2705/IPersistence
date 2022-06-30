package pro.fengjian.pojo;

public class Order {

    private Integer id;
    private Integer uid;
    private Double total;
    private String order_time;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    @Override
    public String toString() {
        return "Order[" +
                "id=" + id +
                ", uid=" + uid +
                ", total=" + total +
                ", order_time='" + order_time + '\'' +
                ", user=" + user +
                ']';
    }
}
