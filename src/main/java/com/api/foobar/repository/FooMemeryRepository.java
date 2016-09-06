package com.api.foobar.repository;

import com.api.foobar.domain.Foo;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by on 01.09.16.
 *
 * @author Jianxin Zhong
 *
 * Spring Data JPA repository for the Foo entity.
 */
@Component
public class FooMemeryRepository {

    private  List<Foo> fooStore;

    public FooMemeryRepository() {
        fooStore = newArrayList();
    }

    public Foo save(Foo foo) {
        Foo foo1 = new Foo(new Long(new Random(123).nextLong()), foo.getValue());
        fooStore.add(foo1);
        return foo1;
    }

    public List<Foo> findAll() {
        return fooStore.stream().collect(Collectors.toList());
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
