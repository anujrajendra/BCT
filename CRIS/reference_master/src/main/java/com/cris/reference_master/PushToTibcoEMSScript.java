package com.cris.reference_master;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import javax.jms.CompletionListener;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.Request;
import com.onwbp.adaptation.XPathExpressionHelper;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.service.ExportSpec;
import com.orchestranetworks.service.LoggingCategory;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.Procedure;
import com.orchestranetworks.service.ProcedureContext;
import com.orchestranetworks.service.ProcedureResult;
import com.orchestranetworks.service.ProgrammaticService;
import com.orchestranetworks.workflow.ScriptTaskBean;
import com.orchestranetworks.workflow.ScriptTaskBeanContext;

/**
*/
public class PushToTibcoEMSScript extends ScriptTaskBean {

	class ExportXMLProcedure implements Procedure {
		private Adaptation record;
		private String result;

		@Override
		public void execute(final ProcedureContext context) throws Exception {
			// TODO Auto-generated method stub

			final Request request = this.record.getContainerTable().createRequest();
			request.setXPathFilter(this.record.toXPathPredicateString());
			LoggingCategory.getWorkflow().info("=== XPath Predicate String ===" + this.record.toXPathPredicateString());
			// request.setXPathFilter(this.record.toXPathExpression());
			final ByteArrayOutputStream out = new ByteArrayOutputStream();

			final ExportSpec spec = new ExportSpec();
			spec.setRequest(request);
			spec.setDestinationStream(out);
			context.doExport(spec);
			try {
				this.result = out.toString(StandardCharsets.UTF_8.name());
			} catch (UnsupportedEncodingException e) {
				LoggingCategory.getWorkflow().info("===Inside catch block===" + e.getMessage());
				throw new Error(e);
			}

		}

		public Adaptation getRecord() {
			return this.record;
		}

		public String getResult() {
			return this.result;
		}

		public void setRecord(final Adaptation record) {
			this.record = record;
		}

	}

	class TibjmsCompletionListener implements CompletionListener {
		// Note: Use caution when modifying a message in a completion
		// listener to avoid concurrent message use.

		@Override
		public void onCompletion(final Message msg) {
			try {
				System.out.printf("Successfully sent message %s.\n", ((TextMessage) msg).getText());
				// System.err.printf("Successfully sent message %s.\n", ((TextMessage)
				// msg).getText());

			} catch (JMSException e) {
				System.out.printf("Error retrieving message text.");
				// System.err.println("Error retrieving message text.");
				e.printStackTrace(System.err);
			}
		}

		@Override
		public void onException(final Message msg, final Exception ex) {
			try {
				System.err.printf("Error sending message %s.\n", ((TextMessage) msg).getText());
			} catch (JMSException e) {
				System.err.println("Error retrieving message text.");
				e.printStackTrace(System.err);
			}

			ex.printStackTrace(System.err);
		}

	}

	/*-----------------------------------------------------------------------
	* Parameters
	*----------------------------------------------------------------------*/
	private String dataSpaceId;
	private String dataSetId;
	private String recordXPath;
	private String serverUrl;
	private String userName;
	private String password;

	private String name;

	private boolean useTopic;
	private boolean useAsync;

	/*-----------------------------------------------------------------------
	* Variables
	*----------------------------------------------------------------------*/
	Connection connection = null;

	Session session = null;

	MessageProducer msgProducer = null;

	Destination destination = null;

