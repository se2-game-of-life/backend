package se2.group3.backend;

import org.springframework.stereotype.Service;
import se2.group3.backend.dto.Player;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SessionService {

    private final AtomicLong idGenerator = new AtomicLong();
    private final ConcurrentHashMap<Long, Session> sessionMap = new ConcurrentHashMap<>();

    public Session createSession(Player host) {
        long sessionId = idGenerator.getAndIncrement();
        Session newSession = new Session(sessionId, host);
        sessionMap.put(sessionId, newSession);
        return newSession;
    }

    public Session joinSession(long sessionId, Player player) throws Exception {
        Session session = sessionMap.get(sessionId);

        if(session == null) {
            throw new Exception("Session could not be found!");
        }
        if(!session.addPlayer(player)) {
            throw new Exception("Player could not be added to Session!");
        }

        return session;
    }

    public boolean leaveSession(long sessionId, Player player) {
        Session session = sessionMap.get(sessionId);
        if(session != null) {
            return session.removePlayer(player);
        }
        return false;
    }

    public Session getSession(long sessionId) {
        return sessionMap.get(sessionId);
    }

    public Collection<Session> getAll() {
        return sessionMap.values();
    }
}
