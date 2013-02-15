package com.founder.fix.fixflow.core.impl.flowgraphics.svg.component;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;


import com.founder.fix.fixflow.core.impl.flowgraphics.svg.CaclModel;
import com.founder.fix.fixflow.core.impl.flowgraphics.svg.FlowSvgUtil;
import com.founder.fix.fixflow.core.impl.flowgraphics.svg.SvgBench;
import com.founder.fix.fixflow.core.impl.flowgraphics.svg.to.SvgBaseTo;
import com.founder.fix.fixflow.core.impl.flowgraphics.svg.to.SvgLineTo;
import com.founder.fix.fixflow.core.impl.flowgraphics.svg.to.SvgLineBaseTo.SvgPoint;
import com.founder.fix.fixflow.core.impl.flowgraphics.svg.to.SvgLineTo.LineType;
import com.founder.fix.fixflow.core.exception.FixFlowException;
import com.founder.fix.fixflow.core.impl.util.StringUtil;
import com.founder.fix.fixflow.core.impl.util.XmlUtil;

public class SvgLineComponent implements ISvgComponent {

	private static String comPath = "/svgcomponent/line.xml";
	
	private static String none ="none";
	
	private static String path= "{path}";
	
	private static String text_x="{text_x}";
	
	private static String text_y="{text_y}";
	
	private static String start_con = "{start_con}";
	
	private static String start_def = "{start_def}";
	
	public String createComponent(SvgBaseTo svgTo) {
		String result = null;
		try {
			SvgLineTo lineTo = (SvgLineTo)svgTo;
			InputStream in = SvgBench.class.getResourceAsStream(comPath);
			Document doc = XmlUtil.read(in);
			String str = doc.getRootElement().asXML();
			str = FlowSvgUtil.replaceAll(str, id, lineTo.getId());
			str = FlowSvgUtil.replaceAll(str, text, lineTo.getLabel());
			List<SvgPoint> pointList = lineTo.getSvgPointList();
			StringBuffer pointPath = new StringBuffer();
			String textx = null;
			String texty = null;
			String startconRef = none;
			String startdefRef = none;
			
			int size = pointList.size();
			for(int i=0;i<size;i++){
				if(i==0){
					pointPath.append("M");
				}else{
					pointPath.append("L");
				}

				SvgPoint point = pointList.get(i);
				float x = point.getX();
				float y = point.getY();
				x = x-2;
				y = y-2;
				pointPath.append(x);
				pointPath.append(" ");
				pointPath.append(y);
			}
			
			int textLength = lineTo.getLabel()==null?0:lineTo.getLabel().length();
			SvgPoint svgPoint = CaclModel.caclCenterPoint(lineTo);
			textx = StringUtil.getString(svgPoint.getX()-textLength*6.9/2);
			texty = StringUtil.getString(svgPoint.getY()-20);
			
//			if(size!=0 && size%2==0){
//				SvgPoint leftPoint = pointList.get(size/2-1);
//				SvgPoint rightPoint = pointList.get(size/2);
//				textx = String.valueOf((leftPoint.getX()+rightPoint.getX())/2);
//				texty = String.valueOf((leftPoint.getY()+rightPoint.getY())/2);
//			}else{
//				int position = size/2;
//				SvgPoint middlePoint = pointList.get(position);
//				textx = String.valueOf(middlePoint.getX());
//				texty = String.valueOf(middlePoint.getY());
//			}
			
			str = FlowSvgUtil.replaceAll(str, path, pointPath.toString());
			str = FlowSvgUtil.replaceAll(str, text_x, textx);
			str = FlowSvgUtil.replaceAll(str, text_y, texty);
			
			if(lineTo.getLineType().equals(LineType.DefaultFlow)){
				startdefRef = "";
			}else if(lineTo.getLineType().equals(LineType.ConditionalFlow)){
				startconRef = "";
			}
			str = FlowSvgUtil.replaceAll(str, start_con, startconRef);
			str = FlowSvgUtil.replaceAll(str, start_def, startdefRef);
			
			result = str;
		} catch (DocumentException e) {
			throw new FixFlowException("",e);
		}
		
		return result;
	}

}
