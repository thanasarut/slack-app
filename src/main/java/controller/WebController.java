package controller;

import bean.Contact;
import bean.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import service.ContactService;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class WebController {

    @Autowired
    private ContactService contactService;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping(value = "/contact/{nickname}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Contact getContact(@PathVariable String nickname) {
        return contactService.getContact(nickname);
    }
}
