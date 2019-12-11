package ca.gc.hc.nhpd.util;

/*******************************************************************************
 * Utility methods to support dealing with SQL.
 * 
 * Note that this hasn't been tested with queries containing the following
 * Oracle reserved characters: !,\,|,?,$
 */
public class SqlUtil {

	//private static String[] REMOVE_CHAR = {"{", "}", ";", "?", "'", "\""}; 
	private static String GENERIC_WILD = "*";
	private static String SQL_WILD = "%";
	private static String SQL_POS = "_";
	private static String S_QUOTE = "'";
	private static String DOUBLE_S_QUOTE = "''";
	private static String D_QUOTE = "\"";
    //private static String EMPTY_STRING = "";

    //Used where multiple values are stored in the same database column
    public static final String VALUE_DELIMITER = "~"; 

    private static String S_QUOTES = "''";
    private static String SQL_POS_ESCAPED = "\\_";
    private static char SQL_WILD_CHAR = '%';
    private static String SQL_WILD_ESCAPED = "\\%";

    
    /***************************************************************************
     * Builds a String containing a list of single quote enclosed String values.
     * Used to build an SQL "ANY" clause; e.g.: 
     *  Select xyz from abc where colx = ANY ( 'aaa', 'bbb', 'ccc' );
     * @param parms   the strings to be placed within the ANY clause
     * @return the created string.
     */
    public static String buildAnyParms(String[] parms) {
        StringBuffer anyClause = new StringBuffer();
        
        anyClause.append("ANY (");
        for (int x=0;x<parms.length;x++) {
            anyClause.append("'" + parms[x] + "',");
        }
        //replace last comma with close bracket
        anyClause.setCharAt(anyClause.length()-1, ')');
   
        return anyClause.toString();
    }    
   
    /***************************************************************************
     * Creates a query expression to be included in a SELECT clause when it
     * requires a "~" delimiter in the  value of its SQL LIKE clause. Used to
     * search for occurrences of a string starting or ending with a "~" within a 
     * set of strings which are separated by that character.
     * <br/>
     * This method is currently used for queries against the 
     * Ingredient_Synonyms_MV and Monograph_Synonyms_MV Materialized Views
     * used for web services.
     * <br/>
     * See createDelimitedQueryExpression(value, delimiter) for a detailed
     * description.
     * @param value the value to be searched on.
     * @return the query expression.
     */
    public static String createDelimitedQueryExpression(String value) {
        return createDelimitedQueryExpression(value, VALUE_DELIMITER);
    }

