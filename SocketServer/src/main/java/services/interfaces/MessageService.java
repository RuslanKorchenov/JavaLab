package services.interfaces;

import context.Component;
import dao.repositories.MessageRepository;
import dto.MessageDto;

public interface MessageService extends Component {
    MessageDto message(String token, String text);
    MessageRepository getMessageRepository();
}

