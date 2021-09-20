package application;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import repositories.*;

public class Repositories {

    //Is this best praxis of having a collection class ??

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ClearBnB2021");
    jakarta.persistence.EntityManager entityManager = entityManagerFactory.createEntityManager();

    ListingRepository listingRepository = new ListingRepository(entityManager);
    UserRepository userRepository = new UserRepository(entityManager);
    AmenitiesRepository amenitiesRepository = new AmenitiesRepository(entityManager);
    CurrentChatRepository currentChatRepository = new CurrentChatRepository(entityManager);
    ChatMessageRepository chatMessageRepository = new ChatMessageRepository(entityManager);
    RatingRepository ratingRepository = new RatingRepository(entityManager);

    public ListingRepository getListingRepository() {
        return listingRepository;
    }

    public UserRepository user() {
        return userRepository;
    }

    public AmenitiesRepository getAmenitiesRepository() {
        return amenitiesRepository;
    }

    public CurrentChatRepository getCurrentChatRepository() {
        return currentChatRepository;
    }

    public ChatMessageRepository getChatMessageRepository() {
        return chatMessageRepository;
    }

    public RatingRepository getRatingRepository() {
        return ratingRepository;
    }
}