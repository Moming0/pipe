package com.bs.pipe.utils;

import java.util.HashMap;
import java.util.Map;

public class DistanceUtil {
	
	private static double EARTH_RADIUS = 6378.137;
	
	private static double rad(Double d) {
		return d * Math.PI / 180.0;
	}
 
	/**
	 * 通过经纬度获取距离(单位：米)
	 * @param lngA	经度A
	 * @param latA	纬度A
	 * @param lngB	经度B
	 * @param latB	纬度B
	 * @return
	 */
	public static double getDistance(Double lngA, Double latA, Double lngB, Double latB
			) {
		double radLat1 = rad(latA);
		double radLat2 = rad(latB);
		double a = radLat1 - radLat2;
		double b = rad(lngA) - rad(lngB);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000d) / 10000d;
		s = s * 1000;
		return s;
	}

	/**
     * 获取当前用户一定距离以内的经纬度值
     * @param raidus 单位米
     * 最小经度 minLng
     * 最小纬度 maxLat
     * 最大经度 maxLng
     * 最大纬度 minLat
     */
    public static Map<String,String> getAround(String latStr, String lngStr, String raidus) {
        Map<String,String> map = new HashMap<String,String>();
          
        Double latitude = Double.parseDouble(latStr);// 传值给经度
        Double longitude = Double.parseDouble(lngStr);// 传值给纬度
  
        Double degree = (24901 * 1609) / 360.0; // 获取每度
        double raidusMile = Double.parseDouble(raidus);
          
        Double mpdLng = Double.parseDouble((degree * Math.cos(latitude * (Math.PI / 180))+"").replace("-", ""));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        //获取最小经度
        Double minLat = longitude - radiusLng;
        // 获取最大经度
        Double maxLat = longitude + radiusLng;
          
        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidusMile;
        // 获取最小纬度
        Double minLng = latitude - radiusLat;
        // 获取最大纬度
        Double maxLng = latitude + radiusLat;
          
        map.put("minLat", minLat+"");
        map.put("maxLat", maxLat+"");
        map.put("minLng", minLng+"");
        map.put("maxLng", maxLng+"");
        return map;
    }
	
    /**
     * 根据经纬度和半径计算经纬度范围
     *
     * @param raidus 单位米
     * @return minLat, minLng, maxLat, maxLng
     */
    public static Map<String,String> getAround(double lon, double lat, int raidus) {
    	Map<String,String> map = new HashMap<String,String>();
        Double longitude = lon;
        Double latitude = lat;

        Double degree = (24901 * 1609) / 360.0;
        double raidusMile = raidus;

        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidusMile;
        Double minLat = latitude - radiusLat;
        Double maxLat = latitude + radiusLat;

        Double mpdLng = degree * Math.cos(latitude * (Math.PI/ 180));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        Double minLng = longitude - radiusLng;
        Double maxLng = longitude + radiusLng;
        map.put("minLat", minLat+"");
        map.put("maxLat", maxLat+"");
        map.put("minLng", minLng+"");
        map.put("maxLng", maxLng+"");
        return map;
    }
	
	
	public static void main(String[] args) {

		Map<String, String> around = getAround(114.420500,30.513400, 111190);
		System.out.println(around.toString());
		
		double distance = getDistance(114.420500,30.513400,
				114.420500 ,30.513800);
		
		/*
		double distance = getDistance(114.420500,30.513400,
				114.421000 ,30.513400);
		*/
		
		System.out.println("距离" + distance / 1000 + "公里");
		
	}

}
