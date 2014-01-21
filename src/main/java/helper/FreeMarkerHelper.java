package helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import util.FileGeneration;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * Clase utilizada para generar archivos utilizando freemarker.
 * 
 * @author Nathalia Ochoa
 * 
 * @since 1.0
 * @version 1.0 Jan 02, 2014
 */
public class FreeMarkerHelper {

	private static final String PERSISTENCE_UNIT = "reports";
	private static final String PERSISTENCE_HELPER = "PersistenceHelper";
	private static final String REPORT_HELPER = "ReportHelper";

	// Directorio donde se encuentran los templates con extension ftl.
	private static String LOCATION_TEMPLATE = "src/main/java/templates/";

	// Templates
	private static final String REPORT_FTL = "Report.ftl";
	private static final String REPORT_HELPER_FTL = "ReportHelper.ftl";
	private static final String PERSISTENCE_FTL = "PersistenceHelper.ftl";
	private static final String REPORT_VIEW_FTL = "ReportView.ftl";

	// Directorio o ubicacion donde se desean generar los archivos.
	private static String LOCATION_GENERATION_REPORT = "src/main/java/generations/report/";
	private static String LOCATION_GENERATION_HELPER = "src/main/java/generations/helper/";

	// Paquetes donde se desean generar los archivos.
	private static String PACKAGE_GENERATION_HELPER = "generations.helper";
	private static String PACKAGE_GENERATION_REPORT = "generations.report";

	// Directorio donde se generaran los reportes.
	public static final String DIRECTORY_GENERATION_REPORT = "/home/nochoa/devel/generaciones/generality/";

	/**
	 * Obtiene el template con extension ftl que se desea utilizar para generar
	 * el archivo.
	 * 
	 * @param nameTemplate
	 *            Nombre del template
	 * 
	 * @return
	 */
	private static Template getTemplate(String nameTemplate) {
		try {
			return getConfiguration().getTemplate(
					LOCATION_TEMPLATE + nameTemplate);
		} catch (IOException e) {
			System.out.println("No se pudo obtener el template" + e);
			return null;
		}

	}

	/**
	 * Genera las clases necesarias para un reporte, esto es la logica y la
	 * vista.
	 * 
	 * @param nameFile
	 *            Nombre del archivo que generara freeemarker (debe ser sin la
	 *            extension).
	 * 
	 */
	public static void generateReport(String nameFile) {
		generateClassReport(nameFile);
		generateViewReport(nameFile);

	}

	/**
	 * Genera una vista para probar el reporte, especificamente una ventana con
	 * dos botones de generacion de reportes, uno para PDF y otro para XLS.
	 * Ademas de un boton para cancelar la operacion.
	 * 
	 * @param nameFile
	 *            Nombre de la clase cuyo reporte se desea generar
	 */
	private static void generateViewReport(String nameFile) {
		String nameView = nameFile + "View";
		String nameReport = nameFile + "Report";
		Map<String, Object> paramsView = new HashMap<String, Object>();
		paramsView.put("namePackage", PACKAGE_GENERATION_REPORT);
		paramsView.put("namePackageHelper", PACKAGE_GENERATION_HELPER);
		paramsView.put("nameReportHelper", REPORT_HELPER);
		paramsView.put("typeFile", FileGeneration.CLASS.getType());
		paramsView.put("nameClass", nameView);
		paramsView.put("nameClassReport", nameReport);
		paramsView.put(nameView, nameView);

		generate(REPORT_VIEW_FTL, nameView, FileGeneration.CLASS, paramsView,
				LOCATION_GENERATION_REPORT);
	}

	/**
	 * Genera la clase que extiende del Helper de los reportes, propio para cada
	 * reporte
	 * 
	 * @param nameFile
	 *            Nombre de la clase cuyo reporte se desea generar
	 */
	private static void generateClassReport(String nameFile) {
		String nameReport = nameFile + "Report";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("namePackage", PACKAGE_GENERATION_REPORT);
		params.put("namePackageHelper", PACKAGE_GENERATION_HELPER);
		params.put("nameReportHelper", REPORT_HELPER);
		params.put("namePersistenceHelper", PERSISTENCE_HELPER);
		params.put("typeFile", FileGeneration.CLASS.getType());
		params.put("nameClass", nameReport);
		params.put("nameEntity", nameFile);

		generate(REPORT_FTL, nameReport, FileGeneration.CLASS, params,
				LOCATION_GENERATION_REPORT);
	}

	/**
	 * Genera la clase Helper que realiza la generacion de los reportes.
	 */
	public static void generateReportHelper() {
		Map<String, Object> params = new HashMap<String, Object>();
		// Nombre del paquete donde se debe generar el helper
		params.put("namePackage", PACKAGE_GENERATION_HELPER);
		params.put("typeFile", FileGeneration.CLASS.getType());
		// Nombre con el cual se debe generar la clase
		params.put("nameClass", REPORT_HELPER);
		// Directorio donde el helper generara los reportes
		params.put("directoryGenerationReport", DIRECTORY_GENERATION_REPORT);
		generate(REPORT_HELPER_FTL, REPORT_HELPER, FileGeneration.CLASS,
				params, LOCATION_GENERATION_HELPER);

	}

	public static void generatePersistenceHelper() {
		Map<String, Object> params = new HashMap<String, Object>();
		// Nombre del paquete donde se debe generar el helper
		params.put("namePackage", PACKAGE_GENERATION_HELPER);
		params.put("typeFile", FileGeneration.CLASS.getType());
		// Nombre con el cual se debe generar la clase
		params.put("nameClass", PERSISTENCE_HELPER);

		params.put("persistenceUnit", PERSISTENCE_UNIT);
		// Directorio donde el helper generara los reportes
		params.put("directoryGenerationReport", DIRECTORY_GENERATION_REPORT);
		generate(PERSISTENCE_FTL, PERSISTENCE_HELPER, FileGeneration.CLASS,
				params, LOCATION_GENERATION_HELPER);

	}

	/**
	 * Genera un arhivo utilizando freemarker.
	 * 
	 * @param nameTemplate
	 *            Nombre del template a utilizar.
	 * @param nameFile
	 *            Nombre del archivo a generar
	 * @param fileGeneration
	 *            Tipo de archivo que se desea generar con su correspondiente
	 *            extension.
	 * @param data
	 *            Lista de parametros mapeados con los parametros del template.
	 * @param locationGeneration
	 *            Paquete donde se desea generar el archivo.
	 */
	private static void generate(String nameTemplate, String nameFile,
			FileGeneration fileGeneration, Map<String, Object> data,
			String locationGeneration) {

		try {
			Template template = getTemplate(nameTemplate);
			// Genera el archivo
			Writer output;
			output = new FileWriter(new File(locationGeneration + nameFile
					+ fileGeneration.getExtension()));

			template.process(data, output);
			output.flush();
			output.close();
			System.out.println("Archivo " + nameFile
					+ fileGeneration.getExtension() + " generado exitosamente");

		} catch (IOException e) {
			System.out.println("No se pudo generar el archivo" + e);

		} catch (TemplateException e) {
			System.out.println("No se pudo procesar el template" + e);
		}

	}

	private static Configuration getConfiguration() {
		return new Configuration();
	}

}
