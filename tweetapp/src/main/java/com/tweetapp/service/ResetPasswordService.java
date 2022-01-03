package com.tweetapp.service;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class ResetPasswordService {

	public int resetPassword(String oldPassword, String newPassword, String confirmPassword) {
		Context context=null;
		Scriptable scope=null;
		Object result=null;
		int status=Integer.MIN_VALUE;
		try {
			context = Context.enter();
			scope = context.initStandardObjects();

			//String source = "java.lang.System.out.println('in reset password');";
			String source = "{ function resetPassword(){\r\n"
					+ "\r\n"
					+ "            if ('"+oldPassword+"' == \"\" || '"+newPassword+"' == \"\" || '"+confirmPassword+"' == \"\") {\r\n"
					+ "            java.lang.System.out.println('Please give all the inputs');\r\n"
					+ "             return '-1';\r\n"
					+ "             }\r\n"
					+ "            else if ('"+oldPassword+"' == '"+newPassword+"') {\r\n"
					+ "             java.lang.System.out.println(\"Old password and New Password cannot be same\");\r\n"
					+ "             return '0';\r\n"
					+ "            }\r\n"
					+ "            else if ('"+newPassword+"' != '"+confirmPassword+"') {\r\n"
					+ "             java.lang.System.out.println(\"Passwords do not match\");\r\n"
					+ "             return '2';\r\n"
					+ "            }\r\n"
					+ "\r\n"
					+ "            return '1';\r\n"
					+ "\r\n"
					+ "        } resetPassword(); }";
			
			result = context.evaluateString(scope, source, "<cmd>", 1, null);
			status=Integer.valueOf(result.toString().trim());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			result=null;
			scope=null;
			Context.exit();
		}
		return status;
	}

}
