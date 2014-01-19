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
 * @author Nathalia Ochoa
 * 
 * @since 1.0
 * @version 1.0 Jan 02, 2014
 */
public class FreeMarkerHelper {
	private static final String REPORT_FTL = "Report.ftl";
	private static final String REPORT_VIEW_FTL = "ReportView.ftl";
	private static final String REPORT_HELPER = "ReportHelper";
	// Directorio donde se encuentran los templates con extension ftl
	private static String LOCATION_TEMPLATE = "src/main/java/templates/";
	private static String LOCATION_GENERATION = "src/main/java/generations/";
	private static String PACKAGE_GENERATION_HELPER = "generations";
	private static String PACKAGE_GENERATION_REPORT = "generations";
	public static final String DIRECTORY_GENERATION_REPORT = "/home/nochoa/devel/generaciones/generality/";

	private static Template getTemplate(String file) {
		try {
			return getConfiguration().getTemplate(LOCATION_TEMPLATE + file);
		} catch (IOException e) {
			System.out.println("No se pudo obtener el template" + e);
			return null;
		}

	}

	/**
	 * 
	 * @param nameTemplate
	 * @param nameFile
	 *            Nombre del archivo que generara freeemarker (debe ser sin la
	 *            extension)
	 * @param data
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
		Map<String, Object> paramsView = new HashMap<String, Object>();
		paramsView.put("namePackage", PACKAGE_GENERATION_REPORT);
		paramsView.put("namePackageHelper", PACKAGE_GENERATION_HELPER);
		paramsView.put("nameHelper", REPORT_HELPER);
		paramsView.put("typeFile", FileGeneration.CLASS.getType());
		paramsView.put("nameFile", nameView);
		paramsView.put("nameClassReport", nameFile);
		paramsView.put(nameView, nameView);

		generate(REPORT_VIEW_FTL, nameView, FileGeneration.CLASS, paramsView);
	}

	/**
	 * Genera la clase que extiende del Helper de los reportes, propio para cada
	 * reporte
	 * 
	 * @param nameFile
	 *            Nombre de la clase cuyo reporte se desea generar
	 */
	private static void generateClassReport(String nameFile) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("namePackage", PACKAGE_GENERATION_REPORT);
		params.put("namePackageHelper", PACKAGE_GENERATION_HELPER);
		params.put("nameHelper", REPORT_HELPER);
		params.put("typeFile", FileGeneration.CLASS.getType());
		params.put("nameFile", nameFile);

		generate(REPORT_FTL, nameFile, FileGeneration.CLASS, params);
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
		params.put("nameFile", REPORT_HELPER);
		// Directorio donde el helper generara los reportes
		params.put("directoryGenerationReport", DIRECTORY_GENERATION_REPORT);
		generate("ReportHelper.ftl", REPORT_HELPER, FileGeneration.CLASS,
				params);

	}

	private static void generate(String nameTemplate, String nameFile,
			FileGeneration fileGeneration, Map<String, Object> data) {

		try {
			Template template = getTemplate(nameTemplate);
			// Genera el archivo
			Writer output;
			output = new FileWriter(new File(LOCATION_GENERATION + nameFile
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
