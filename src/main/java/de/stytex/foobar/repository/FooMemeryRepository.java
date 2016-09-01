package de.stytex.foobar.repository;

import de.stytex.foobar.domain.Foo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.stereotype.Component;

/**
 * Spring Data JPA repository for the Foo entity.
 */
@Component
public class FooMemeryRepository {

    private static List<Foo> fooStore = new ArrayList();

    public Foo save(Foo foo) {
        Foo foo1 = new Foo(new Long(new Random(123).nextLong()), foo.getValue());
        fooStore.add(foo1);
        return foo1;
    }

    public List<Foo> findAll() {
        return fooStore;
    }

    public Foo findOne(Long id) {
        Optional<Foo> opFoo = fooStore
            .stream()
            .filter(foo -> foo.getId().equals(id)).findFirst();
        return opFoo.isPresent() ? opFoo.get() : null;
    }

    public void delete(Long id) {
        Foo foo = findOne(id);
        if (Optional.ofNullable(foo).isPresent()) {
            fooStore.remove(foo);
        }
    }

    public void saveAndFlush(Foo foo) {
        fooStore.clear();
        fooStore.add(foo);
    }
}