package routes;

import application.RatingLogic;
import application.Repositories;
import dtos.DeleteRatingDTO;
import dtos.createNewRatingFromFrontendDTO;
import entityDO.User;
import express.Express;
import mapper.RatingMapper;

import static java.lang.Integer.parseInt;

public class RatingRoutes {

    Express app;
    Repositories repositories;
    RatingLogic ratingLogic;
    RatingMapper ratingMapper = new RatingMapper();

    public RatingRoutes(Express app, Repositories repositories) {
        this.app = app;
        this.repositories = repositories;
        this.ratingLogic = new RatingLogic(repositories);
        getAllRatingsOfUser();
        getAvgRatingOfUser();
        checkIfThereIsAnyRatingToFill();
        createANewRating();
        deleteRating();
    }

    public void getAllRatingsOfUser(){
        app.get("/rest/rating/:userID", (req, res) ->{
            int userID = parseInt(req.params("userID"));
            System.out.println(userID);
            res.json(ratingLogic.getAllRatingsOfUser(userID));
        });
    }

    public void getAvgRatingOfUser(){
        app.get("/rest/avg-rating/:userID", (req, res) ->{
            int userID = parseInt(req.params("userID"));
            res.json(ratingLogic.getAvgRatingsOfUser(userID));
        });
    }

    public void checkIfThereIsAnyRatingToFill(){
        app.get("/rest/check-if-there-is-ratings-to-fill/", (req, res) ->{
            User currentUser = req.session("current-user");
            res.json(ratingLogic.checkIfThereIsAnyRatingToFill(currentUser));
        });
    }

    public void createANewRating(){
        app.post("/rest/createNewRating", (req, res) -> {
            createNewRatingFromFrontendDTO dto = req.body(createNewRatingFromFrontendDTO.class);
            ratingLogic.createNewRating(dto);
        });
    }

    //Maybe this should update all rating fields to "deleted" instead of deleting the whole row? //Mac
    public void deleteRating(){
        app.delete("/api/delete-rating", (req, res) -> {
            DeleteRatingDTO dto = req.body(DeleteRatingDTO.class);
            res.json(ratingLogic.deleteRating(dto.getId()));
        });
    }
}
