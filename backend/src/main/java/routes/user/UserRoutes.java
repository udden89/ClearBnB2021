package routes.user;

import application.user.UserAccess;
import express.Express;
import models.User;
import application.Repositories;
import repositories.UserRepository;
import utils.HashPassword;

import java.util.Map;
import java.util.Optional;

import static nosqlite.Database.collection;

public class UserRoutes {

    private UserAccess userAccess;

    public UserRoutes(Express app, UserRepository userRepository) {

        userAccess = new UserAccess(userRepository);

        app.get("/user", ((req, res) -> {

            Optional<User> optUser = userRepository.findById(1);
            User user = optUser.get();

            res.json(user);
        }));

        app.post("/api/registerUser", (req, res) -> {   //Create user
            User user = userAccess.createNewUser(req.body(User.class));
            req.session("current-user", user);
            res.json(user);
        });

        app.post("/api/login", (req, res) -> {
            User user = userAccess.loginUser(req.body(User.class));
            if( user != null) {
                req.session("current-user", user);
                res.json(user);
            }
            else{
                res.json(Map.of("Error", "Logindetails failed"));}
        });

        app.get("/api/whoami", (req, res)-> {   //Control logged in user
            res.json(req.session("current-user"));
        });

        app.get("/api/logout",(req,res)->{
            req.session("current-user", null);

            res.json(Map.of("Ok", "Logged out"));
        });

    }
}