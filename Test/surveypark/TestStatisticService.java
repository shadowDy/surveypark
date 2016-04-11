package surveypark;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dy.surveypark.service.StatisticsService;

/**
 * @author DY
 * @version 创建时间：2015年10月26日 下午9:37:11
 */
public class TestStatisticService {

	private static StatisticsService ss;

	@BeforeClass
	public static void iniSurveyService() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		ss = (StatisticsService) ac.getBean("statisticsService");
	}

	@Test
	public void statistics() {
		ss.statistics(6);
	}

}
