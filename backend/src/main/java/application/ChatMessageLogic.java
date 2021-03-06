package application;

import dtos.ChatMessageDTO;
import entityDO.ChatMessage;
import entityDO.CurrentChat;
import entityDO.User;
import mapper.ChatMessageMapper;
import java.util.List;

public class ChatMessageLogic {
    Repositories repositories;
    private ChatMessageMapper ms;

    public ChatMessageLogic(Repositories repositories) {
        this.repositories = repositories;
        this.ms = new ChatMessageMapper();
    }

    public ChatMessageLogic() {
    }

    public void createNewMessage(ChatMessageDTO dto, Integer roomID){
        User sender = repositories.getUserRepository().findUserById(dto.getSenderID());
        User receiver = repositories.getUserRepository().findUserById(dto.getReceiverID());
        CurrentChat chatRoom = repositories.getCurrentChatRepository().findById(roomID).get();
        ChatMessage newMess = ms.convertDTOToMessage(dto, sender, receiver, chatRoom);
        repositories.getChatMessageRepository().addChatMessage(newMess);
    }

    public List<ChatMessageDTO> getChatMessageDTOOfUser(int senderID, int receiverID){
        return ms.convertUserMessageToDTOList(repositories.getChatMessageRepository().findMessagesBySenderID(senderID, receiverID));
    }
}
