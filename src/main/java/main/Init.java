/**
 * 
 */
package main;

import helper.FreeMarkerHelper;

/**
 * @author Nathalia Ochoa
 * 
 * @since 1.0
 * @version 1.0 Dec 27, 2013
 */
public class Init {
	public static void main(String[] args) {

		FreeMarkerHelper.generateReportHelper();
		FreeMarkerHelper.generatePersistenceHelper();
		FreeMarkerHelper.generateReport("Persona");
		FreeMarkerHelper.generateReport("Pais");

	}
}
