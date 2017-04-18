package com.jnzy.mdm.service.impl;

import com.jnzy.mdm.bean.BusinessDataCode;
import com.jnzy.mdm.bean.browser.BookMarkVO;
import com.jnzy.mdm.bean.browser.WeatherVO;
import com.jnzy.mdm.bean.list.ListMobile;
import com.jnzy.mdm.bean.other.AppCityVO;
import com.jnzy.mdm.bean.other.WebsiteNavigationVO;
import com.jnzy.mdm.bean.user.UserVO;
import com.jnzy.mdm.constant.HttpConstants;
import com.jnzy.mdm.dao.persistence.BrowserMapper;
import com.jnzy.mdm.dao.persistence.OtherMapper;
import com.jnzy.mdm.service.IBrowserService;
import com.jnzy.mdm.util.DocumentProUtil;
import com.jnzy.mdm.util.ParameterUtil;
import com.jnzy.mdm.util.ServiceProxyResponse;
import com.jnzy.mdm.util.StringUtil;
import com.jnzy.mdm.util.mapper.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by hardy on 2017/2/20.
 */
@Service
public class BrowserService implements IBrowserService{
    @Autowired
    private BrowserMapper browserMapper;
    @Autowired
    private OtherMapper otherMapper;

    /**
     * 获取城市
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse selCityProvince(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        Integer citylevel = 1;
        if (StringUtil.isNotBlank(map.get("citylevel") + "")) {
            citylevel = Integer.parseInt(map.get("citylevel") + "");
        }
        if (StringUtil.isNotBlank(map.get("adcode") + "")) {
            map.put("adcode", (map.get("adcode") + "").substring(0, 2));
        } else {
            map.put("adcode", null);
        }
        map.put("citylevel", citylevel);
        List<AppCityVO> appCityList = browserMapper.selCityProvince(map);
        if (appCityList.size() == 0 || appCityList.toString().equals("[]") || appCityList.toString().equals("[{}]")) {
            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
        }
        JSONArray jsonArray = new JSONArray();
        for(int i=0;i<appCityList.size();i++){
            AppCityVO appCityVO = appCityList.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",appCityVO.getName());
            jsonObject.put("adcode",appCityVO.getAdcode());
            jsonObject.put("citylevel",appCityVO.getCitylevel());
            jsonObject.put("tqMark",appCityVO.getTqMark());
            jsonArray.add(jsonObject);
        }
        return ServiceProxyResponse.success(jsonArray);
    }

    /**
     * 查询天气
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse selCityWeather(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isBlank(map.get("tqMark") + "")) {
            return ServiceProxyResponse.argsError();
        }

        String citycode = map.get("tqMark") + "";
        Document doc = Jsoup.connect(DocumentProUtil.getValues("weatherUrl")+"?citykey=" + citycode + "").get();
        String city = "";
        String temperature = "";
        String quality = "";
        String pm25 = "";
        String highTemperature = "";
        String lowTemperature = "";
        String weather = "";
        String wind = "";
        JSONArray temperArray=new JSONArray();
        try {
            Element respEle = doc.select("resp").get(0);
            city = respEle.getElementsByTag("city").get(0).text();
            temperature = respEle.getElementsByTag("wendu").get(0).text() + "℃";
            pm25 = respEle.getElementsByTag("environment").get(0).getElementsByTag("pm25").get(0).text();
            quality = respEle.getElementsByTag("environment").get(0).getElementsByTag("quality").get(0).text();
            Element forecastEle = respEle.getElementsByTag("forecast").get(0);
            highTemperature = forecastEle.getElementsByTag("weather").get(0).getElementsByTag("high").get(0).text();
            lowTemperature = forecastEle.getElementsByTag("weather").get(0).getElementsByTag("low").get(0).text();
            Element weatherEle = forecastEle.getElementsByTag("weather").get(0).getElementsByTag("day").get(0);
            weather = weatherEle.getElementsByTag("type").get(0).text();
            wind = weatherEle.getElementsByTag("fengxiang").get(0).text() + weatherEle.getElementsByTag("fengli").get(0).text();

            if (highTemperature.length() >= 7) {
                highTemperature = highTemperature.substring(3, 7);
            } else if (highTemperature.length() >= 6) {
                highTemperature = highTemperature.substring(3, 6);
            } else {
                highTemperature = highTemperature.substring(3, 5);
            }
            if (lowTemperature.length() >= 7) {
                lowTemperature = lowTemperature.substring(3, 7);
            } else if (lowTemperature.length() >= 6) {
                lowTemperature = lowTemperature.substring(3, 6);
            } else {
                lowTemperature = lowTemperature.substring(3, 5);
            }
            System.out.println("-----" + city + "----" + temperature + "------" + highTemperature + "-------" + lowTemperature + "---"
                    + "----" + pm25 + "-----" + quality + "----" + weather + "---" + wind);

            for(int i=0;i<5;i++){
                WeatherVO weatherVO = new WeatherVO();
                if(i==0){
                    weatherVO.setDateTime("今天");
                }else{
                    String dateTime = forecastEle.getElementsByTag("weather").get(i).getElementsByTag("date").get(0).text();
                    dateTime=dateTime.substring(3,dateTime.length());
                    weatherVO.setDateTime(dateTime);
                }
                String highTemp = forecastEle.getElementsByTag("weather").get(i).getElementsByTag("high").get(0).text();
                String lowTemp = forecastEle.getElementsByTag("weather").get(i).getElementsByTag("low").get(0).text();
                if (highTemp.length() >= 7) {
                    highTemp = highTemp.substring(3, 7);
                } else if (highTemp.length() >= 6) {
                    highTemp = highTemp.substring(3, 6);
                } else {
                    highTemp = highTemp.substring(3, 5);
                }
                if (lowTemp.length() >= 7) {
                    lowTemp = lowTemp.substring(3, 7);
                } else if (lowTemp.length() >= 6) {
                    lowTemp = lowTemp.substring(3, 6);
                } else {
                    lowTemp = lowTemp.substring(3, 5);
                }
                weatherVO.setHigh(highTemp);
                weatherVO.setLow(lowTemp);
                weatherVO.setWeather(forecastEle.getElementsByTag("weather").get(i).getElementsByTag("day").get(0).getElementsByTag("type").get(0).text());
                temperArray.add(weatherVO);
            }


        } catch (Exception e) {
            System.out.println("-----------抓取数据失败-----------");
            return ServiceProxyResponse.error(BusinessDataCode.SERVICE_EXCEPTION);
        }
        JSONObject returnObj = new JSONObject();
        returnObj.put("city", city);
        returnObj.put("temperature", temperature);
        returnObj.put("highTemperature", highTemperature);
        returnObj.put("lowTemperature", lowTemperature);
        returnObj.put("pm25", pm25);
        returnObj.put("quality", quality);
        returnObj.put("weather", weather);
        returnObj.put("wind", wind);
        if(temperArray.size()==0){
            returnObj.put("temperArray","[]");
        }else{
            returnObj.put("temperArray",temperArray);
        }
        return ServiceProxyResponse.success(returnObj);
    }

    /**
     * 根据城市查询天气
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse selCityWeatherByCity(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if (StringUtil.isBlank(map.get("cityName") + "")) {
            return ServiceProxyResponse.argsError();
        }
        //根据城市名称查询城市id
        String citycode = browserMapper.selCityCode(map);
        System.out.println("----------"+citycode);
        Document doc = Jsoup.connect(DocumentProUtil.getValues("weatherUrl")+"?citykey=" + citycode + "").get();
        String city = "";
        String temperature = "";
        String quality = "";
        String pm25 = "";
        String highTemperature = "";
        String lowTemperature = "";
        String weather = "";
        String wind = "";
        JSONArray temperArray=new JSONArray();
        try {
            Element respEle = doc.select("resp").get(0);
            city = respEle.getElementsByTag("city").get(0).text();
            temperature = respEle.getElementsByTag("wendu").get(0).text() + "℃";
            pm25 = respEle.getElementsByTag("environment").get(0).getElementsByTag("pm25").get(0).text();
            quality = respEle.getElementsByTag("environment").get(0).getElementsByTag("quality").get(0).text();
            Element forecastEle = respEle.getElementsByTag("forecast").get(0);
            highTemperature = forecastEle.getElementsByTag("weather").get(0).getElementsByTag("high").get(0).text();
            lowTemperature = forecastEle.getElementsByTag("weather").get(0).getElementsByTag("low").get(0).text();
            Element weatherEle = forecastEle.getElementsByTag("weather").get(0).getElementsByTag("day").get(0);
            weather = weatherEle.getElementsByTag("type").get(0).text();
            wind = weatherEle.getElementsByTag("fengxiang").get(0).text() + weatherEle.getElementsByTag("fengli").get(0).text();


            if (highTemperature.length() >= 7) {
                highTemperature = highTemperature.substring(3, 7);
            } else if (highTemperature.length() >= 6) {
                highTemperature = highTemperature.substring(3, 6);
            } else {
                highTemperature = highTemperature.substring(3, 5);
            }
            if (lowTemperature.length() >= 7) {
                lowTemperature = lowTemperature.substring(3, 7);
            } else if (lowTemperature.length() >= 6) {
                lowTemperature = lowTemperature.substring(3, 6);
            } else {
                lowTemperature = lowTemperature.substring(3, 5);
            }
            System.out.println("-----" + city + "----" + temperature + "------" + highTemperature + "-------" + lowTemperature + "---"
                    + "----" + pm25 + "-----" + quality + "----" + weather + "---" + wind);


            for(int i=0;i<5;i++){
                WeatherVO weatherVO = new WeatherVO();
                if(i==0){
                    weatherVO.setDateTime("今天");
                }else{
                    String dateTime = forecastEle.getElementsByTag("weather").get(i).getElementsByTag("date").get(0).text();
                    dateTime=dateTime.substring(3,dateTime.length());
                    weatherVO.setDateTime(dateTime);
                }
                String highTemp = forecastEle.getElementsByTag("weather").get(i).getElementsByTag("high").get(0).text();
                String lowTemp = forecastEle.getElementsByTag("weather").get(i).getElementsByTag("low").get(0).text();
                if (highTemp.length() >= 7) {
                    highTemp = highTemp.substring(3, 7);
                } else if (highTemp.length() >= 6) {
                    highTemp = highTemp.substring(3, 6);
                } else {
                    highTemp = highTemp.substring(3, 5);
                }
                if (lowTemp.length() >= 7) {
                    lowTemp = lowTemp.substring(3, 7);
                } else if (lowTemp.length() >= 6) {
                    lowTemp = lowTemp.substring(3, 6);
                } else {
                    lowTemp = lowTemp.substring(3, 5);
                }
                weatherVO.setHigh(highTemp);
                weatherVO.setLow(lowTemp);
                weatherVO.setWeather(forecastEle.getElementsByTag("weather").get(i).getElementsByTag("day").get(0).getElementsByTag("type").get(0).text());
                temperArray.add(weatherVO);
            }


        } catch (Exception e) {
            System.out.println("-----------抓取数据失败-----------");
            return ServiceProxyResponse.error(BusinessDataCode.SERVICE_EXCEPTION);
        }
        JSONObject returnObj = new JSONObject();
        returnObj.put("city", city);
        returnObj.put("temperature", temperature);
        returnObj.put("highTemperature", highTemperature);
        returnObj.put("lowTemperature", lowTemperature);
        returnObj.put("pm25", pm25);
        returnObj.put("quality", quality);
        returnObj.put("weather", weather);
        returnObj.put("wind", wind);
        if(temperArray.size()==0){
            returnObj.put("temperArray",temperArray);
        }else{
            returnObj.put("temperArray",temperArray);
        }
        return ServiceProxyResponse.success(returnObj);

    }


    /**
     *  浏览器意见反馈
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertWebFeedback(HttpServletRequest request,MultipartFile[] multipartFiles) throws Exception {
        if(StringUtil.isNull(request.getHeader(HttpConstants._headerKey))){

        }

        return null;
    }

    /**
     * 首页中的网址导航
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse websiteNavigationHome(HttpServletRequest request) throws Exception {
        Map map =  ParameterUtil.getMapParamsByRequest(request);
        if(StringUtil.isBlank(map.get("more")+"")){
            return ServiceProxyResponse.argsError();
        }
        String more = map.get("more")+"";
        //首页
        if(more.equals("0")){
            map.put("startIndex",0);
            map.put("pageSize",11);
        }
        List<WebsiteNavigationVO> websiteNavigationsList = browserMapper.websiteNavigationHome(map);
        if (websiteNavigationsList.size() == 0) {
            return ServiceProxyResponse.error(BusinessDataCode.NO_DATA);
        }
        String[] needAddDomain = {"websiteLogo"};
        return ServiceProxyResponse.success(ObjectMapper.mapListOnlyCont(websiteNavigationsList, Arrays.asList(needAddDomain), "websiteName", "websiteLogo", "websiteUrl"));
    }
    /**
     * 上传书签数据
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse insertBookmark(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getDecodeParamsMapByJSON(request);
        if(StringUtil.isBlank(map.get("deviceId")+"")||StringUtil.isBlank(map.get("bookMarkList")+"")){
            return ServiceProxyResponse.argsError();
        }
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        String bookMarkListJSON = map.get("bookMarkList")+"";
        JSONArray jsonArray = JSONArray.fromObject(bookMarkListJSON);
        List<BookMarkVO> bookMarkVOList = new ArrayList<BookMarkVO>();
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            if(!jsonObject.containsKey("bookmarkName")||!jsonObject.containsKey("bookmarkUrl")
                    ||!jsonObject.containsKey("bookmarkIdent")||!jsonObject.containsKey("level")){
                return ServiceProxyResponse.argsError();
            }else{
                if(StringUtil.isBlank(jsonObject.getString("bookmarkName"))
                        ||StringUtil.isBlank(jsonObject.getString("bookmarkUrl"))
                        ||StringUtil.isBlank(jsonObject.getString("bookmarkIdent"))
                        ||StringUtil.isBlank(jsonObject.get("level")+"")){
                    return ServiceProxyResponse.argsError();
                }else{
                    BookMarkVO bookMarkVO = new BookMarkVO();
                    bookMarkVO.setUserId(userVO.getUserId());
                    bookMarkVO.setBookmarkIdent(Integer.parseInt(jsonObject.get("bookmarkIdent")+""));
                    bookMarkVO.setBookmarkName(jsonObject.getString("bookmarkName"));
                    bookMarkVO.setBookmarkUrl(jsonObject.getString("bookmarkUrl"));
                    bookMarkVO.setLevel(Integer.parseInt(jsonObject.get("level")+""));
                    //根据标识查询是否存在改
                    if(StringUtil.isNotBlank(browserMapper.selBookmarkByIdent(bookMarkVO)+"")){
                        browserMapper.updateBookmark(bookMarkVO);
                        return ServiceProxyResponse.success();
                    }else{
                        bookMarkVOList.add(bookMarkVO);
                    }
                }
            }
        }
        map.put("bookMarkVOList",bookMarkVOList);
        browserMapper.insertBookmark(bookMarkVOList);

        return ServiceProxyResponse.success();
    }

    /**
     * 删除书签
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse delBookmark(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if(StringUtil.isBlank(map.get("deviceId")+"")||StringUtil.isBlank(map.get("bookmarkIdent")+"")){
            return ServiceProxyResponse.argsError();
        }
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        map.put("userId",userVO.getUserId());
        browserMapper.delBookmark(map);

        return ServiceProxyResponse.success();
    }

    /**
     *获取当前用户书签分页
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public ServiceProxyResponse selBookmark(HttpServletRequest request) throws Exception {
        Map map = ParameterUtil.getMapParamsByRequest(request);
        if(StringUtil.isBlank(map.get("deviceId")+"")){
            return ServiceProxyResponse.argsError();
        }
        UserVO userVO = (UserVO) request.getAttribute("userVO");
        map.put("userId",userVO.getUserId());
        List<BookMarkVO> bookMarkVOList = browserMapper.selBookmarkByUserId(map);
        return ServiceProxyResponse.success(ObjectMapper.mapListOnlyCont(bookMarkVOList,"bookmarkName","bookmarkUrl","bookmarkIdent","level"));
    }

}
