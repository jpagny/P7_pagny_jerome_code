package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurvePointTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @Test
    public void should_beReturnedCurvePoint_when_aNewCurvePointIsSaved() {
        CurvePoint curvePoint = new CurvePoint(10,10d,30d);

        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        curvePoint = curvePointRepository.save(curvePoint);

        assertNotNull(curvePoint.getId());
        assertTrue(curvePoint.getCurveId() == 10);
        assertNotNull(curvePoint.getCreationDate());
    }

	/*

		// Update
		curvePoint.setCurveId(20);
		curvePoint = curvePointRepository.save(curvePoint);
		Assert.assertTrue(curvePoint.getCurveId() == 20);

		// Find
		List<CurvePoint> listResult = curvePointRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = curvePoint.getId();
		curvePointRepository.delete(curvePoint);
		Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
		Assert.assertFalse(curvePointList.isPresent());
	 */


}