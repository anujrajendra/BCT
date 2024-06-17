package com.bct.addon.dxch.datamodelgenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.MessageFormat;

import com.orchestranetworks.addon.adix.dataexchange.datamodelgenerator.i18n.DataModelerErrorMessages;
import com.orchestranetworks.addon.dml.DataModelException;

/**
 *
 * @author mostafashokiel
 */

public final class DMLTemplateManager {
	public static final String XSD_TEMPLATE_FILE_PATH = "/com/bct/addon/dxch/templates/template_Datamodel.txt";

	public static final String TABLE_TEMPLATE_FILE_PATH = "/com/bct/addon/dxch/templates/template_Table.txt";

	public static final String FIELD_TEMPLATE_FILE_PATH = "/com/bct/addon/dxch/templates/template_Field.txt";

	public static final String GROUP_TEMPLATE_FILE_PATH = "/com/bct/addon/dxch/templates/template_Group.txt";

	public static final String DOCUMENT_TEMPLATE_FILE_PATH = "/com/bct/addon/dxch/templates/template_Documentation.txt";

	public static String loadTemplate(final String filePath) throws DataModelException {
		StringBuilder builder = new StringBuilder();
		try {
			InputStreamReader in = new InputStreamReader(DMLTemplateManager.class.getResourceAsStream(filePath),
					Charset.forName("UTF-8"));
			BufferedReader bufferedReader = new BufferedReader(in);
			try {
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					builder.append(line);
					builder.append("\n");
				}
				return builder.toString();
			} finally {
				bufferedReader.close();
				in.close();
			}
		} catch (IOException ex) {
			throw new DataModelException(DataModelerErrorMessages.get_Exceptions_FileNotFound(ex));
		}
	}

	public static String generate(final String template, final Object[] params) {
		MessageFormat format = new MessageFormat(template);
		return format.format(params);
	}
}