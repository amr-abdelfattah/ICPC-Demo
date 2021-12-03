package edu.baylor.swe.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;


@Service
public class ReportSender {
	@Autowired
	private JmsTemplate jms;
	private String destinationName = "report.team.queue";
	
	public ReportSender(JmsTemplate jms) {
		this.jms = jms;
	}

	public void sendMessage(String messageData) {
		jms.send(destinationName, session -> session.createObjectMessage(messageData));
	}
}