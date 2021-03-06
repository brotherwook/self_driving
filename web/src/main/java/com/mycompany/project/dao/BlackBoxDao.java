package com.mycompany.project.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.mycompany.project.model.BlackBox;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class BlackBoxDao extends EgovAbstractMapper{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BlackBoxDao.class);
	private static final int TOTAL_COUNT_OF_IMAGES = 100;
	
	private Map<String, Boolean> check = new HashMap<>();
	
	public void saveImage(BlackBox blackBox) {
		//LOGGER.info("실행");
		
		/*로버당 저장한 디비의 row의 개수가 1000개 이하이면 (즉, check에 key값 rname이 없거나 value가 false이면) insert 
		 *로버당 저장한 디비의 row의 개수가 1000개 이상이면 (즉, check의 value가 true라면) 제일 오래된 row를 delete 후 insert
		 * */
		String rname = blackBox.getRname();
		int countOfRow = selectOne("blackBox.countRowByRname", blackBox);
		
		if(TOTAL_COUNT_OF_IMAGES <= countOfRow){
			insert("blackBox.insert", blackBox);
			delete("blackBox.deleteOldestRow");		
		}else {
			insert("blackBox.insert", blackBox);
			LOGGER.info("{}: {}",rname, Integer.toString(countOfRow));
		}
	}

	public List<String> getImages(String rname) {
		LOGGER.info("실행");
		List<String> list = selectList("selectAllByRname", rname);
		return list;
	}
}
