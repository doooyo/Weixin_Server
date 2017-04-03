package com.whayer.wx.test.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test {
	public static void main(String[] args) throws Exception {
		Filter f1 = new Filter();
		f1.setId("001");
		
		Filter f2 = new Filter();
		f1.setName("doyo");
		
		Filter f3 = new Filter();
		f1.setEmail("duyud@qq.com,xxx@163.com");
		
		String sql1 = query(f1);
		String sql2 = query(f2);
		String sql3 = query(f3);
		
		System.out.println(sql1);
		System.out.println(sql2);
		System.out.println(sql3);
		
	}
	
	private static String query(Object filter) throws Exception {
		
		StringBuilder sb = new StringBuilder();
		//获取class
		Class cls = filter.getClass();
		//获取table名
		boolean exist = cls.isAnnotationPresent(Table.class);
		if(!exist){
			return null;
		}
		Table table = (Table)cls.getAnnotation(Table.class);
		String tableName = table.value();
		sb.append("select * from ").append(tableName).append("where 1=1");
		
		//遍历所有字段
		Field[] fields = cls.getDeclaredFields();
		for(Field field : fields){
			//处理每个字段,拿到字段名
			boolean fExist = field.isAnnotationPresent(Column.class);
			if(!fExist) continue;
			Column column = field.getAnnotation(Column.class);
			String columnName = column.value();
			//拿到字段值(注意columnName可以和fieldName不同,所以要单独取)
			String fieldName = field.getName();
			String getMethodName = "get"+fieldName.substring(0, 1).toUpperCase()
					+fieldName.substring(1);//第一个字母大写
			Method method = cls.getDeclaredMethod(getMethodName);//.getMethod(getMethodName);
			Object fieldValue = method.invoke(filter);
			//开始拼装sql
			if(fieldValue == null 
					|| fieldValue instanceof Integer && (Integer)fieldValue == 0){ //处理 null 和 0
				continue;
			}
			sb.append(" and ").append(fieldName);
			if(fieldValue instanceof String){
				sb.append("=").append("'").append(fieldValue).append("'");
			}else if(fieldValue instanceof Integer){
				sb.append("=").append(fieldValue);
			}//其他类型请自行添加
		}
		
		return sb.toString();
	}
}
