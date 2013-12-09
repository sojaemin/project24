package util;

public class common {
	
	public static String nulltovoid(String str) {
		if (str==null || "".equals(str))
		return "";
		else return str;
		}
	
	public static String cutString(String str, int byLen)
	{
		for (int i=str.length();i>0;i--)
		{
			if (str.substring(0,i).getBytes().length<=byLen)
			{
			str=str.substring(0,i);
			break;
			}
		}
		return str;
	}
	public static String cutStringInstead(String str, int byLen)
		{
			int strlength = str.length();
			
			for (int i=str.length();i>0;i--)
			{
				if (str.substring(0,i).getBytes().length<=byLen)
				{
				str=str.substring(0,i);
				break;
				}
			}
			for (int i=0;i<strlength-byLen;i++) {
				str = str + "*";
			}
			return str;
	}
	public static int str2int(String str) {
		if(str==null)
		return 0;
		else
		return Integer.valueOf(str).intValue();
		}
	
	public static String replaceBr(String str) {
		if (str==null || "".equals(str)) {
			return "";
		}	else {
			str = str.replaceAll("\r\n", "<br/>");//줄바꿈
			str = str.replaceAll("\u0020","&nbsp;");//스페이스바
			return str;
		}
	}
	
	public static String replaceRn(String str) {
		if (str==null || "".equals(str)) {
			return "";
		}	else {
			str = str.replaceAll("<br/>", "\r\n");//줄바꿈
			str = str.replaceAll("&nbsp;","\u0020");//스페이스바
			return str;
		}
	}
	
}

