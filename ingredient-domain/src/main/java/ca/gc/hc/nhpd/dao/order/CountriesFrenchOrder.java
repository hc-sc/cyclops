package ca.gc.hc.nhpd.dao.order;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;

public class CountriesFrenchOrder extends Order {

	private static final long serialVersionUID = 6281532833953585164L;

	public CountriesFrenchOrder() {
		super("", true);
	}
	
    @Override
    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
    	return "case when country_code = 'CA' then '1' when country_code = 'US' then '2' else '3' || country_name_fr end";
    }
	
}


