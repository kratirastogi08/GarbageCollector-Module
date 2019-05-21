package com.example.kratirastogi.garbagecollector;

class ListViewItemDTO {
    private String name,address,pay,id,state;

    public ListViewItemDTO(String name, String address,String pay,String id,String state) {
        this.name = name;
        this.address = address;
        this.pay=pay;
        this.id=id;
        this.state=state;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
