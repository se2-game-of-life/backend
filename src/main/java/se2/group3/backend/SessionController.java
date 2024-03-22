package se2.group3.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import se2.group3.backend.dto.JoinSessionRequest;
import se2.group3.backend.dto.Player;

@Controller
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @MessageMapping("/lobby/create")
    @SendTo("/client/lobbies")
    public Session createSession(Player player) {
        return sessionService.createSession(player);
    }

    @MessageMapping("/lobby/join")
    @SendTo("/")
    public Session joinSession(JoinSessionRequest request) {
        Session session = new Session(0, new Player("temp"));
//        try {
//            session = sessionService.joinSession(request.getSessionID(), new Player(request.getPlayerName()));
//        } catch (Exception e) {
//
//        }
        return session;
    }



}
