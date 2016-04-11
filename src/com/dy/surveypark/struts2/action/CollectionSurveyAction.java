package com.dy.surveypark.struts2.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dy.surveypark.model.Answer;
import com.dy.surveypark.model.Question;
import com.dy.surveypark.model.Survey;
import com.dy.surveypark.service.SurveyService;

/**
 * 收集调查
 * 
 * @author DY
 * @version 创建时间：2015年10月30日 下午8:58:20
 */
@Controller
@Scope("prototype")
public class CollectionSurveyAction extends BaseAction<Survey> {

	private static final long serialVersionUID = 1L;
	private Integer sid;
	@Autowired
	private SurveyService surveyService;

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String execute() {
		return SUCCESS;

	}

	public InputStream getIs() {
		try {
			// 存放 qid <-->index（列号） 映射
			Map<Integer, Integer> qidIndexMap = new HashMap<Integer, Integer>();
			List<Question> list = surveyService.getQuestions(sid);
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet();
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = null;
			Question q = null;
			for (int i = 0; i < list.size(); i++) {
				q = list.get(i);
				cell = row.createCell(i);
				// 设置列宽
				sheet.setColumnWidth(i, 6000);
				cell.setCellValue(q.getTitle());
				qidIndexMap.put(q.getId(), i);
			}
			// 设置自动环绕
			HSSFCellStyle style = wb.createCellStyle();
			style.setWrapText(true);
			// 输出答案
			List<Answer> answers = surveyService.getAnswers(sid);
			String oldUuid = null;
			String newUuid = null;
			int rowIndex = 0;
			for (Answer a : answers) {
				newUuid = a.getUuid();
				if (!newUuid.equals(oldUuid)) {
					rowIndex++;
					oldUuid = newUuid;
					row = sheet.createRow(rowIndex);
				}
				cell = row.createCell(qidIndexMap.get(a.getQuestionId()));
				cell.setCellValue(a.getAnswersIds());
				cell.setCellStyle(style);
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			wb.write(baos);
			return new ByteArrayInputStream(baos.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
