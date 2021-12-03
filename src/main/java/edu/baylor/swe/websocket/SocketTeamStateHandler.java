package edu.baylor.swe.websocket;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.baylor.swe.jms.TeamStatusListener;
import edu.baylor.swe.models.Applicant;
import edu.baylor.swe.models.Team;
import edu.baylor.swe.models.Team.State;
import edu.baylor.swe.services.AmazonService;
import edu.baylor.swe.services.TeamService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@AllArgsConstructor
public class SocketTeamStateHandler extends TextWebSocketHandler {
	
	private List<WebSocketSession> sessions = new ArrayList<>();
	
	@Autowired
	private AmazonService amazonService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
		
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//		super.afterConnectionEstablished(session);
		System.err.println("Connection Established");
		sessions.add(session);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.err.println("Recieve "+ message);
		String messageStr = message.getPayload();
		try {
			TestModel modelObject = objectMapper.readValue(messageStr, TestModel.class);
			amazonService.registration(modelObject.getApplicationId(), modelObject.getApplicant());
			sendRefreshMessage();
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} 
	}
	
//	@Override
//	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
//		String messageStr = StandardCharsets.UTF_8.decode(message.getPayload()).toString();
//		System.err.println("Recieved "+ messageStr);
//		try {
//			TeamStateDTO team = objectMapper.readValue(messageStr, TeamStateDTO.class);
//			teamService.changeTeamState(team.getId(), team.getState());
//			sendRefreshMessage();
//			
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//		super.afterConnectionClosed(session, status);
		sessions.remove(session);
	}
	
	public void sendRefreshMessage() {
		for (WebSocketSession webSocketSession : sessions) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("refreshData", true);
			try {
				webSocketSession.sendMessage(new TextMessage(jsonObject.toString()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.err.println("Sent "+ jsonObject.toString());
		}
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class TeamStateDTO {
		private long id;
		private String name;
		private State state;
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class TestModel {
		private Applicant applicant;
		private long applicationId;
//		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
//		private Date dateOfBirth;
	}
}
