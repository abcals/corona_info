package com.greenart.api;

import java.util.Calendar;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.greenart.service.RegionalInfoService;
import com.greenart.vo.CoronaSidoInfoVO;
import com.greenart.vo.CoronaVaccinationVO;
import com.greenart.vo.CoronaVaccineInfoVO;
import com.greenart.vo.CoronaWeeksVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegionalAPIController {
    @Autowired
    RegionalInfoService service;
    @GetMapping("/api/regional")
    public Map<String, Object> getRegionalInfo(
        @RequestParam String region, @RequestParam @Nullable String date
    ){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        
        if(date == null || date.equals("")){
            // Date now = new Date();
            // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            // date = formatter.format(now);
            Calendar now = Calendar.getInstance();
            Calendar standard = Calendar.getInstance();
            standard.set(Calendar.HOUR, 11);
            standard.set(Calendar.MINUTE, 00);
            standard.set(Calendar.SECOND, 00);
            
            if(now.getTimeInMillis() < standard.getTimeInMillis()){
            // 현재 접속시간이 기준시간 (11시 10분) 보다 이전일 때
            // 하루 이전 날자로 변경
                now.add(Calendar.DATE, -1);
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = formatter.format(now.getTime());
        }

        CoronaSidoInfoVO vo = service.selectRegionalCoronaInfo(region, date);
        resultMap.put("data", vo);

        String dangerAge = service.selectDangerAge(date);
        resultMap.put("dangerAge", dangerAge);
        
        List<CoronaWeeksVO> coronaWeeksList = service.selectRegionalCoronaTwoWeeks(region, date);
        resultMap.put("coronaWeeksList", coronaWeeksList);

        char[] c = region.toCharArray();
        String region2 = "%"+c[0]+"%"+c[1]+"%";
        
        List<CoronaVaccinationVO> coronaVaccinationList = service.CoronaVaccinationTwoWeeks(region2, date);
        resultMap.put("coronaVaccinationList", coronaVaccinationList);

        return resultMap;
    }

    @GetMapping("/api/regional/vaccine")
    public Map<String, Object> getRegionalVaccineInfo(
        @RequestParam String region, @RequestParam @Nullable String date
    ){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        
        // "제주" -> ['제', '주']
        char[] c = region.toCharArray();
        // System.out.println(c[0]); // '제'
        // System.out.println(c[1]); // '주'

        region = "%"+c[0]+"%"+c[1]+"%";

        
        if(date == null || date.equals("")){
            Calendar now = Calendar.getInstance();
            Calendar standard = Calendar.getInstance();
            standard.set(Calendar.HOUR, 15);
            standard.set(Calendar.MINUTE, 30);
            standard.set(Calendar.SECOND, 00);
            
            if(now.getTimeInMillis() < standard.getTimeInMillis()){
            // 현재 접속시간이 기준시간 (15시 30분) 보다 이전일 때
            // 하루 이전 날자로 변경
                now.add(Calendar.DATE, -1);
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = formatter.format(now.getTime());
        }
        CoronaVaccineInfoVO vo = service.selectCoronaVaccineInfo(region, date);
        if(vo == null){
            resultMap.put("status", false);
            resultMap.put("data", null);
            resultMap.put("message", "데이터가 없습니다. ("+region+","+date+")");
            return resultMap;
        }
        DecimalFormat formatter = new DecimalFormat("###,###");

        String formattedFirstCnt = formatter.format(vo.getAccFirstCnt());
        String formattedSecondCnt = formatter.format(vo.getAccSecondCnt());

        resultMap.put("status", true);
        resultMap.put("data", vo);
        resultMap.put("formattedFirstCnt", formattedFirstCnt);
        resultMap.put("formattedSecondCnt", formattedSecondCnt);

        return resultMap;
    }

}
