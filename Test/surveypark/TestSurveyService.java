package surveypark;

import org.junit.Test;

import com.dy.surveypark.service.SurveyService;

/**
 * @author DY
 * @version 创建时间：2015年10月14日 下午4:04:02
 */
public class TestSurveyService {
	private static SurveyService ss;
	
	/**
	 * 切换状态
	 */
	@Test
	public void toggleStatus(){
		ss.toggleStatus(4);
		System.out.println();
	}
	
	@Test
	public void getSurvey(){
		ss.getSurvey(6);
	}
}
