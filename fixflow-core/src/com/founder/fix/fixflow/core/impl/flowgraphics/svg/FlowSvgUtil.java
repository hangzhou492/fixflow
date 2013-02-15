package com.founder.fix.fixflow.core.impl.flowgraphics.svg;

import com.founder.fix.fixflow.core.exception.FixFlowException;
import com.founder.fix.fixflow.core.impl.flowgraphics.svg.component.ISvgComponent;
import com.founder.fix.fixflow.core.impl.flowgraphics.svg.to.SvgBaseTo;

public class FlowSvgUtil {
	
	private static String __REGEX_SIGNS = "\\{[^}{]+\\}";
	
	public static String replaceAll(String src,String target,String dist){
        if (src == null || target == null || dist == null) {
            return src;
        }
        StringBuffer result = new StringBuffer(src);
        String str = src;

        int preLength = 0;
        int newEndPost = 0;
        while (str.toString().indexOf(target) >= 0) {
            int startPost = preLength + str.toString().indexOf(target);
            int endPost = startPost + target.length();

            result.delete(startPost, endPost);
            result.insert(startPost, dist);

            newEndPost = startPost + dist.length();
            str = result.substring(newEndPost, result.length());
            preLength = newEndPost;
        }
        return result.toString();
//		String regex = __REGEX_SIGNS;
//		Pattern regexExpType = Pattern.compile(regex);
//		Matcher mType = regexExpType.matcher(src);
//		StringBuffer sb = new StringBuffer();
//		while(mType.find()){
//			if(mType.group().equals(target))
//				if(dist==null){
//					mType.appendReplacement(sb,"");
//				}
//			else {
//				mType.appendReplacement(sb,StringUtil.getString(dist));
//			}
//				
//		}
//		mType.appendTail(sb);
//		return sb.toString();
	} 
	
	public static String getSvgComponent(SvgBaseTo svgTo) throws FixFlowException{
		String result = null;
		String compClass = svgTo.getComponentClass();
		
		Object obj = null;
		try {
			obj = Class.forName(compClass).newInstance();
		} catch (Exception e) {
			throw new FixFlowException("",e);
		}
		ISvgComponent comp = null;
		if(obj instanceof ISvgComponent)
			comp = (ISvgComponent)obj;
		else
			throw new FixFlowException(compClass+"没有继承自组件类ISvgComponent！");
		
		result = comp.createComponent(svgTo);
		
		return result;
	}
	

}
