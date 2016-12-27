package demo.bupt.test;

import java.util.ArrayList;

import demo.bupt.Test;

public class TestsExample extends ArrayList<Test> {
	// TestsExample tests1;
	String[] testTitles = new String[] {
			"1.2012年2月1日某企业购入原材料一批开出一张面值为117 000元期限为3个月的不 带息的商业承兑汇票。2012年5月1日该企业无力支付票款时下列会计处理正确的是 。",
			"2.2011年3月某企业开始自行研发一项非专利技术至2011年12月31日研发成功并达到 预定可使用状态累计研究支出为160万元"
					+ "累计开发支出为500万元其中符合资本化条 件的支出为400万元。该非专利技术使用寿命不能合理确定假定不考虑其他因素该业 务"
					+ "致企业2011年度利润总额减少 万元。",
			"3.某企业为增值税一般纳税人适用的增值税税率为17%2012年6月建造厂房领用材料 实际成本20 000元计税价格为24 000元该项业务应计入在建工程成本的金额为 元。",
			"4.企业每期期末计提一次还本付息的长期借款利息对其中应当予以资本化的部分下列会 计处理正确的是 。  ",
			"5.某企业适用的城市维护建设税税率为7%2011年8月份该企业应缴纳增值税200 000元、 土地增值税30 000元、营业税100 000元、消费税50 000元、资源税20 000元8月份该 企业应记入“应交税费——应交城市维护建设税”科目的金额为 元。" };

	String[] testAnserAs = new String[] { "A.借记“财务费用”科目贷记“长期借款”科目  ",
			" A.100  ", "A.借应付票据 117 000  贷短期借款 ",
			"A.借记“财务费用”科目贷记“长期借款”科目  ", "A.16100 "

	};

	String[] testAnserBs = new String[] { "B.借记“财务费用”科目贷记“长期借款”科目  ",
			"B.100  ", "B.借应付票据 117 000  贷短期借款 ",
			"B.借记“财务费用”科目贷记“长期借款”科目  ", "B.16100 "

	};

	String[] testAnserCs = new String[] { "C.借记“财务费用”科目贷记“长期借款”科目  ",
			"C.100  ", "C.借应付票据 117 000  贷短期借款 ",
			"C.借记“财务费用”科目贷记“长期借款”科目  ", "C.16100 "

	};
	String[] testAnserDs = new String[] { "D.借记“财务费用”科目贷记“长期借款”科目  ",
			"D.100  ", "D.借应付票据 117 000  贷短期借款 ",
			"D.借记“财务费用”科目贷记“长期借款”科目  ", "D.16100 "

	};
	String[] testAnswers = new String[] { "B", "A", "C", "D", "A"

	};

	public TestsExample() {

		for (int i = 0; i < 5; i++) {
			Test test = new Test();
			test.setTestNum(i + 1);
			test.setTestTitle(testTitles[i]);
			test.setAnswerA(testAnserAs[i]);
			test.setAnswerB(testAnserBs[i]);
			test.setAnswerC(testAnserCs[i]);
			test.setAnswerD(testAnserDs[i]);
			test.setRightAnswer(testAnswers[i]);
			this.add(test);

		}
	}
}