    /***************************************************************************
	 * Creates a query expression to be included in a SELECT clause when it
	 * requires special handling to insert an arbitrary delimiter character in
	 * the  value of an SQL LIKE clause which describes a "starting with" or 
     * "ending with" condition. Used to search for occurrences of a string 
     * starting or ending with a particular sequence of characters within a 
     * set of strings which are separated by the specified delimiter character.
	 * The parsing is similar to that of createQueryExpression(), however
	 * literal value searches use a LIKE.
     * <br/><br/>
     * Examples (if the delimiter is a "~"):
	 * <ul>
     * <li>null returns [ LIKE '%' ]
     * <li>[] returns [ LIKE '%' ]
     * <li>['try'] returns [ LIKE '%~try~%' ]
     * <li>['try '] returns [ LIKE '%~try ~%' ]
     * <li>["try"] returns [ LIKE '%~try~%' ]
     * <li>['t'r'y'] returns [ LIKE '%~t''r''y~%' ]
     * <li>["tr"y"] returns [ LIKE '%~tr\"y~%' ]
     * <li>['tr%y%'] returns [ LIKE '%~tr\%y\%~%' ]
     * <li>['tr_y_'] returns [ LIKE '%~tr\_y\_~%' ]
     * <li>['try*'] returns [ LIKE '%~try*~%' ]
     * <li>[try] returns [ LIKE '%try%' ]
     * <li>[try ] returns [ LIKE '%try%' ]
     * <li>[try%] returns [ LIKE '%~try%' ]
     * <li>[ try %] returns [ LIKE '%~try %' ]
     * <li>[t_ry] returns [ LIKE '%~t_ry~%' ]
     * <li>[*try] returns [ LIKE '%try~%' ]
     * <li>[tr'y] returns [ LIKE '%tr''y%' ]
     * <li>[tr?y] returns [ LIKE '%tr?y%' ]
     * <li>[tr{}y] returns [ LIKE '%tr{}y%' ]
     * </ul>
	 * @param value the value to be searched on.
     * @param delimiter the delimiter character.
	 * @return the query expression.
	 */
	public static String createDelimitedQueryExpression(String value,
	                                                    String delimiter) {
		if (value == null || value.length() == 0) {
			return " LIKE '%' ";
		}
		
		value = value.trim();
        
        // Determine if the string has starting and closing quotes.
        boolean hasQuotes = 
          ( value.startsWith(D_QUOTE) && value.endsWith( D_QUOTE ) ) || 
          ( value.startsWith(S_QUOTE) && value.endsWith( S_QUOTE ) );
        
        if ( hasQuotes ) {
            //strip wrapping quotes
            value = value.substring(1, value.length() - 1); 
            //escape SQL wildcards
            value = value.replace(SQL_POS, SQL_POS_ESCAPED);
            value = value.replace(SQL_WILD, SQL_WILD_ESCAPED);

        } else {
            //replace the generic wildcard * with the SQL wildcard %  
            value = value.replace(GENERIC_WILD, SQL_WILD);
        }

        //escape single quotes
        value = value.replace(S_QUOTE, S_QUOTES);

		if (hasQuotes || value.indexOf(SQL_WILD) != -1 
		    || value.indexOf(SQL_POS) != -1) {
            return addStartEndDelimiter(" LIKE '" + value + "' ", delimiter, hasQuotes);

		}else {
            return addStartEndDelimiter(" LIKE '%" + value + "%' ", delimiter, hasQuotes);
		}
	}
    
    /***************************************************************************
     * Creates a query expression to be included in a SELECT clause. Removes
     * leading and trailing spaces. This determines whether the user is
     * searching for a literal value by testing if it is enclosed in quotes.
     * Otherwise a LIKE is used. If the user has included any wildcard
     * characters, it assumes that they know what they are doing and uses their
     * entry (after substituting SQL wild cards for generic ones and escaping
     * single quotes). Otherwise a wild card is added to both ends.
     * <br/><br/>
     * Examples:
     * <ul>
     * <li>null returns [ LIKE '%' ]
     * <li>[] returns [ LIKE '%' ]
     * <li>['try'] returns [ = 'try' ]
     * <li>['try '] returns [ = 'try ' ]
     * <li>["try"] returns [ = 'try' ]
     * <li>['t'r'y'] returns [ = 't''r''y' ]
     * <li>["tr"y"] returns [ = 'tr"y' ]
     * <li>['tr%y%'] returns [ = 'tr%y%' ]
     * <li>['tr_y_'] returns [ = 'tr_y_' ]
     * <li>['try*'] returns [ = 'try*' ]
     * <li>[try] returns [ LIKE '%try%' ]
     * <li>[try ] returns [ LIKE '%try%' ]
     * <li>[try%] returns [ LIKE 'try%' ]
     * <li>[ try %] returns [ LIKE 'try %' ]
     * <li>[t_ry] returns [ LIKE 't_ry' ]
     * <li>[*try] returns [ LIKE '%try' ]
     * <li>[tr'y] returns [ LIKE '%tr''y%' ]
     * <li>[tr?y] returns [ LIKE '%tr?y%' ]
     * <li>[tr{}y] returns [ LIKE '%tr{}y%' ]
     * </ul>
     * @param value the value to be searched on.
     * @return the query expression.
     */
    public static String createQueryExpression(String value) {
        
        if (value == null || value.length() == 0) {
            return " LIKE '%' ";
        }
        
        value = value.trim();
        
        // Determine if the string has starting and closing quotes.
        boolean hasQuotes = 
          ( value.startsWith(D_QUOTE) && value.endsWith( D_QUOTE ) ) || 
          ( value.startsWith(S_QUOTE) && value.endsWith( S_QUOTE ) );

        if ( hasQuotes ) {
            value = value.substring(1, value.length() - 1); //strip wrapping quotes
            //escape single quotes
            value = value.replace(S_QUOTE, S_QUOTES);
            return createLiteral(value);
        }

        //escape single quotes
        value = value.replace(S_QUOTE, S_QUOTES);
        //replace the generic wildcard * with the SQL wildcard %  
        value = value.replace(GENERIC_WILD, SQL_WILD);
        if (value.indexOf(SQL_WILD) == -1 && value.indexOf(SQL_POS) == -1) {
            return " LIKE '%" + value + "%' ";
        } 
        else {
            return " LIKE '" + value + "' ";
        }
        
    }

