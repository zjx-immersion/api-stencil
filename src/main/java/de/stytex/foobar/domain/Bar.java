package de.stytex.foobar.domain;

/**
 * Created by on 01.09.16.
 *
 * @author Jianxin Zhong
 */
public class Bar {
    private long id;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
