//package com.business.base.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;

//@SuppressWarnings("unchecked")
//public class PreviledgeInterceptor implements HandlerInterceptor {

//	private List<String> excludedUrls;
//	public static boolean interceptPreviledgeEnabled = false;
//
//	static{
//		interceptPreviledgeEnabled = SafeConvert.convertToBoolean(ConfigPropertieUtils.getString("media.system.security.interceptor.previledge.enabled"), false);
//	}
//
//	public void setExcludedUrls(List<String> excludedUrls) {
//		this.excludedUrls = excludedUrls;
//	}
//
//    @Override
//    public boolean preHandle(HttpServletRequest request,
//            HttpServletResponse response, Object handler) throws Exception {
//
//    	if(request.getSession().getAttribute("previledgeEnabled") == null){
//    		request.getSession().setAttribute("previledgeEnabled", interceptPreviledgeEnabled);
//    	}
//
//    	if(!interceptPreviledgeEnabled){
//    		return true;
//    	}
//
//        String requestUri = request.getRequestURI();
//        for (String url : excludedUrls) {
//            if (requestUri.startsWith(url)) {
//                return true;
//            }
//        }
//
//        UsercmsSessionInfo usercmsSessionInfo = (UsercmsSessionInfo) request.getSession().getAttribute(DigitalCmsConstants.CMS_USER_INFO_STORED_IN_SESSION);
//
//        if (usercmsSessionInfo == null || usercmsSessionInfo.getUserInfo() == null||
//        		usercmsSessionInfo.getUserInfo().getUsercmsId() ==null ||
//        		usercmsSessionInfo.getUserInfo().getUsercmsId() == 0L) {
//            throw new MediaSecurityException();
//
//        } else if(usercmsSessionInfo.getUserInfo().getPreviledge() == 0) {
//
//            return true;
//        } else{
//
//        	List<Functionality> userAllFuncs = (List<Functionality>)request.getSession().getAttribute(DigitalCmsConstants.ALL_FUNCTIONALITIES_USER);
//        	List<Functionality> allSystemFuncs = (List<Functionality>)request.getSession().getAttribute(DigitalCmsConstants.ALL_FUNCTIONALITIES_SYSTEM);
//        	//判断在用户权限里是否能找到一条匹配的
//        	URLPathMatcher matcher = new URLPathMatcher();
//        	if(getMostMatchedRule(matcher, userAllFuncs,requestUri) == null && getMostMatchedRule(matcher, allSystemFuncs,requestUri)!=null ){
//        		throw new MediaAuthorizationException();
//        	}
//        }
//        return true;
//    }
//
//	@Override
//	public void afterCompletion(HttpServletRequest arg0,
//			HttpServletResponse arg1, Object arg2, Exception arg3)
//			throws Exception {
//
//	}
//
//	@Override
//	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
//			Object arg2, ModelAndView arg3) throws Exception {
//
//	}
//
//
//	private static Functionality getMostMatchedRule(URLPathMatcher matcher, List<Functionality> funcList, String requestUri){
//
//		Functionality functionality = null;
//		int finalScore = 0;
//		for(Functionality tmpFunc: funcList){
//			//父节点不参与url匹配
//			if(tmpFunc.getLeaf() && StringUtils.isNotEmpty(tmpFunc.getUrlPattern())){
//
//				String[] urlPatterns = DigitalCmsConstants.semiColonSpliter.split(tmpFunc.getUrlPattern());
//				for(String urlPattern:urlPatterns){
//					int score = matcher.getMatchPoint(urlPattern.trim(), requestUri);
//					if(score>finalScore){
//						finalScore = score;
//						functionality = tmpFunc;
//					}
//				}
//			}
//		}
//		return functionality;
//	}
	
//}
