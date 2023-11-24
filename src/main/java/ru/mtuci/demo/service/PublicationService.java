package ru.mtuci.demo.service;

import ru.mtuci.demo.details.PublicationDto;
import ru.mtuci.demo.models.Publication;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public interface PublicationService {
    PublicationDto addPublication(PublicationDto publicationDto);
    Optional<Publication> findOne(UUID id);
    //Iterable<Publication> findByTitleContainingIgnoreCase(String title) throws Exception;

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        return new Predicate<T>() {
            private final Set<Object> seen = ConcurrentHashMap.newKeySet();

            @Override
            public boolean test(T t) {
                return seen.add(keyExtractor.apply(t));
            }
        };
    }
}
