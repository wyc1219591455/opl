package me.zhengjie.modules.opl.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.opl.domain.Model;
import me.zhengjie.modules.opl.domain.Project;
import me.zhengjie.modules.opl.domain.Status;
import me.zhengjie.modules.opl.mapper.PublicMapper;
import me.zhengjie.modules.opl.service.PublicService;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: chenxin.jiang
 * @Date: 2020/11/2
 * @Description:
 */
@Service
@RequiredArgsConstructor
public class PublicServiceImpl implements PublicService {
   private final PublicMapper publicMapper;
    @Override
    public List<Model> findAllModel() {
        return publicMapper.findAllModel();
    }

    @Override
    public List<Status> findAllStatus() {
        return publicMapper.findAllStatus();
    }

    @Override
    public List<Project> findAllProject() {
        return publicMapper.findAllProject();
    }

    @Override
    public Integer findCountProblemToday(Date date) {
        DateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
        String format = dateFormat.format(date);
        return publicMapper.findCountProblemToday(format);
    }

}
