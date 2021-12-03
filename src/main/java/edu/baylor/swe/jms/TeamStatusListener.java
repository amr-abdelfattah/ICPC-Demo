package edu.baylor.swe.jms;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.baylor.swe.models.Applicant;
import edu.baylor.swe.models.Team;
import edu.baylor.swe.models.Team.State;
import edu.baylor.swe.services.AmazonService;
import edu.baylor.swe.services.ContestService;
import edu.baylor.swe.services.TeamService;
import edu.baylor.swe.websocket.SocketTeamStateHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import edu.baylor.swe.services.ContestService.ContestCapacity;

@Component
public class TeamStatusListener {
//	private TeamService teamService;
//	private ContestService contestService;
//	private ReportSender sender;
	private SocketTeamStateHandler socketTeamState;
	private AmazonService amazonService;

	@Autowired
	public TeamStatusListener(AmazonService amazonService, SocketTeamStateHandler socketTeamState) {
		this.amazonService = amazonService;
		this.socketTeamState = socketTeamState;
	}

	@JmsListener(destination = "status.team.queue")
	@Transactional
	public void receiveOrder(String messageData) {
		try {
			System.err.println("HERE  " + messageData);
			ObjectMapper objectMapper = new ObjectMapper();
			TestModel modelObject = objectMapper.readValue(messageData, TestModel.class);
			
			amazonService.registration(modelObject.getApplicationId(), modelObject.getApplicant());
//			ContestCapacity capacity = changeTeamState(modelObject.getId(), modelObject.getState());
//			System.err.println("JMS:AFTER "+ capacity);
//			sendResponse(capacity);
//			sendUpdateMessage();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
//	@JmsListener(destination = "status.team.queue")
//	@Transactional
//	public void receiveOrder(String messageData) {
//		try {
//			String[] messageParts = messageData.split(",");
//			Long teamID = Long.valueOf(messageParts[0]);
//			State state = State.valueOf(messageParts[1]);
//			ContestCapacity capacity = changeTeamState(teamID, state);
//			System.err.println("JMS:AFTER "+ capacity);
//			sendResponse(capacity);
//			sendUpdateMessage();
//		} catch(Exception e) {
//			e.printStackTrace();
//			sendError();
//		}
//	}

	
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
