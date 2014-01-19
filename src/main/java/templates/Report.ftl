package ${namePackage};

import ${namePackageHelper}.${nameHelper};

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public ${typeFile} ${nameFile} extends ${nameHelper} {

	/*
	 * (non-Javadoc)
	 * 
	 * @see ${namePackageHelper}.${nameHelper}#getDataSource()
	 */
	@Override
	public List<?> getData() {
		List<Element> aRet = new ArrayList<Element>();
		aRet.add(new Element("1", "Diego", "Franco", "aa"));
		aRet.add(new Element("2", "Te", "Amo", " aa"));
		aRet.add(new Element("3", "Mi", "Cielito", " gaaaay"));
		return aRet;
	}

	public class Element {
		private String id;
		private String nombre;
		private String apellido;
		private String chuckNorris;

		/**
		 * @param nombre
		 * @param apellido
		 */
		public Element(String id, String nombre, String apellido, String chuck) {
			super();
			this.id = id;
			this.nombre = nombre;
			this.apellido = apellido;
			chuckNorris = chuck;
		}

		/**
		 * @return the chuckNorris
		 */
		public String getChuckNorris() {
			return chuckNorris;
		}

		/**
		 * @param chuckNorris
		 *            the chuckNorris to set
		 */
		public void setChuckNorris(String chuckNorris) {
			this.chuckNorris = chuckNorris;
		}

		/**
		 * @return the nombre
		 */
		public String getNombre() {
			return nombre;
		}

		/**
		 * @param nombre
		 *            the nombre to set
		 */
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		/**
		 * @return the apellido
		 */
		public String getApellido() {
			return apellido;
		}

		/**
		 * @param apellido
		 *            the apellido to set
		 */
		public void setApellido(String apellido) {
			this.apellido = apellido;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ${namePackageHelper}.${nameHelper}#getNameReport()
	 */
	@Override
	public String getNameReport() {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("dd-MM-yyyy");
		return "Reporte - " + sdf.format(new Date());
	}

}
