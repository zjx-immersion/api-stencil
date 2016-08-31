package de.stytex.foobar.domain;


import java.io.Serializable;
import java.util.Objects;

/**
 * A Foo.
 */
//@Entity
//@Table(name = "foo")
public class Foo implements Serializable {

    private static final long serialVersionUID = 1L;

    public Foo() {
    }

    public Foo(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    //    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Column(name = "value")
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Foo foo = (Foo) o;
        if(foo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, foo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Foo{" +
            "id=" + id +
            ", value='" + value + "'" +
            '}';
    }
}
