package com.zxy.ps5gametradingsystem.controller;
import com.zxy.ps5gametradingsystem.common.Result;
import com.zxy.ps5gametradingsystem.entity.Recycle;
import com.zxy.ps5gametradingsystem.service.RecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recycle")
public class RecycleController {
    @Autowired
    private RecycleService recycleService;
    @PostMapping("/save")
    public Result save (@RequestBody Recycle s) {
        recycleService.save(s);
        return Result.success();

    }
}
