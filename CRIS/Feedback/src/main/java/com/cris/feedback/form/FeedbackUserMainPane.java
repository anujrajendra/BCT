package com.cris.feedback.form;

import com.cris.feedback.path.FeedbackPath;
import com.cris.feedback.utils.UIUtils;
import com.orchestranetworks.ui.form.UIFormContext;
import com.orchestranetworks.ui.form.UIFormPane;
import com.orchestranetworks.ui.form.UIFormPaneWriter;

public class FeedbackUserMainPane implements UIFormPane {

	@Override
	public void writePane(UIFormPaneWriter pWriter, UIFormContext context) {

		String LeftDivStyle = "display: inline-block; width: 49%; padding-bottom: 10px;";
		String RightDivStyle = "float:right; display: inline-block; width: 49%;";
		String DownDivStyle = "display: inline-block; width: 90%;";

		pWriter.startBorder(true);
		pWriter.add("<div style='" + LeftDivStyle + " min-width:100px;'>");

		UIUtils.addTitleFormRow(pWriter, "Feedback/Issue/Suggestion");

		pWriter.add("<br>");
		pWriter.startTableFormRow();
		pWriter.addFormRow(FeedbackPath._Root_Feedback._Root_Feedback_Id);
		pWriter.addFormRow(FeedbackPath._Root_Feedback._Root_Feedback_UserId);
		pWriter.addFormRow(FeedbackPath._Root_Feedback._Root_Feedback_UserName);
		pWriter.addFormRow(FeedbackPath._Root_Feedback._Root_Feedback_UserDesignation);

		pWriter.addFormRow(FeedbackPath._Root_Feedback._Root_Feedback_RequestType);
		pWriter.endTableFormRow();
		pWriter.add("</div>");

		pWriter.add("<div style='" + RightDivStyle + " min-width:100px;'>");
		pWriter.add("<br>");
		pWriter.add("<br>");
		pWriter.add("<br>");
		pWriter.add("<br>");

		pWriter.startTableFormRow();
		pWriter.addFormRow(FeedbackPath._Root_Feedback._Root_Feedback_UserZone);
		pWriter.addFormRow(FeedbackPath._Root_Feedback._Root_Feedback_UserDivision);
		pWriter.addFormRow(FeedbackPath._Root_Feedback._Root_Feedback_MobileNumber);

		pWriter.addFormRow(FeedbackPath._Root_Feedback._Root_Feedback_RequestPriority);
		pWriter.endTableFormRow();
		pWriter.add("</div>");

		pWriter.add("<div style='" + DownDivStyle + " min-width:100px;'>");

		pWriter.add("<br>");
		pWriter.add("<br>");
		pWriter.startTableFormRow();
		pWriter.addFormRow(FeedbackPath._Root_Feedback._Root_Feedback_ChooseFunctionality);

		pWriter.addFormRow(FeedbackPath._Root_Feedback._Root_Feedback_RequestTitle);

		pWriter.addFormRow(FeedbackPath._Root_Feedback._Root_Feedback_RequestDescription);
		pWriter.endTableFormRow();
		pWriter.add("</div>");
		pWriter.endBorder();

		UIUtils.standardizeFieldLabelWidth(pWriter, true);

	}
}
