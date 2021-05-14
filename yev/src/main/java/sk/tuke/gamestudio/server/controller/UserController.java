package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Person;
import sk.tuke.gamestudio.service.UserService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {
    private Person loggedUser;

    @Autowired
    private UserService userService;
    private boolean isPerson;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login(@RequestParam(required = false)String login, @RequestParam(required = false) String password) {
        if(login != null && password != null ){
            isPerson = userService.login(login,password);
            if(isPerson){
                loggedUser = new Person(login,password);
                return "redirect:/pegsolitaire";
            }
        }

        return "redirect:/";
    }
    @RequestMapping("/register")
    public String register(@RequestParam(required = false)String login, @RequestParam(required = false) String password) {
        if(login != null && password != null){
            isPerson = userService.register(login,password);
            if(isPerson){
                loggedUser = new Person(login,password);
                return "redirect:/pegsolitaire";
            }
        }
        return "redirect:/";
    }


    @RequestMapping("/logout")
    public String logout() {
        loggedUser = null;
        return "redirect:/";
    }

    public Person getLoggedUser() {
        if(loggedUser == null) {
            return new Person("guest", "guest");
        }
        return loggedUser;
    }

    public boolean isLogged() {
        return loggedUser != null;
    }
}
