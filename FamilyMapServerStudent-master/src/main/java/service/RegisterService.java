package service;

import request.RegisterRequest;
import result.RegisterResult;
import model.AuthToken;
import model.Person;
import model.User;

public class RegisterService {

    /** this method registers a user by creating a user, as well as a person, tree and an authtoken for that user
     * @param request is the info needed to create model objects for use in dao classes
     * @return the registered user and their info, as well as whether the request succeeded or fail
     */
    RegisterResult register(RegisterRequest request) {
        return null;
    }

}
