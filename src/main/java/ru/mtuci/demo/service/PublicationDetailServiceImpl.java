package ru.mtuci.demo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtuci.demo.details.PublicationDto;
import ru.mtuci.demo.models.Publication;
import ru.mtuci.demo.repository.PublicationRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PublicationDetailServiceImpl implements PublicationService {

    private PublicationRepository publicationRepository;

    @Override
    public Optional<Publication> findOne(UUID id){
        return publicationRepository.findById(id);
    }

//    @Override
//    public Iterable<Publication> findByTitleContainingIgnoreCase(String title) throws Exception {
//        Iterable<Publication> searchResult = publicationRepository.findByTitleContainingIgnoreCase(title);
//        if(searchResult != null){
//            throw new Exception("Пост не найден");
//        }
//        return searchResult;
//    }

    @Override
    public PublicationDto addPublication(PublicationDto publicationDto){
        Publication publication = new Publication();
        publication.setTitle(publicationDto.getTitile());
        publication.setGenre(publicationDto.getGenre());
        publication.setLink(publicationDto.getLink());
        publication.setDate(publicationDto.getRequestTime());
        publication.setPublisherName(publicationDto.getPublisherName());
        publication.setDescription(publicationDto.getDescription());

        Publication savedPublication = publicationRepository.save(publication);

        PublicationDto savedPublicationDto = new PublicationDto();
        savedPublicationDto.setId(savedPublication.getId());
        savedPublicationDto.setTitile(savedPublication.getTitle());
        savedPublicationDto.setGenre(savedPublication.getGenre());
        savedPublicationDto.setLink(savedPublication.getLink());
        savedPublicationDto.setRequestTime(savedPublication.getDate());
        savedPublicationDto.setPublisherName(savedPublication.getPublisherName());
        savedPublicationDto.setDescription(savedPublication.getDescription());

        return savedPublicationDto;
    }
}
