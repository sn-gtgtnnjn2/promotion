package dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DaoFactory {
	public static PreUserDaoImpl createPreUserDao() {
		PreUserDaoImpl pud = new PreUserDaoImpl(getDataSource());
		return pud;
	}
	
	public static UserDaoImpl createUserDao() {
		UserDaoImpl ud = new UserDaoImpl(getDataSource());
		return ud;
	}

	private static DataSource getDataSource() {
		InitialContext ctx = null;
		DataSource ds = null;

		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/site_db");
		} catch (NamingException e) {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (NamingException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		return ds;
	}

}
