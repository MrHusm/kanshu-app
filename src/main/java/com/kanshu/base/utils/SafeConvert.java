//package com.business.base.utils;
//
//import java.math.BigDecimal;
//import java.net.URLEncoder;
//import java.sql.Date;
//import java.sql.Timestamp;
//import java.util.Calendar;
//import java.util.GregorianCalendar;
//import java.util.Locale;
//import java.util.TimeZone;
//
//import org.apache.log4j.Logger;
//
//public class SafeConvert {
//	private static final Logger logger = Logger.getLogger(SafeConvert.class);
//	public static final String ENCODING_DEFAULT = "UTF-8";
//
//	public static BigDecimal convertToBigDecimal(String s, String defaultValue) {
//		try {
//			if (s == null)
//				return new BigDecimal(defaultValue);
//			return new BigDecimal(s);
//		} catch (NumberFormatException e) {
//			return new BigDecimal(-1);
//		}
//	}
//
//	public static BigDecimal convertToBigDecimal(BigDecimal b, String defaultValue) {
//		try {
//			if (b == null)
//				return new BigDecimal(defaultValue);
//			return new BigDecimal(b.toString());
//		} catch (NumberFormatException e) {
//			return new BigDecimal(-1);
//		}
//	}
//
//	/**
//	 * Removes the "." if any from the String value.
//	 *
//	 * @param val
//	 * @return String
//	 */
//	private static String getIntOnlyFromString(String val) {
//		int length = val.indexOf(".");
//		return val.substring(0, length);
//	}
//
//	/**
//	 * This method returns the integer representation of String even if has a
//	 * double format (with .). i.e. 320.00 will return 320.
//	 *
//	 * @param s,
//	 *            String representation.
//	 * @param defaultValue,
//	 *            int value.
//	 * @return int.
//	 */
//	public static int convertToIntFromDouble(String s, int defaultValue) {
//		try {
//			if (s != null) {
//				return Integer.parseInt(getIntOnlyFromString((s)));
//			}
//		} catch (Exception e) {
//		}
//		return defaultValue;
//	}
//
//	public static BigDecimal convertToBigDecimal(String s,
//			BigDecimal defaultValue, BigDecimal minimumValue) {
//		try {
//
//			BigDecimal db = new BigDecimal(defaultValue.toString());
//			if (s != null) {
//				db = new BigDecimal(s);
//			}
//			if (db.compareTo(minimumValue) == -1) {
//				db = new BigDecimal(minimumValue.toString());
//			}
//			return db;
//		} catch (NumberFormatException e) {
//			return new BigDecimal(defaultValue.toString());
//		}
//	}
//
//	public static BigDecimal convertToBigDecimal(String s,
//			BigDecimal defaultValue) {
//		BigDecimal db = defaultValue;
//		try {
//			if (s != null && s.trim().length()>0) {
//				db = new BigDecimal(s);
//			}
//		} catch (NumberFormatException e) {
//		}
//		return db;
//	}
//
//	public static int convertToInt(BigDecimal bd, int defaultValue) {
//		try {
//			if (bd == null)
//				return defaultValue;
//
//			return bd.intValue();
//		} catch (NumberFormatException e) {
//			return defaultValue;
//		}
//	}
//
//	public static int convertToInt(String s, int defaultValue) {
//		try {
//			if (s != null) {
//				return Integer.parseInt(s);
//			}
//		} catch (Exception e) {
//		}
//		return defaultValue;
//	}
//
//	public static String convertToStringNotNull(String s) {
//		return convertToStringNotNull(s, "");
//	}
//
//	public static String convertToStringNotNull(String s, String defaultValue) {
//		if (s == null || s.equals("null")) //backend returns "null" as string too.
//			return defaultValue;
//		return s;
//	}
//
//	public static String nonEmpty(String s, String defaultValue) {
//		if ( (s == null) || (s.trim().length() == 0))
//			return defaultValue;
//		return s.trim();
//	}
//
//	public static String convertToStringNotNull(String s, String defaultValue,
//			boolean trim, int maxLen) {
//		if (s == null) {
//			return defaultValue;
//		}
//		if (trim) {
//			String sTrimmed = s.trim();
//			if (sTrimmed.length() > maxLen) {
//				return sTrimmed.substring(0, maxLen);
//			} else {
//				return sTrimmed;
//			}
//		} else {
//			if (s.length() > maxLen) {
//				return s.substring(0, maxLen);
//			} else {
//				return s;
//			}
//
//		}
//
//	}
//
//	public static String convertToStringFromObjNotNull(Object o,
//			String defaultValue) {
//		if (o == null)
//			return defaultValue;
//		return o.toString();
//	}
//
//	public static Date convertToDate(String s, String defaultValue) {
//		try {
//			return Date.valueOf(s);
//		} catch (Exception e) {
//		}
//		try {
//			return Date.valueOf(defaultValue);
//		} catch (Exception e) {
//			return new Date(System.currentTimeMillis());
//		}
//	}
//
//	public static boolean convertToBoolean(String s, boolean defaultValue) {
//		try {
//			if (s == null)
//				return defaultValue;
//			if ("".equals(s))
//				return defaultValue;
//			if ("Y".equalsIgnoreCase(s))
//				return true;
//			if ("ON".equalsIgnoreCase(s))
//				return true;
//			if ("N".equalsIgnoreCase(s))
//				return false;
//			if ("T".equalsIgnoreCase(s))
//				return true;
//			if ("F".equalsIgnoreCase(s))
//				return false;
//			if ("1".equalsIgnoreCase(s))
//				return true;
//			if ("0".equalsIgnoreCase(s))
//				return false;
//			return Boolean.parseBoolean(s);
//		} catch (Exception e) {
//
//		}
//		return defaultValue;
//	}
//
//	public static boolean convertToBoolean(Object sIn, boolean defaultValue) {
//		try {
//			if (sIn == null)
//				return defaultValue;
//			if (sIn instanceof Boolean)
//				return ((Boolean)sIn).booleanValue();
//			if (! (sIn instanceof String) )
//				return defaultValue;
//			String s = (String) sIn;
//			if ("".equals(s))
//				return defaultValue;
//			if ("Y".equalsIgnoreCase(s))
//				return true;
//			if ("ON".equalsIgnoreCase(s))
//				return true;
//			if ("N".equalsIgnoreCase(s))
//				return false;
//			if ("T".equalsIgnoreCase(s))
//				return true;
//			if ("F".equalsIgnoreCase(s))
//				return false;
//			if ("1".equalsIgnoreCase(s))
//				return true;
//			if ("0".equalsIgnoreCase(s))
//				return false;
//			return Boolean.parseBoolean(s);
//		} catch (Exception e) {
//
//		}
//		return defaultValue;
//	}
//
//	public static boolean convertStringToBooleanFromObject(Object o,
//			boolean defaultValue) {
//		try {
//			if (o == null)
//				return false;
//			if (!(o instanceof String))
//				return false;
//			String s = (String) o;
//			return convertToBoolean(s, defaultValue);
//		} catch (Exception e) {
//
//		}
//		return defaultValue;
//	}
//
//	public static String convertFromBooleanToYN(boolean bool) {
//		if (bool)
//			return "Y";
//		return "N";
//	}
//
//	public static int convertStringToWithDefaultGT(String value,
//			int greaterThan, int defaultValue) {
//		try {
//
//			int valueAsInt = convertToInt(value, defaultValue);
//			if (valueAsInt > greaterThan) {
//				return valueAsInt;
//			}
//		} catch (Exception e) {
//
//		}
//		return defaultValue;
//
//	}
//
//	public static int convertIntWithDefaultGT(int value, int greaterThan,
//			int defaultValue) {
//		try {
//
//			int valueAsInt = value;
//			if (valueAsInt > greaterThan) {
//				return valueAsInt;
//			}
//		} catch (Exception e) {
//
//		}
//		return defaultValue;
//
//	}
//
//	/**
//	 * method - convertStringToDouble
//	 *
//	 * Parse a numeric entry to a double. If invalid, set to default value.
//	 *
//	 *
//	 * @param number
//	 * @param defaultValue
//	 * @return
//	 */
//	public static double convertStringToDouble(String number,
//			double defaultValue) {
//		try {
//			return Double.parseDouble(number);
//		} catch (Exception e) {
//
//		}
//		return defaultValue;
//	}
//
//	public static float toFloat(String number,
//			float defaultValue) {
//		try {
//			return Float.parseFloat(number);
//		} catch (Exception e) {
//
//		}
//		return defaultValue;
//	}
//
//	/**
//	 * method - convertStringToInteger
//	 *
//	 * Parse a numeric entry to a integer. If invalid, set to default value.
//	 *
//	 *
//	 * @param number
//	 * @param defaultValue
//	 * @return
//	 */
//	public static int convertStringToInteger(String number,
//			int defaultValue) {
//		try {
//			return Integer.parseInt(number);
//		} catch (Exception e) {
//
//		}
//		return defaultValue;
//	}
//
//	/**
//	 * Method - convertNumericToInteger
//	 *
//	 * Remove the Decimals
//	 *
//	 * @param numeric
//	 * @return
//	 * @throws Exception
//	 */
//	public static int convertDoubleToInteger(double numeric) {
//		return (int) numeric;
//	}
//
//	public static Timestamp convertCalendarToTimestampDefaultCurrent(
//			Calendar calendar) {
//		try {
//			if (calendar != null) {
//				return new Timestamp(calendar.getTimeInMillis());
//			}
//		} catch (Exception e) {
//
//		}
//		return new Timestamp(System.currentTimeMillis());
//	}
//
//	private static void loggerdebug(String s) {
//		//logger.debug(s);
//
//	}
//
//	public static GregorianCalendar convertTimestampToCalendarWithDefaultCurrent(
//			Timestamp ts) {
//		try {
//			if (ts != null) {
//				loggerdebug("IN" + ts.getTime());
//				loggerdebug("NOTNULL" + ts.getTime());
//				GregorianCalendar gc = new GregorianCalendar(TimeZone
//						.getTimeZone("UTC"));
//				if (ts.getNanos() > 0) {
//					gc.setTimeInMillis(ts.getTime());
//					loggerdebug("ODR" + gc.toString());
//					return gc;
//				}
//			}
//		} catch (Exception e) {
//
//		}
//		GregorianCalendar gc = new GregorianCalendar(TimeZone
//				.getTimeZone("UTC"));
//		gc.setTimeInMillis(System.currentTimeMillis());
//		loggerdebug("ERR" + gc.toString());
//		return gc;
//	}
//
//	public static GregorianCalendar convertToGregorialCalendar(
//			String dateString, Locale locale, String datePattern) {
//
//		//DateFormatSymbols symbols = new DateFormatSymbols(locale);
//
//		GregorianCalendar gc = new GregorianCalendar(locale);
//		try {
//			java.util.Date d = DateUtil.getDateByFormat(dateString,datePattern);
//			gc.setTime(d);
//			return gc;
//		} catch (Exception e) {
//			loggerdebug("ERR" + gc.toString() + "EXCEPTION:"
//					+ e.getClass().getName() + ":MESSAGE:" + e.getMessage());
//		}
//		gc.setTimeInMillis(System.currentTimeMillis());
//		return gc;
//	}
//
//	public static GregorianCalendar convertToGregorialCalendarDefaultCurrent(
//			String year, String month, String day, Locale locale) {
//
//		GregorianCalendar gc = new GregorianCalendar(locale);
//		// gc.setLenient(false);
//		try {
//			int checkYear4 = Integer.parseInt(year);
//			if ( checkYear4 < 100) {
//				checkYear4 += 2000;
//			}
//			gc.set(Calendar.YEAR, checkYear4);
//			gc.set(Calendar.MONTH, Integer.parseInt(month) - 1);
//			gc.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
//			return gc;
//		} catch (Exception e) {
//			loggerdebug("ERR" + gc.toString() + "EXCEPTION:"
//					+ e.getClass().getName() + ":MESSAGE:" + e.getMessage());
//		}
//		gc.setTimeInMillis(System.currentTimeMillis());
//		return gc;
//	}
//
//	public static GregorianCalendar convertToGregorialCalendarDefaultFirstDay(
//			String year, String month, String day, Locale locale) {
//
//		GregorianCalendar gc = new GregorianCalendar(locale);
//		gc.setLenient(false);
//		try {
//			gc.set(Calendar.YEAR, Integer.parseInt(year));
//			gc.set(Calendar.MONTH, Integer.parseInt(month) - 1);
//			gc.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
//			gc.get(Calendar.YEAR);
//			gc.get(Calendar.MONTH);
//			gc.get(Calendar.DAY_OF_MONTH);
//			return gc;
//		} catch (Exception e) {
//			loggerdebug("ERR" + gc.toString() + "EXCEPTION:"
//					+ e.getClass().getName() + ":MESSAGE:" + e.getMessage());
//		}
//		try {
//			gc.set(Calendar.YEAR, Integer.parseInt(year));
//			gc.set(Calendar.MONTH, Integer.parseInt(month) - 1);
//			gc.set(Calendar.DAY_OF_MONTH, 1);
//			gc.get(Calendar.YEAR);
//			gc.get(Calendar.MONTH);
//			gc.get(Calendar.DAY_OF_MONTH);
//			return gc;
//		} catch (Exception e) {
//			logger.debug("ERR" + gc.toString() + "EXCEPTION:"
//					+ e.getClass().getName() + ":MESSAGE:" + e.getMessage());
//		}
//		gc.setTimeInMillis(System.currentTimeMillis());
//		return gc;
//	}
//
//	public static GregorianCalendar convertToGregorialCalendarFromTimeOfDay(
//			String tod, Locale locale) {
//
//		GregorianCalendar gc = nowGC(locale);
//		if ((tod != null) && (tod.length() == 8)) {
//			String hours = tod.substring(0, 2);
//			String minutes = tod.substring(3, 5);
//			String ampm = tod.substring(6);
//
//			try {
//				gc.set(Calendar.HOUR, Integer.parseInt(hours));
//				gc.set(Calendar.MINUTE, Integer.parseInt(minutes));
//				gc
//						.set(Calendar.AM_PM,
//								"AM".equalsIgnoreCase(ampm) ? Calendar.AM
//										: Calendar.PM);
//				return gc;
//			} catch (Exception e) {
//				logger
//						.debug("ERR" + gc.toString() + "EXCEPTION:"
//								+ e.getClass().getName() + ":MESSAGE:"
//								+ e.getMessage());
//			}
//		}
//		gc.setTimeInMillis(System.currentTimeMillis());
//		return gc;
//	}
//
//	public static GregorianCalendar nowGC(Locale locale) {
//		GregorianCalendar gc = new GregorianCalendar(locale);
//		gc.setTimeInMillis(System.currentTimeMillis());
//		return gc;
//
//	}
//
//	public static GregorianCalendar addToDateRecoverAfterError(Locale locale,
//			GregorianCalendar date, int index, int delta, boolean isLenient) {
//		String func = "addToDateRecoverAfterError:";
//		GregorianCalendar gcNew = nowGC(locale);
//		try {
//			if (date == null) {
//				logger.error(func
//						+ "Null Value - Application Verification Requierd");
//				return gcNew;
//			}
//			gcNew.setTimeInMillis(date.getTimeInMillis());
//
//			if (gcNew.isLenient() != isLenient)
//				gcNew.setLenient(isLenient);
//			gcNew.add(index, delta);
//			return gcNew;
//		} catch (NullPointerException npe) {
//			// This is a strange error when doing add which can be fixed by
//			// creating a new GC
//			return addToDateRecoverAfterError(locale, date, index, delta,
//					isLenient);
//		} catch (Exception e) {
//			logger.error(func
//					+ "Null Value - Application Verification Requierd");
//
//		}
//
//		return gcNew;
//	}
//
//	public static GregorianCalendar addToDate(Locale locale,
//			GregorianCalendar date, int index, int delta, boolean isLenient) {
//		String func = "addToDate:";
//		try {
//			if (date == null) {
//				logger.error(func
//						+ "Null Value - Application Verification Requierd");
//				return nowGC(locale);
//			}
//
//			if (date.isLenient() != isLenient)
//				date.setLenient(isLenient);
//			date.add(index, delta);
//			return date;
//		} catch (NullPointerException npe) {
//			// This is a strange error when doing add which can be fixed by
//			// creating a new GC
//			return addToDateRecoverAfterError(locale, date, index, delta,
//					isLenient);
//		} catch (Exception e) {
//			logger.error(func
//					+ "Null Value - Application Verification Requierd");
//
//		}
//
//		return nowGC(locale);
//	}
//
//	public static void dateOnly(GregorianCalendar gc) {
//		gc.set(Calendar.HOUR, 0);
//		gc.set(Calendar.MINUTE, 0);
//		gc.set(Calendar.SECOND, 0);
//		gc.set(Calendar.MILLISECOND, 0);
//	}
//
//	public static GregorianCalendar sqlDateToGC(Locale locale,
//			Date sqlDate) {
//		String func = "sqlDateToGC:";
//		GregorianCalendar gcNew = nowGC(locale);
//		try {
//			if (sqlDate == null) {
//				logger.error(func
//						+ "Null Value - Application Verification Requierd");
//				return nowGC(locale);
//			}
//			gcNew.setTime(sqlDate);
//			return gcNew;
//		} catch (NullPointerException npe) {
//			// This is a strange error when doing add which can be fixed by
//			// creating a new GC
//		} catch (Exception e) {
//			logger.error(func
//					+ "Null Value - Application Verification Requierd");
//
//		}
//		return nowGC(locale);
//
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public static boolean testEnum(Enum e1, Enum e2) {
//		try {
//			return (0 == (e1.compareTo(e2)));
//		} catch (Exception e) {
//			logger.debug("testEnum failed:MESSAGE=" + e.getMessage());
//		}
//		return false;
//	}
//
//	public static String ccExpDateMMYYtoMMYYYY(String expDateMMYY) {
//		String expDateMMYYYY = convertToStringNotNull(expDateMMYY, "");
//
//		// Only check if 4 characters long
//		if ((expDateMMYY != null) && (expDateMMYY.length() == 4)) {
//
//			// TODO - Use Century Login - but that is a long time from now (we
//			// should store this as MMYYYY
//
//			expDateMMYYYY = expDateMMYY.substring(0, 2) + "20"
//					+ expDateMMYY.substring(2);
//		}
//		return expDateMMYYYY;
//	}
//
//	public static String removeBlanks(String s) {
//		try {
//			if ( s != null ) {
//				StringBuffer sb = new StringBuffer(s.length());
//
//				for( int i = 0; i < s.length(); i++ ) {
//					if ( s.charAt(i) != ' ' ) {
//						sb.append(s.charAt(i));
//					}
//				}
//				return sb.toString();
//			} else {
//				return "";
//			}
//		} catch (Exception e) {
//
//		}
//		return s;
//
//	}
//
//	public static String removeChar(String s, char c, boolean stopAtFirst) {
//		try {
//			if ( s != null ) {
//				StringBuffer sb = new StringBuffer(s.length());
//
//				for( int i = 0; i < s.length(); i++ ) {
//					if ( s.charAt(i) != c ) {
//						if(stopAtFirst) {
//							return s.substring(i);
//						}
//						else {
//							sb.append(s.charAt(i));
//						}
//					}
//				}
//				return sb.toString();
//			} else {
//				return "";
//			}
//		} catch (Exception e) {
//
//		}
//		return s;
//	}
//
//
//	/**
//	 * Returns a String object that has the specified charactes that are escaped.
//	 * used for escaping the single quotes in a javascript alert message.
//	 * @param  msg  String containing the character to be escaped.
//	 * @param  ch   the character to be escaped.
//	 * @return      the String that has the escaped characters.
//	 */
//	 public static String escapeChar(String msg, char ch) {
//
//		    int beginIndex = 0 ;
//		    int endIndex = 0;
//		    StringBuffer newMsg = new StringBuffer("");
//		    while ((endIndex = msg.indexOf("'", endIndex)) != -1 ) {
//		      newMsg.append(msg.substring(beginIndex, endIndex)).append("\\'");
//		      beginIndex = ++endIndex;
//		    }
//
//		    if (beginIndex > 0) {
//		         newMsg.append(msg.substring(beginIndex));
//		         return newMsg.toString();
//		    }
//
//		 return msg;
//	 }
//
//	   public static String formatDateUsingSimpleFormat(String pattern, java.util.Date date, String defaultValue) {
//	    	try {
//
//		        String dateAsString = DateUtil.getDate(date, pattern);
//		        return dateAsString;
//
//	    	} catch(Exception e) {
//
//	    	}
//	    	return defaultValue;
//	    }
//
//	   public static String cleanUpHTMLInString(String s) {
//		   try {
//			   if ( s == null ) {
//				   return "";
//			   }
//			   s = s.replaceAll("&nbsp;", " ");
//			   s = s.replaceAll("\\<.*?\\>", "");
//			   return s;
//		   } catch(Exception e) {
//		   }
//		   return "";
//	   }
//
//
//	   public static boolean isEmpty(String s) {
//		   try {
//			   if ( s == null ) {
//				   return true;
//			   }
//			   if( s.trim().length() == 0) {
//				   return true;
//			   }
//			   return false;
//		   } catch(Exception e) {
//		   }
//		   return true;
//	   }
//
//	   public static String encode(String stringToEncode) {
//		   String encodedString = stringToEncode;
//		   if (stringToEncode==null) {
//			   return "";
//		   }
//		   try {
//			   encodedString = URLEncoder.encode(stringToEncode, ENCODING_DEFAULT);
//		   } catch(Exception e) {
//			   logger.warn("encode:<<<EXCEPTION>>>msg=" + e.getMessage(), e);
//		   }
//		   return encodedString;
//	   }
//
//	   /**
//		 * method - convertStringToLong
//		 *
//		 * Parse a numeric entry to a long. If invalid, set to default value.
//		 *
//		 *
//		 * @param number
//		 * @param defaultValue
//		 * @return
//		 */
//		public static long convertStringToLong(String number,
//				long defaultValue) {
//			try {
//				return Long.parseLong(number);
//			} catch (Exception e) {
//
//			}
//			return defaultValue;
//		}
//
//		public static String toJavaCase(String text){
//			if (text==null || text.trim().length()==0)
//				return "";
//			String javaText = text.trim();
//			if (javaText.length()>0)
//				javaText = (javaText.charAt(0)+"").toLowerCase()+javaText.substring(1);
//			return javaText;
//		}
//
//}
