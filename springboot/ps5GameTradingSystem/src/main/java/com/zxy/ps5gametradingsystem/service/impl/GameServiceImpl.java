package com.zxy.ps5gametradingsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.ps5gametradingsystem.entity.Game;
import com.zxy.ps5gametradingsystem.mapper.GameMapper;
import com.zxy.ps5gametradingsystem.service.GameService;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl extends ServiceImpl<GameMapper, Game> implements GameService {
}
