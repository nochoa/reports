package ${namePackage};

import ${namePackageHelper}.${nameReportHelper};
import ${namePackageHelper}.${namePersistenceHelper};

import java.util.List;

public ${typeFile} ${nameClass} extends ${nameReportHelper} {

	/*
	 * (non-Javadoc)
	 * 
	 * @see ${namePackageHelper}.${nameReportHelper}#getDataSource()
	 */
	@Override
	public List<?> getData() {
		return ${namePersistenceHelper}.createQuery("select p from ${nameEntity} p");
		
	}

}