	@Override
	public void executeScript(final ScriptTaskBeanContext context) throws OperationException {
		// TODO Auto-generated method stub

		// Test the variables
		if (this.dataSpaceId == null) {
			throw OperationException.createError("dataSpaceId is null");
		}
		if (this.dataSetId == null) {
			throw OperationException.createError("dataSetId is null");
		}
		if (this.recordXPath == null) {
			throw OperationException.createError("recordXPath is null");
		}
		if (this.serverUrl == null) {
			throw OperationException.createError("serverUrl is null");
		}
		if (this.userName == null) {
			throw OperationException.createError("userName is null");
		}
		if (this.password == null) {
			throw OperationException.createError("password is null");
		}
		if (this.name == null) {
			throw OperationException.createError("name is null");
		}

		Repository repository = context.getRepository();
		AdaptationHome dataSpace = repository.lookupHome(HomeKey.forBranchName(this.dataSpaceId));
		Adaptation dataSet = dataSpace.findAdaptationOrNull(AdaptationName.forName(this.dataSetId));
		Adaptation recordToPush = XPathExpressionHelper.lookupFirstRecordMatchingXPath(dataSet, this.recordXPath);
		ProgrammaticService programmaticService = ProgrammaticService.createForSession(context.getSession(), dataSpace);
		// LoggingCategory.getWorkflow().info("===Push to EMS===" +
		// recordToPush.get(Path.parse("./Loco_Owning_Zone")));
		try {
			this.sendRecordData(recordToPush, programmaticService);
		} catch (JMSException ex) {
			// TODO Auto-generated catch block
			throw new RuntimeException(ex);
		}

	}

	public String getDataSetId() {
		return this.dataSetId;
	}

	public String getDataSpaceId() {
		return this.dataSpaceId;
	}

	public String getName() {
		return this.name;
	}

	public String getPassword() {
		return this.password;
	}

	public String getRecordXPath() {
		return this.recordXPath;
	}

	public String getServerUrl() {
		return this.serverUrl;
	}

	public String getUserName() {
		return this.userName;
	}

	public boolean isUseAsync() {
		return this.useAsync;
	}

	public boolean isUseTopic() {
		return this.useTopic;
	}

	public void setDataSetId(final String dataSetId) {
		this.dataSetId = dataSetId;
	}

	public void setDataSpaceId(final String dataSpaceId) {
		this.dataSpaceId = dataSpaceId;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setRecordXPath(final String recordXPath) {
		this.recordXPath = recordXPath;
	}

	public void setServerUrl(final String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public void setUseAsync(final boolean useAsync) {
		this.useAsync = useAsync;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public void setUseTopic(final boolean useTopic) {
		this.useTopic = useTopic;
	}

	private String exportToXml(final Adaptation record, final ProgrammaticService programmaticService)
			throws OperationException {

		String result = null;
		ExportXMLProcedure procedure = new ExportXMLProcedure();
		procedure.setRecord(record);

		ProcedureResult procedureResult = programmaticService.execute(procedure);
		if (!procedureResult.hasFailed()) {
			LoggingCategory.getWorkflow().info("===export to XML===" + procedureResult);
			return procedure.getResult();
		} else {
			LoggingCategory.getWorkflow().info(
					"===Get exception full message===" + procedureResult.getExceptionFullMessage(Locale.getDefault()));
		}

		return result;
	}

	private void sendRecordData(final Adaptation record, final ProgrammaticService programmaticService)
			throws OperationException, JMSException {
		TibjmsCompletionListener completionListener = null;
		String xmlMessage = this.exportToXml(record, programmaticService);
		LoggingCategory.getWorkflow().info("===XML Message===" + xmlMessage);
		// try
		// {
		// tibjmsUtilities.initSSLParams(serverUrl,args);
		// }
		// catch (JMSSecurityException e)
		// {
		// System.err.println("JMSSecurityException: "+e.getMessage()+",
		// provider="+e.getErrorCode());
		// e.printStackTrace();
		// System.exit(0);
		// }

		TextMessage msg;
		ConnectionFactory factory = new com.tibco.tibjms.TibjmsConnectionFactory(this.serverUrl);

		this.connection = factory.createConnection(this.userName, this.password);

		/* create the session */
		this.session = this.connection.createSession(javax.jms.Session.AUTO_ACKNOWLEDGE);

		/* create the destination */
		if (this.useTopic) {
			this.destination = this.session.createTopic(this.name);
		} else {
			this.destination = this.session.createQueue(this.name);
		}

		/* create the producer */
		this.msgProducer = this.session.createProducer(null);

		if (this.useAsync) {
			completionListener = new TibjmsCompletionListener();
		}

		/* create text message */
		msg = this.session.createTextMessage();

		/* set message text */
		msg.setText(xmlMessage);

		/* publish message */
		if (this.useAsync == false) {
			this.msgProducer.send(this.destination, msg);
		} else {
			this.msgProducer.send(this.destination, msg, completionListener);
		}

	}

}