	/***************************************************************************
	 * Special handling to add delimiter character(s) and wild cards to the ends
	 * of an SQL LIKE clause. These determine a "starting with" or "ending with"
	 * condition. Used to search for occurrences of a string starting or ending
	 * with a particular sequence of characters within a set of strings which
	 * are separated by the specified delimiter character(s).
	 * <br/><br/>
	 * For example, if the delimiter is a ~ and force is false:
     * <ul>
     * <li>[ LIKE 'xyz%' ] returns [ LIKE '%~xyz%' ] 
	 * <li>[ LIKE '%xyz' ] returns [ LIKE '%xyz~%' ] 
     * <li>[ LIKE 'xyz' ] returns [ LIKE '%~xyz~%' ]
     * </ul>
     * If the delimiter is a ~ and force is true:
     * <ul>
     * <li>[ LIKE 'xyz\%' ] returns [ LIKE '%~xyz\%~%' ] 
     * <li>[ LIKE '\%xyz' ] returns [ LIKE '%~\%xyz~%' ] 
     * <li>[ LIKE 'xyz' ] returns [ LIKE '%~xyz~%' ]
     * </ul>
	 * @param clause an SQL LIKE clause, e.g. LIKE '%xyz' 
	 * @param delimiter the delimiter character(s).
     * @param force true if the delimiter character(s) and wildcard should
     *        always be appended to both ends. When false, they are only added
     *        when the end character is not already a wildcard.
	 * @return the adjusted LIKE clause.
	 */
	private static String addStartEndDelimiter(String clause, String delimiter,
	                                           boolean force) {
		int start = clause.indexOf(S_QUOTE);
		int end = clause.lastIndexOf(S_QUOTE);
        
        //"ending with" clause                  
        if (force || clause.charAt(end-1) != SQL_WILD_CHAR) {
            clause = clause.substring(0, end) + delimiter
                     + SQL_WILD + clause.substring(end, clause.length());
        }
		
		//"starting with" clause
        if (force || clause.charAt(start+1) != SQL_WILD_CHAR) {
            clause = clause.substring(0, start + 1) + SQL_WILD + delimiter 
                     + clause.substring(start + 1, clause.length());
        }
		
		return clause;
	}
	
	/***************************************************************************
	 * Creates a literal expression out of the passed string, after escaping
	 * all single quotes. E.g. [foo] returns [ = 'foo' ].
	 * @param value the literal value.
	 * @return the adjusted string.
	 */
	private static String createLiteral(String value) {
		return " = '" + value + "' ";
	}


	/***************************************************************************
	 * Replaces all single quotes with a pair of single quotes.
	 * @param value the string that may contain single quotes.
	 * @return the adjusted string.
	 */
	public static String escapeQuotes(String value) {
		return value.replaceAll(S_QUOTE,DOUBLE_S_QUOTE);
	}
    
	
	/***************************************************************************
	 * Given a string, replaces the occurences of a search string with a replace
	 * string.
	 * @param s is the string being manipulated.
	 * @param search is the search string being searched in s
	 * @param replace is the string we are using to replacing the search string
	 * @return is the original supplied string with the search string having been replaced using the replace string.
	 */
	 public static String replaceSubString(
			String s,
			String search,
			String replace) {

		int p = 0;

		while (p < s.length() && (p = s.indexOf(search, p)) >= 0) {
			s = s.substring(0, p) + replace +
					s.substring(p + search.length(), s.length());
			p += replace.length();
		}
		return s;
	}

}
