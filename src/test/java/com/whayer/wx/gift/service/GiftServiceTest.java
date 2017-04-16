package com.whayer.wx.gift.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.base.UnitTestBase;
import com.whayer.wx.common.X;
import com.whayer.wx.common.mvc.Pagination;
import com.whayer.wx.gift.vo.Gift;

@RunWith(BlockJUnit4ClassRunner.class)
public class GiftServiceTest extends UnitTestBase{

	public GiftServiceTest() {
		super("classpath:IOC.xml");
	}
	
	
	@Test
	public void testSaveGift() throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		GiftService giftService = super.getBean("giftServiceImpl");
		for(int i = 10; i < 20; i++){
			Gift gift = new Gift();
			gift.setId(X.uuidPure());
			gift.setName("礼品"+ i);
			gift.setDetail("礼品描述" + i);
			gift.setDeadline(df.parse("2017-04-01 23:59:59"));
			gift.setImgSrc("/image/icon_"+ (i-10) +".jpg");
			gift.setCreateTime(new Date());
			
			giftService.save(gift);
		}
	}
	
	@Test
	public void testFindById(){
		GiftService giftService = super.getBean("giftServiceImpl");
		Gift gift = giftService.findById("6C4058E75EC9408DA98F38BCE389F26C");
		
		System.out.println(gift.toString());
	}
	
	@Test
	public void testUpdate() throws ParseException{
		GiftService giftService = super.getBean("giftServiceImpl");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Gift gift = new Gift();
		gift.setId("6C4058E75EC9408DA98F38BCE389F26C");
		gift.setName("更改礼品");
		gift.setDetail("更改礼品描述");
		gift.setDeadline(df.parse("2017-04-18 23:59:59"));
		//gift.setImgSrc("/image/icon_xx.jpg");
		//gift.setCreateTime(new Date());
		giftService.update(gift);
	}
	
	@Test
	public void testDeleteById(){
		GiftService giftService = super.getBean("giftServiceImpl");
		int count = giftService.deleteById("6594923A14B847D09548991634906A72");
		System.out.println(count);
	}
	
	@Test
	public void testGetList(){
		GiftService giftService = super.getBean("giftServiceImpl");
		PageInfo<Gift> pageInfo = giftService.getGiftList(1, new Pagination());
		System.out.println(pageInfo.getList());
	}
}
