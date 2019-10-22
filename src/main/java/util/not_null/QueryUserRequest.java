package util.not_null;


import com.sun.istack.internal.NotNull;

public class QueryUserRequest extends BaseRequest {
    @NotNull
    private String name;
    @NotNull
    private Integer age;
    private Boolean gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }
}
