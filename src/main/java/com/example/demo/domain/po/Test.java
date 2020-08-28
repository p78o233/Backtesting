package com.example.demo.domain.po;
/*
 * @author p78o2
 * @date 2020/8/28
 */

//测试表
public class Test {
    private Integer id;
    private String name;

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Test() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Test(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
