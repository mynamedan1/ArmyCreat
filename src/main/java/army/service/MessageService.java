package army.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import army.db.dao.MessageMapper;
import army.db.pojo.Message;

@Service
public class MessageService {
	@Autowired
	private MessageMapper messageMapper;

	public boolean addMessage(Message message) {
		return messageMapper.insert(message) == 1 ? true : false;
	}
	
	public boolean updateMessage(Message message) {
		return messageMapper.updateByPrimaryKeySelective(message)==1?true:false;
	}
	public boolean deleteMessage(int  id) {
		return messageMapper.deleteByPrimaryKey(id)==1?true:false;
	}
	
	public List<Message> getMessage(int userId,int type){
		return messageMapper.getMessage(userId,type);
	}
}
