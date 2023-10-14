package ru.mtuci.demo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtuci.demo.details.PublicationDto;
import ru.mtuci.demo.models.Publication;
import ru.mtuci.demo.repository.PublicationRepository;

@Service
@AllArgsConstructor
public class PublicationDetailServiceImpl implements PublicationService {

    private PublicationRepository publicationRepository;

    @Override
    public PublicationDto addPublication(PublicationDto publicationDto){
        Publication publication = new Publication();
        publication.setTitle(publicationDto.getTitile());
        publication.setGenre(publicationDto.getGenre());
        publication.setLink(publicationDto.getLink());
        publication.setDate(publicationDto.getRequestTime());
        publication.setPublisherName(publicationDto.getPublisherName());

        Publication savedPublication = publicationRepository.save(publication);

        PublicationDto savedPublicationDto = new PublicationDto();
        savedPublicationDto.setId(savedPublication.getId());
        savedPublicationDto.setTitile(savedPublication.getTitle());
        savedPublicationDto.setGenre(savedPublication.getGenre());
        savedPublicationDto.setLink(savedPublication.getLink());
        savedPublicationDto.setRequestTime(savedPublication.getDate());
        savedPublicationDto.setPublisherName(savedPublication.getPublisherName());

        return savedPublicationDto;
    }
}
