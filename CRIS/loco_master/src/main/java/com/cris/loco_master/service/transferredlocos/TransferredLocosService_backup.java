package com.cris.loco_master.service.transferredlocos;

import java.util.ArrayList;
import java.util.List;

import com.cris.loco_master.Paths;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.RequestResult;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.service.Session;
import com.orchestranetworks.ui.UICSSClasses;
import com.orchestranetworks.ui.selection.TableViewEntitySelection;
import com.orchestranetworks.userservice.UserService;
import com.orchestranetworks.userservice.UserServiceDisplayConfigurator;
import com.orchestranetworks.userservice.UserServiceEventOutcome;
import com.orchestranetworks.userservice.UserServiceObjectContextBuilder;
import com.orchestranetworks.userservice.UserServicePane;
import com.orchestranetworks.userservice.UserServicePaneContext;
import com.orchestranetworks.userservice.UserServicePaneWriter;
import com.orchestranetworks.userservice.UserServiceProcessEventOutcomeContext;
import com.orchestranetworks.userservice.UserServiceSetupDisplayContext;
import com.orchestranetworks.userservice.UserServiceSetupObjectContext;
import com.orchestranetworks.userservice.UserServiceValidateContext;

public class TransferredLocosService_backup implements UserService<TableViewEntitySelection> {

