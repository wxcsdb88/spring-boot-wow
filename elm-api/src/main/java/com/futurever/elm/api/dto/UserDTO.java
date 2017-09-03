package com.futurever.elm.api.dto;/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-09-03 19:05
 **/

/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-09-03 19:05
 **/
public class UserDTO {
    private Long id;
    private String username;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