	@Override
	public UserServiceEventOutcome processEventOutcome(
			UserServiceProcessEventOutcomeContext<TableViewEntitySelection> arg0, UserServiceEventOutcome arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setupDisplay(UserServiceSetupDisplayContext<TableViewEntitySelection> context,
			UserServiceDisplayConfigurator config) {
		// TODO Auto-generated method stub
		config.setContent(new UserServicePane() {

			@Override
			public void writePane(UserServicePaneContext arg0, UserServicePaneWriter pWriter) {
				// TODO Auto-generated method stub
				Session session = context.getSession();

				System.out.println("===INside transferred Loco service 1===");
				Repository repository = Repository.getDefault();
				AdaptationHome locoDataspace = repository
						.lookupHome(HomeKey.parse(HomeKey.forBranchName("loco_data").format()));
				Adaptation locoDataset = locoDataspace.findAdaptationOrNull(AdaptationName.forName("loco_data"));
				AdaptationTable locoAdaptationTable = locoDataset.getTable((Path.parse("/root/Locomotive")));
				AdaptationTable locoHistoryTable = locoAdaptationTable.getHistory();

				pWriter.add("<div").addSafeAttribute("class", UICSSClasses.CONTAINER_WITH_TEXT).add(">");

				pWriter.add("</div>");

				String floatDivStyle = "float:left; width:100%; ";

				pWriter.add("<div style='" + floatDivStyle + " min-width:200px;'>");
				// pWriter.startBorder(true);
				pWriter.add(
						"<table style= 'border-collapse: collapse;'><tr><td style='margin-top: 3px; width: 30px; min-width: 30px; max-width: 30px; vertical-align:top'>");

				pWriter.add(
						"<tr><td style='white-space: nowrap; color: gray; border: 1px solid; background: yellow;'>Loco Number</div></td>");

				pWriter.add(
						"<td style='white-space: nowrap; color: gray; border: 1px solid;background: yellow;'>From Zone</div></td>");

				pWriter.add(
						"<td style='white-space: nowrap; color: gray; border: 1px solid;background: yellow;'>From Division</div></td>");

				pWriter.add(
						"<td style='white-space: nowrap; color: gray; border: 1px solid;background: yellow;'>From Shed</div></td>");
				// pWriter.add("<tr><td style='height: 10px;'></td></tr>");

				pWriter.add(
						"<td style='white-space: nowrap; color: gray; border: 1px solid;background: yellow;'>To Zone</div></td>");

				pWriter.add(
						"<td style='white-space: nowrap; color: gray; border: 1px solid;background: yellow;'>To Division</div></td>");

				pWriter.add(
						"<td style='white-space: nowrap; color: gray; border: 1px solid;background: yellow;'>To Shed</div></td>");

				pWriter.add(
						"<td style='white-space: nowrap; color: gray; border: 1px solid;background: yellow;'>Transfer Date</div></td>");

				// pWriter.add("<tr><td style='height: 10px;'></td></tr>");
//				pWriter.add(
//						"<td style='white-space: nowrap; color: gray; border: 1px solid;background: yellow;'>Source Event Type</div></td>");

				pWriter.add("</tr>");
				// locoAdaptation.isOccurrenceHidden();

				final String predicate = Paths._Root_Locomotive._Root_Locomotive_Audit_Info_Source_Event_Type.format()
						+ "='SLTR'"; // +SLTR +"'";
				RequestResult historyRequestResult = locoHistoryTable.createRequestResult(predicate);

				RequestResult newRequestResult = locoAdaptationTable.createRequestResult(predicate);
				Adaptation historyLocoAdaptation = null;

				List<String> locoNumberOldNewShedsList = new ArrayList<String>();

//				while ((historyLocoAdaptation = historyRequestResult.nextAdaptation()) != null) {
//
//					String locoNumber = (String) historyLocoAdaptation
//							.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Number);
//
//					System.out.println("++++Loco Number====" + locoNumber);
//					System.out
//							.println("====Txn_id===" + historyLocoAdaptation.get(Path.parse("./ebx-technical/tx_id")));
//					if (!session.getPermissions().getAdaptationAccessPermission(historyLocoAdaptation).isHidden()) {
//
////						if (locoNumberList.contains(locoNumber)) {
////							continue;
////						} else {
////							System.out.println("====Loco number added to the list====");
////							locoNumberList.add(locoNumber);
////						}
//						Adaptation newLocoAdaptation = null;
//						newLocoAdaptation = locoAdaptationTable
//								.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(locoNumber));
//
//						String oldShed = (String) historyLocoAdaptation
//								.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Shed);
//
//						String newShed = (String) newLocoAdaptation
//								.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Shed);
//
//						if (newShed.equalsIgnoreCase(oldShed)) {
//							System.out.println("====Old and new sheds same====");
//							continue;
//						}
//
//						String locoNumberOldNewShed = locoNumber + "" + oldShed + "" + newShed;
//						if (locoNumberOldNewShedsList.contains(locoNumberOldNewShed)) {
//							continue;
//						} else {
//							locoNumberOldNewShedsList.add(locoNumberOldNewShed);
//						}
//						String oldZone = (String) historyLocoAdaptation
//								.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Zone);
//						String oldDivision = (String) historyLocoAdaptation
//								.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Division);
//
//						String newZone = (String) newLocoAdaptation
//								.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Zone);
//						;
//						String newDivision = (String) newLocoAdaptation
//								.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Division);
//						;
//
//						System.out.println("===Inside transferred Loco service 2===");
//						System.out.println("===Loco Number ===="
//								+ historyLocoAdaptation.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Number));
//
//						pWriter.add("<tr>");
//						pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + locoNumber
//								+ "</div></td>");
//						pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + oldZone
//								+ "</div></td>");
//						pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + oldDivision
//								+ "</div></td>");
//						pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + oldShed
//								+ "</div></td>");
//
//						pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + newZone
//								+ "</div></td>");
//						pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + newDivision
//								+ "</div></td>");
//						pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + newShed
//								+ "</div></td>");
//
////						pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>"
////								+ locoAdaptation
////										.get(Paths._Root_Locomotive._Root_Locomotive_Audit_Info_Source_Event_Type)
////								+ "</div></td>");
//
//					}
//				}
//
//				Adaptation newLocoAdaptation = null;
//				while ((newLocoAdaptation = newRequestResult.nextAdaptation()) != null) {
//					String locoNumber = (String) newLocoAdaptation
//							.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Number);
//
//					final String locoNumberHistoryPredicate = Paths._Root_Locomotive._Root_Locomotive_Loco_Number
//							.format() + "='" + locoNumber + "'";
//					RequestResult locoNumberHistoryRequestResult = locoHistoryTable
//							.createRequestResult(locoNumberHistoryPredicate);
//					while ((historyLocoAdaptation = locoNumberHistoryRequestResult.nextAdaptation()) != null) {
//						if (!session.getPermissions().getAdaptationAccessPermission(historyLocoAdaptation).isHidden()) {
//
//							String oldShed = (String) historyLocoAdaptation
//									.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Shed);
//
//							String newShed = (String) newLocoAdaptation
//									.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Shed);
//
//							if (newShed.equalsIgnoreCase(oldShed)) {
//								System.out.println("====Old and new sheds same====");
//								continue;
//							}
//
//							String locoNumberOldNewShed = locoNumber + "" + oldShed + "" + newShed;
//							if (locoNumberOldNewShedsList.contains(locoNumberOldNewShed)) {
//								continue;
//							} else {
//								locoNumberOldNewShedsList.add(locoNumberOldNewShed);
//							}
//							String oldZone = (String) historyLocoAdaptation
//									.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Zone);
//							String oldDivision = (String) historyLocoAdaptation
//									.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Division);
//
//							String newZone = (String) newLocoAdaptation
//									.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Zone);
//							;
//							String newDivision = (String) newLocoAdaptation
//									.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Division);
//							;
//
//							System.out.println("===Inside transferred Loco service 2===");
//							System.out.println("===Loco Number ===="
//									+ historyLocoAdaptation.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Number));
//
//							pWriter.add("<tr>");
//							pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + locoNumber
//									+ "</div></td>");
//							pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + oldZone
//									+ "</div></td>");
//							pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>"
//									+ oldDivision + "</div></td>");
//							pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + oldShed
//									+ "</div></td>");
//
//							pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + newZone
//									+ "</div></td>");
//							pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>"
//									+ newDivision + "</div></td>");
//							pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + newShed
//									+ "</div></td>");
//
//						}
//					}
//				}

				Adaptation historyLocoAdaptation1 = null;
				while ((historyLocoAdaptation1 = historyRequestResult.nextAdaptation()) != null) {

					String locoNumber = (String) historyLocoAdaptation1
							.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Number);
					Integer transId1 = (Integer) historyLocoAdaptation1.get(Path.parse("./ebx-technical/tx_id"));

//					System.out.println("++++Loco Number====" + locoNumber);
//					System.out
//							.println("====Txn_id===" + historyLocoAdaptation.get(Path.parse("./ebx-technical/tx_id")));
//					if (!session.getPermissions().getAdaptationAccessPermission(historyLocoAdaptation1).isHidden()) {

					List<Integer> trandsactionIdList = new ArrayList<>();

					final String locoNumberHistoryPredicate = Paths._Root_Locomotive._Root_Locomotive_Loco_Number
							.format() + "='" + locoNumber + "'";
					// locoHistoryTable.createRequest().setSortCriteria(RequestSortCriteria.)

					RequestResult locoNumberHistoryRequestResult = locoHistoryTable
							.createRequestResult(locoNumberHistoryPredicate);

					Adaptation historyLocoAdaptation2 = null;

					while ((historyLocoAdaptation2 = locoNumberHistoryRequestResult.nextAdaptation()) != null) {

						Integer transId2 = (Integer) historyLocoAdaptation2.get(Path.parse("./ebx-technical/tx_id"));

						if (!session.getPermissions().getAdaptationAccessPermission(historyLocoAdaptation2).isHidden()
								&& (transId2 < transId1)) {
							trandsactionIdList.add(transId2);

							String fromShed = (String) historyLocoAdaptation2
									.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Shed);

							String toShed = (String) historyLocoAdaptation1
									.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Shed);

							if (toShed.equalsIgnoreCase(fromShed)) {
								// System.out.println("====Old and new sheds same====");
								continue;
							}

							String locoNumberOldNewShed = locoNumber + "" + fromShed + "" + toShed;
							if (locoNumberOldNewShedsList.contains(locoNumberOldNewShed)) {
								continue;
							} else {
								locoNumberOldNewShedsList.add(locoNumberOldNewShed);
							}

							String fromZone = (String) historyLocoAdaptation2
									.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Zone);
							String fromDivision = (String) historyLocoAdaptation2
									.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Division);

							String toZone = (String) historyLocoAdaptation1
									.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Zone);
							;
							String toDivision = (String) historyLocoAdaptation1
									.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Division);
							;

//							System.out.println("===Inside transferred Loco service 2===");
//							System.out.println("===Loco Number ===="
//									+ historyLocoAdaptation.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Number));

							pWriter.add("<tr>");
							pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + locoNumber
									+ "</div></td>");
							pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + fromZone
									+ "</div></td>");
							pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>"
									+ fromDivision + "</div></td>");
							pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + fromShed
									+ "</div></td>");

							pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + toZone
									+ "</div></td>");
							pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + toDivision
									+ "</div></td>");
							pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + toShed
									+ "</div></td>");

							pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>"
									+ historyLocoAdaptation1
											.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Transfer_Date)
									+ "</div></td>");
							break;
						}
						// }

					}
				}

				pWriter.startTableFormRow();
				pWriter.endTableFormRow();

				pWriter.add("</td></tr></table>");
				// pWriter.endBorder();
				pWriter.add("</div>");
			}
		});
	}

	@Override
	public void setupObjectContext(UserServiceSetupObjectContext<TableViewEntitySelection> arg0,
			UserServiceObjectContextBuilder arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void validate(UserServiceValidateContext<TableViewEntitySelection> arg0) {
		// TODO Auto-generated method stub

	}

}
